package com.d1m.wechat.controller.material;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.controller.file.Upload;
import com.d1m.wechat.controller.file.UploadController;
import com.d1m.wechat.dto.ImageTextDto;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.dto.MaterialImageTextDetailDto;
import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.mapper.MaterialCategoryMapper;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.d1m.wechat.pamametermodel.ImageModel;
import com.d1m.wechat.pamametermodel.ImageTextModel;
import com.d1m.wechat.pamametermodel.MaterialModel;
import com.d1m.wechat.service.ConversationService;
import com.d1m.wechat.service.MaterialImageTextDetailService;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.util.CommonUtils;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.util.StringUtils;
import com.github.pagehelper.Page;
import com.github.pagehelper.StringUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

@Controller
@RequestMapping("/material")
@Api(value = "素材API", tags = "素材接口")
public class MaterialController extends BaseController {

    private Logger log = LoggerFactory.getLogger(MaterialController.class);

    @Autowired
    private MaterialService materialService;

    @Autowired
    private MaterialImageTextDetailService materialImageTextDetailService;
    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MemberService memberService;

    /**
     * 图片素材推送到微信
     *
     * @param materialModel
     * @param session
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "素材图文推送微信", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-素材图文推送微信成功")
    @RequestMapping(value = "pushwx.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject pushToWx(
            @ApiParam(name = "MaterialModel", required = false)
            @RequestBody(required = false) MaterialModel materialModel,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            if (materialModel == null) {
                materialModel = new MaterialModel();
            }
            materialService.pushMaterialImageTextToWx(getWechatId(session),
                    getUser(session), materialModel.getId());
            return representation(Message.MATERIAL_IMAGE_TEXT_PUSH_WX_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 素材预览
     *
     * @param materialModel
     * @param session
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "素材图文推送微信", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-素材图文推送微信成功")
    @RequestMapping(value = "preview.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject previewMaterial(
            @ApiParam(name = "MaterialModel", required = false)
            @RequestBody(required = false) MaterialModel materialModel,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            //如果是预览单图文，无materialId，则走客服预览接口
            if (materialModel.getId() == null && materialModel.getNewid() != null) {
                MemberDto member = memberService.getMemberDto(getWechatId(), materialModel.getMemberId());
                notBlank(member, Message.MEMBER_NOT_EXIST);
                CommonUtils.send2SocialWechatCoreApi(getWechatId(), member, materialModel.getNewid(), materialModel.getNewtype(), conversationService);
            }
            //有materialId走原有接口
            else {
                materialService.previewMaterial(getWechatId(), materialModel);
            }
            return representation(Message.MATERIAL_IMAGE_TEXT_PUSH_WX_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 图片素材列表
     *
     * @param imageModel
     * @param session
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "获取素材图片列表", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-获取素材图片列表成功")
    @RequestMapping(value = "image/list.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"app-msg:list", "member:list", "message:message-list"}, logical = Logical.OR)
    public JSONObject searchImage(
            @ApiParam(name = "ImageModel", required = false)
            @RequestBody(required = false) ImageModel imageModel,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        if (imageModel == null) {
            imageModel = new ImageModel();
        }
        Page<MaterialDto> materialDtos = materialService.searchImage(
                getWechatId(session), imageModel, true);
        return representation(Message.MATERIAL_IMAGE_LIST_SUCCESS,
                materialDtos, imageModel.getPageSize(),
                imageModel.getPageNum(), materialDtos.getTotal());
    }

    /**
     * 图文列表
     * query : 标题/作者/简介 模糊查询
     *
     * @param imageTextModel
     * @param session
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "获取素材图文列表", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-获取素材图文列表成功")
    @RequestMapping(value = "imagetext/list.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject searchImageText(
            @ApiParam(name = "ImageTextModel", required = false)
            @RequestBody(required = false) ImageTextModel imageTextModel,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        if (imageTextModel == null) {
            imageTextModel = new ImageTextModel();
        }
        Page<MaterialDto> materialDtos = materialService.searchImageText(
                getWechatId(session), imageTextModel, true);
        return representation(Message.MATERIAL_IMAGE_TEXT_LIST_SUCCESS,
                materialDtos, imageTextModel.getPageSize(),
                imageTextModel.getPageNum(), materialDtos.getTotal());
    }

    /**
     * @param file
     * @param request
     * @param response
     * @param session
     * @return
     */
    @ApiOperation(value = "上传图片文件", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-上传文件成功")
    @RequestMapping(value = "image/new.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject uploadImage(
            @ApiParam(name = "上传文件", required = false)
            @RequestParam(required = false) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response,
            HttpSession session) {
        try {
            Upload upload = UploadController.upload(getWechatId(), file, Constants.IMAGE,
                    Constants.MATERIAL);
            Material material = materialService.createMaterialImage(
                    getWechatId(session), getUser(session), upload);
            MaterialDto materialDto = new MaterialDto();
            materialDto.setId(material.getId());
            materialDto.setTitle(material.getName());
            materialDto.setUrl(material.getPicUrl());
            materialDto.setWxPicUrl(material.getWxPicUrl());
            return representation(Message.FILE_UPLOAD_SUCCESS, materialDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "上传图文图片文件", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-上传文件成功")
    @RequestMapping(value = "mediaimage/new.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject uploadMediaImage(
            @ApiParam(name = "上传文件", required = false)
            @RequestParam(required = false) MultipartFile file,
            HttpServletRequest request, HttpServletResponse response,
            HttpSession session) {
        try {
            Upload upload = UploadController.upload(getWechatId(), file, Constants.MEDIAIMAGE,
                    Constants.MATERIAL);
            Material material = materialService.createMediaImage(
                    getWechatId(session), getUser(session), upload);
            MaterialDto materialDto = new MaterialDto();
            materialDto.setId(material.getId());
            materialDto.setTitle(material.getName());
            materialDto.setUrl(material.getPicUrl());
            materialDto.setWxPicUrl(material.getWxPicUrl());
            return representation(Message.FILE_UPLOAD_SUCCESS, materialDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * Upload image by xiumi image's URL.
     *
     * @param request  HttpServletRequest
     * @param response HttpServletResponse
     * @param session  HttpSession
     * @return upload result
     */
    @ApiOperation(value = "上传秀米图文图片文件", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-上传文件成功")
    @RequestMapping(value = "mediaimage/newXiumiImage.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject uploadXiumiMediaImage(
            HttpServletRequest request, HttpServletResponse response,
            HttpSession session) {
        try {
            String imgUrl = request.getParameter("img");
            Upload upload = UploadController.upload(getWechatId(), imgUrl, Constants.MEDIAIMAGE,
                    Constants.MATERIAL);
            Material material = materialService.createMediaImage(
                    getWechatId(session), getUser(session), upload);
            MaterialDto materialDto = new MaterialDto();
            materialDto.setId(material.getId());
            materialDto.setTitle(material.getName());
            materialDto.setUrl(material.getPicUrl());
            materialDto.setWxPicUrl(material.getWxPicUrl());
            return representation(Message.FILE_UPLOAD_SUCCESS, materialDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 新增图文
     * <p>
     * imageTexts String like '[{"title" : "title","author" : "author",
     * "content" : "content", "contentSourceChecked" : true, "contentSourceUrl"
     * : "http://www.xxxx.com", "materialCoverId" : 1, "showCover" : true,
     * "summary" : "summary"}....]'
     * <p>
     * materialCoverId : 封面图片(图片素材ID)
     * <p>
     * required : title, content, materialCoverId
     *
     * @param session
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "创建素材图文", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-创建素材图文成功")
    @RequestMapping(value = "imagetext/new.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject createImageText(
            @ApiParam(name = "MaterialModel", required = false)
            @RequestBody(required = false) MaterialModel materialModel,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            Material material = materialService.createImageText(
                    getWechatId(session), getUser(session), materialModel);
            if (materialModel.getPush() != null && materialModel.getPush()) {
                materialService.pushMaterialImageTextToWx(getWechatId(session),
                        getUser(session), material.getId());
            }
            MaterialDto materialDto = new MaterialDto();
            materialDto.setId(material.getId());
            return representation(Message.MATERIAL_IMAGE_TEXT_CREATE_SUCCESS,
                    materialDto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 更新图文
     * <p>
     * materialId : 图文ID
     * <p>
     * imageTexts String like '[{"title" : "title","author" : "author",
     * "content" : "content", "contentSourceChecked" : true, "contentSourceUrl"
     * : "http://www.xxxx.com", "materialCoverId" : 1, "showCover" : true,
     * "summary" : "summary"}....]'
     * <p>
     * materialCoverId : 封面图片(图片素材ID)
     * <p>
     * required : title, content, materialCoverId
     *
     * @param materialId 素材id
     * @return baseResponse
     */
    @ApiOperation(value = "更新素材图文", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-更新素材图文成功")
    @RequestMapping(value = "imagetext/{materialId}/update.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject updateImageText(
            @ApiParam("素材ID")
            @PathVariable Integer materialId,
            @ApiParam(name = "MaterialUpdateReq")
            @Validated @RequestBody MaterialUpdateReq updateReq) {
        try {

            final Material material = materialService.selectByKey(materialId);

            if (Objects.isNull(material)) {
                return representation(Message.MATERIAL_NOT_EXIST);
            }

            material.setId(materialId);
            material.setWechatId(getWechatId());
            material.setMaterialCategoryId(updateReq.getMaterialCategoryId());


            final List<MaterialImageTextDetail> imageTextDetails = updateReq.getImageTextUpdateReqs().stream()
                    .filter(i -> Objects.nonNull(i.getId()))
                    .filter(i -> StringUtils.isNotEmpty(i.getRemarks()) || Objects.nonNull(i.getShowOriginalLink()))
                    .map(i -> {
                        final MaterialImageTextDetail imageTextDetail = new MaterialImageTextDetail();
                        imageTextDetail.setMaterialId(materialId);
                        imageTextDetail.setId(i.getId());
                        if (StringUtil.isNotEmpty(i.getRemarks())) {
                            imageTextDetail.setRemarks(i.getRemarks());
                        }
                        if (Objects.nonNull(i.getShowOriginalLink())) {
                            imageTextDetail.setContentSourceChecked(i.getShowOriginalLink());
                        }
                        return imageTextDetail;
                    })
                    .collect(Collectors.toList());

            materialService.updateMaterialAndImageText(material, imageTextDetails);

            return representation(Message.MATERIAL_IMAGE_TEXT_UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error("图文更新失败", e.getMessage());
            return wrapException(e);
        }
    }

    @SuppressWarnings("NullableProblems")
    private static class MaterialUpdateReq {

        private String materialCategoryId;

        @Size(min = 1, message = "图文数量至少一个")
        @NotNull(message = "图文数量至少一个")
        @Valid
        private List<ImageTextUpdateReq> imageTextUpdateReqs;

        public String getMaterialCategoryId() {
            return materialCategoryId;
        }

        public void setMaterialCategoryId(String materialCategoryId) {
            this.materialCategoryId = materialCategoryId;
        }

        public List<ImageTextUpdateReq> getImageTextUpdateReqs() {
            return imageTextUpdateReqs;
        }

        public void setImageTextUpdateReqs(List<ImageTextUpdateReq> imageTextUpdateReqs) {
            this.imageTextUpdateReqs = imageTextUpdateReqs;
        }
    }

    private static class ImageTextUpdateReq {

        @NotNull(message = "图文id不能空")
        private Integer id;

        private Boolean showOriginalLink;

        private String remarks;

        public Integer getId() {
            return id;
        }

        public void setId(Integer id) {
            this.id = id;
        }

        public Boolean getShowOriginalLink() {
            return showOriginalLink;
        }

        public void setShowOriginalLink(Boolean showOriginalLink) {
            this.showOriginalLink = showOriginalLink;
        }

        public String getRemarks() {
            return remarks;
        }

        public void setRemarks(String remarks) {
            this.remarks = remarks;
        }
    }

    /**
     * 删除图文
     *
     * @param materialId
     * @param session
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "删除素材图文", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-删除素材图文成功")
    @RequestMapping(value = "imagetext/{materialId}/delete.json", method = RequestMethod.DELETE)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject deleteImageText(
            @ApiParam("素材ID")
            @PathVariable Integer materialId,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            materialService.deleteImageText(getWechatId(session),
                    getUser(session), materialId);
            return representation(Message.MATERIAL_IMAGE_TEXT_DELETE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 删除图文详情
     *
     * @param session
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "删除图文详情", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-删除图文详情成功")
    @RequestMapping(value = "imagetextdetail/{materialImageTextDetailId}/delete.json", method = RequestMethod.DELETE)
    @ResponseBody
    @Deprecated
    @RequiresPermissions("app-msg:list")
    public JSONObject deleteImageTextDetail(
            @ApiParam("素材图文ID")
            @PathVariable Integer materialImageTextDetailId,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            materialService.deleteImageTextDetail(getWechatId(session),
                    getUser(session), materialImageTextDetailId);
            return representation(Message.MATERIAL_IMAGE_TEXT_DETAIL_DELETE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 查看图文详情
     * <p>
     * id : 图文素材ID
     *
     * @param id
     * @param session
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "获取图文详情", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-获取图文详情成功")
    @RequestMapping(value = "imagetextdetail/{id}/get.json", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject getImageText(
            @ApiParam("图文ID")
            @PathVariable Integer id,
            @RequestParam(value = "isSub", required = false) Boolean isSub,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        MaterialDto materialDto = materialService.getImageText(getWechatId(), id, isSub);
        return representation(Message.MATERIAL_IMAGE_TEXT_GET_SUCCESS, materialDto);
    }

    /**
     * 图文素材详情列表
     * <p>
     * materialId ： 图文素材ID
     *
     * @param materialId
     * @param query
     * @param sortName
     * @param sortDir
     * @param pageNum
     * @param pageSize
     * @param session
     * @param request
     * @param response
     * @return
     */
    @ApiOperation(value = "获取图文详情列表", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-获取图文详情列表成功")
    @RequestMapping(value = "imagetextdetail/list.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject listImageTextDetail(
            @ApiParam(name = "素材ID", required = false)
            @RequestParam(required = false) Integer materialId,
            @ApiParam(name = "查询条件", required = false)
            @RequestParam(required = false) String query,
            @ApiParam(name = "排序名", required = false)
            @RequestParam(required = false) String sortName,
            @ApiParam(name = "排序顺序", required = false)
            @RequestParam(required = false) String sortDir,
            @ApiParam(name = "当前页数", defaultValue = "1", required = false)
            @RequestParam(required = false, defaultValue = "1") Integer pageNum,
            @ApiParam(name = "分页大小", defaultValue = "10", required = false)
            @RequestParam(required = false, defaultValue = "10") Integer pageSize,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        Page<MaterialImageTextDetailDto> materialImageTextDetails = materialImageTextDetailService
                .search(getWechatId(session), materialId, query, sortName,
                        sortDir, pageNum, pageSize, true);
        return representation(Message.MATERIAL_IMAGE_TEXT_DETAIL_LIST_SUCCESS,
                materialImageTextDetails, pageSize, pageNum,
                materialImageTextDetails.getTotal());
    }

    /**
     * 更新图片名称
     */
    @ApiOperation(value = "重命名素材图片", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-重命名素材图片成功")
    @RequestMapping(value = "image/{materialId}/rename.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject updateImageName(
            @ApiParam("素材ID")
            @PathVariable Integer materialId,
            @ApiParam(name = "素材名称", required = false)
            @RequestParam(required = false) String name, HttpSession session,
            HttpServletRequest request, HttpServletResponse response) {
        try {
            materialService.renameImage(getWechatId(session), getUser(session),
                    materialId, name);
            return representation(Message.MATERIAL_IMAGE_RENAME_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 删除图片
     */
    @ApiOperation(value = "删除素材图片", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-删除素材图片成功")
    @RequestMapping(value = "image/{materialId}/delete.json", method = RequestMethod.DELETE)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject deleteImage(
            @ApiParam("素材ID")
            @PathVariable Integer materialId,
            HttpSession session, HttpServletRequest request,
            HttpServletResponse response) {
        try {
            notBlank(materialId, Message.MATERIAL_ID_NOT_BLANK);
            JSONObject jsonObject = materialService.deleteImage(getWechatId(session), materialId);
            log.info("删除图片返回：{}", jsonObject);
            return jsonObject;
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    /**
     * 根据sn查看图文详情
     *
     * @param sn
     * @return
     */
    @ApiOperation(value = "根据sn查看图文详情", tags = "素材接口")
    @ApiResponse(code = 200, message = "1-获取图文详情成功")
    @RequestMapping(value = "imagetextdetail/{sn}/getInfoBySn.json", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("app-msg:list")
    public JSONObject getInfoBySn(@ApiParam("sn") @PathVariable String sn) {
        MaterialDto materialDto = materialService.getInfoBySn(sn);
        return representation(Message.MATERIAL_IMAGE_TEXT_GET_SUCCESS, materialDto);
    }

    @Autowired
    private MaterialCategoryMapper materialCategoryMapper;
    /**
     * 图文列表分组
     */
    @ApiResponse(code = 200, message = "1-获取素材图文列表成功")
    @PostMapping(value = "imagetext/group/list.json")
    @ResponseBody
    public JSONObject getImageTextGroupList(HttpSession session) {
        try {
        Page<MaterialDto> materialDtos = materialService.getImageTextGroupList(getWechatId(session));
            final List<MaterialDto> arrayList = new ArrayList<>();
            materialDtos.stream()
                    .map(a -> {
                        if (a.getMaterialCategoryId() == null) {
                            a.setMaterialCategoryName("未知");
                            final String title = a.getItems().get(0).getTitle();  //标题
                        }else {
                            final String name = materialCategoryMapper.selectByPrimaryKey(a.getMaterialCategoryId()).getName();
                            a.setMaterialCategoryName(name);
                        }
                        arrayList.add(a);
                        return null;
                    }).collect(Collectors.toList());
            final Map<String, List<MaterialDto>> collect = arrayList.stream()
                    .collect(Collectors.groupingBy(MaterialDto::getMaterialCategoryName));


            System.out.println("ceshi ");
        return representation(Message.SUCCESS, collect);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }



}
