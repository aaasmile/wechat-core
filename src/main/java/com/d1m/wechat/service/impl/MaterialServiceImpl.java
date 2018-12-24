package com.d1m.wechat.service.impl;

import cn.d1m.wechat.client.core.WxResponse;
import cn.d1m.wechat.client.model.WxArticle;
import cn.d1m.wechat.client.model.WxMaterial;
import cn.d1m.wechat.client.model.WxMedia;
import cn.d1m.wechat.client.model.WxMessage;
import cn.d1m.wechat.client.model.common.WxHolder;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.common.rest.RestResponse;
import com.d1m.wechat.component.FileUploadConfig;
import com.d1m.wechat.controller.file.Upload;
import com.d1m.wechat.dto.ImageTextDto;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.dto.MiniProgramDto;
import com.d1m.wechat.exception.BusinessException;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.MaterialImageTextDetailMapper;
import com.d1m.wechat.mapper.MaterialMapper;
import com.d1m.wechat.mapper.MaterialMiniProgramMapper;
import com.d1m.wechat.mapper.WechatMapper;
import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.MaterialStatus;
import com.d1m.wechat.model.enums.MaterialType;
import com.d1m.wechat.pamametermodel.*;
import com.d1m.wechat.service.ConversationService;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.util.HtmlUtils;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.util.ParamUtil;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.wechatclient.WechatTokenRestService;
import com.d1m.wechat.wechatclient.WechatTokenRestServiceV1;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections4.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.io.File;
import java.util.*;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

@Slf4j
@Service
public class MaterialServiceImpl extends BaseService<Material> implements MaterialService {

    private static final String WEIXIN_DELETE_MATERIAL = "https://api.weixin.qq.com/cgi-bin/material/del_material";

    @Resource
    private MaterialMapper materialMapper;

    @Resource
    private MaterialImageTextDetailMapper materialImageTextDetailMapper;

    @Resource
    private MaterialMiniProgramMapper materialMiniProgramMapper;

    @Resource
    private MemberService memberService;

    @Resource
    private ConversationService conversationService;

    @Resource
    private WechatTokenRestService wechatTokenRestService;

    @Resource
    private WechatMapper wechatMapper;

    @Resource
    private WechatTokenRestServiceV1 wechatTokenRestServiceV1;

    @Autowired
    public RestTemplate restTemplate;


    public void setMaterialMapper(MaterialMapper materialMapper) {
        this.materialMapper = materialMapper;
    }

    public void setMaterialImageTextDetailMapper(
     MaterialImageTextDetailMapper materialImageTextDetailMapper) {
        this.materialImageTextDetailMapper = materialImageTextDetailMapper;
    }

    @Override
    public MaterialDto getImageText(Integer wechatId, Integer id) {
        MaterialDto materialDto = materialMapper.getImageText(wechatId, id);
        if (Objects.nonNull(materialDto)) {
            return materialDto;
        }
        final List<ImageTextDto> imageTextDtos = materialMapper.getImageTextDetail(ImmutableMap.of("materialId", id));
        if (CollectionUtils.isNotEmpty(imageTextDtos)) {
            materialDto = new MaterialDto();
            BeanUtils.copyProperties(imageTextDtos.get(0), materialDto);
            materialDto.setItems(imageTextDtos);
        }
        return materialDto;
    }

    @Override
    public Material createImage(Integer wechatId, User user, Upload upload)
     throws WechatException {
        notBlank(upload.getAccessPath(), Message.MATERIAL_IMAGE_NOT_BLANK);
        Date current = new Date();
        Material material = createMaterialImage(wechatId, user, upload, current);


        WxMedia wxMedia = WechatClientDelegate.uploadMedia(wechatId, "image", new File(upload.getAbsolutePath()));
        material.setMediaId(wxMedia.getMediaId());
        material.setLastPushAt(current);
        materialMapper.insert(material);
        return material;
    }

    @Override
    public Material createMediaImage(Integer wechatId, User user, Upload upload) {
        notBlank(upload.getAccessPath(), Message.MATERIAL_IMAGE_NOT_BLANK);
        Date current = new Date();
        Material material = createMediaImage(wechatId, user, upload, current,
         MaterialType.MEDIAIMAGE.getValue());

        WxHolder<String> wxURL = WechatClientDelegate.uploadImg(wechatId, new File(upload.getAbsolutePath()));
        if (wxURL.fail()) {
            throw new WechatException(Message.SYSTEM_ERROR);
        }
        material.setWxPicUrl(wxURL.get());
        material.setLastPushAt(current);
        materialMapper.insert(material);
        return material;
    }

    private Material createMediaImage(Integer wechatId, User user,
                                      Upload upload, Date current, Byte materialType) {
        Material material = new Material();
        material.setCreatedAt(current);
        material.setCreatorId(user.getId());
        material.setMaterialType(materialType);
        material.setName(upload.getFileName());
        material.setPicUrl(upload.getAccessPath());
        material.setStatus(MaterialStatus.INUSED.getValue());
        material.setWechatId(wechatId);
        return material;
    }

    /**
     * 暂时先注释门店图片上传
     *
     * @param wechatId
     * @param user
     * @param upload
     * @return
     */
    @Override
    public Material createOutletImage(Integer wechatId, User user, Upload upload) {
        notBlank(upload.getAccessPath(), Message.MATERIAL_IMAGE_NOT_BLANK);
        Date current = new Date();
        Material material = createMediaImage(wechatId, user, upload, current,
         MaterialType.OUTLETIMAGE.getValue());

//		WxUploadImg wxUploadImg = JwShopAPI.uploadImg(RefreshAccessTokenJob.getAccessTokenStr(wechatId),
//				upload.getAbsolutePath());
//		if (wxUploadImg == null) {
//			throw new WechatException(Message.BUSINESS_WEIXIN_PHOTO_UPLOAD_FAIL);
//		}
//		material.setWxPicUrl(wxUploadImg.getUrl());
        material.setLastPushAt(current);
        material.setModifyAt(current);
        materialMapper.insert(material);
        return material;
    }

    @Override
    public Material createMaterialImage(Integer wechatId, User user,
                                        Upload upload) throws WechatException {
        notBlank(upload.getAccessPath(), Message.MATERIAL_IMAGE_NOT_BLANK);
        Date current = new Date();
        Material material = createMaterialImage(wechatId, user, upload, current);
        //TODO 此处根据环境判断,如果是上线前的部署环境,不上传到微信服务器,后面通过定时任务处理
        if ("0".equals(FileUploadConfig.getValue(wechatId, "running.env"))) {
            WxMaterial wxMaterial = WechatClientDelegate.addMaterial(wechatId, "image", new File(upload.getAbsolutePath()));
            if (wxMaterial.fail()) {
                throw new WechatException(Message.SYSTEM_ERROR);
            }
            material.setMediaId(wxMaterial.getMediaId());
            material.setWxPicUrl(wxMaterial.getUrl());
        }

        material.setLastPushAt(current);
        materialMapper.insert(material);
        return material;
    }

    private Material createMaterialImage(Integer wechatId, User user,
                                         Upload upload, Date current) {
        Material material = new Material();
        material.setCreatedAt(current);
        material.setCreatorId(user.getId());
        material.setMaterialType(MaterialType.IMAGE.getValue());
        material.setName(upload.getFileName());
        material.setPicUrl(upload.getAccessPath());
        material.setStatus(MaterialStatus.INUSED.getValue());
        material.setWechatId(wechatId);
        return material;
    }

    @Override
    public Material createMaterialVideo(Integer wechatId, User user,
                                        Upload upload) throws WechatException {
        notBlank(upload.getAccessPath(), Message.MATERIAL_IMAGE_NOT_BLANK);
        Material material = new Material();
        material.setCreatedAt(new Date());
        material.setCreatorId(user.getId());
        material.setMaterialType(MaterialType.VIDEO.getValue());
        material.setName(upload.getAccessPath().substring(
         upload.getAccessPath().lastIndexOf("_") + 1,
         upload.getAccessPath().lastIndexOf(".")));
        material.setVideoUrl(upload.getAccessPath());
        material.setStatus(MaterialStatus.INUSED.getValue());
        material.setWechatId(wechatId);

        // FIXME:  上传video却用的是MaterialType.IMAGE
        //WxUpload wxUpload = JwMediaAPI.uploadMedia(
        //		RefreshAccessTokenJob.getAccessTokenStr(wechatId),
        //		MaterialType.IMAGE.name().toLowerCase(),
        //		upload.getAbsolutePath());
        //
        //material.setMediaId(wxUpload.getMedia_id());
        materialMapper.insert(material);
        return material;
    }

    @Override
    public Material createImageText(Integer wechatId, User user,
                                    MaterialModel materialModel) throws WechatException {
        List<ImageTextModel> imageTexts = materialModel.getImagetexts();
        if (imageTexts == null || imageTexts.isEmpty()) {
            throw new WechatException(Message.MATERIAL_IMAGE_TEXT_NOT_BLANK);
        }
        Date current = new Date();
        List<MaterialImageTextDetail> materialImageTextDetails = getMaterialImageTextDetails(
         wechatId, null, imageTexts, current);
        Material material = new Material();
        material.setCreatedAt(current);
        material.setCreatorId(user.getId());
        material.setModifyAt(current);
        material.setModifyById(user.getId());
        material.setMaterialType(MaterialType.IMAGE_TEXT.getValue());
        material.setStatus(MaterialStatus.INUSED.getValue());
        material.setWechatId(wechatId);
//		material.setComment(materialModel.getComment());
        materialMapper.insert(material);
        for (MaterialImageTextDetail materialImageTextDetail : materialImageTextDetails) {
            materialImageTextDetail.setMaterialId(material.getId());
            materialImageTextDetail.setStatus((byte) 1);
        }
        if (!materialImageTextDetails.isEmpty()) {
            materialImageTextDetailMapper.insertList(materialImageTextDetails);
        }
        return material;
    }

    /**
     * 删除图片
     *
     * @param wechatId
     * @param materialId
     * @return
     * @throws WechatException
     */
    @Override
    public JSONObject deleteImage(Integer wechatId, Integer materialId)
     throws BusinessException {
        MaterialDto materialDto = materialMapper.getUsedImageText(wechatId, materialId);
        notBlank(materialDto, Message.MATERIAL_NOT_EXIST);
        if (CollectionUtils.isNotEmpty(materialDto.getItems()))
            return response(Message.MATERIAL_UNABLE_DELETE, materialDto.getItems());
        Material material = new Material();
        material.setId(materialId);
        material.setStatus((byte) 0);
        int res = materialMapper.update(material);
        // 删除微信上的永久素材
        if (res > 0) {
            if (StringUtils.isNotBlank(materialDto.getMediaId())) {
                Wechat wechat = new Wechat();
                wechat.setId(wechatId);
                Wechat wt = wechatMapper.selectByPrimaryKey(wechat);
                if (wt != null) {
                    String accessToken = getAccesToken(wt.getAppid(), wt.getAppscret());
                    log.info("返回结果：{}", accessToken);
                    if (StringUtils.isNotBlank(accessToken)) {
                        String result = deleteMaterial(accessToken, materialDto.getMediaId());
                        log.info("删除微信上的永久素材返回结果：{}", JSON.toJSON(result));
                        JSONObject jsonObject = JSONObject.parseObject(result);
                        String errcode = (String) jsonObject.get("errcode");
                        if (!errcode.equals("0")) {
                            throw new BusinessException(Message.MATERIAL_WX_NOT_DELETE, materialDto.getName());
                        }
                    }
                }
            }
        }
        return response(Message.MATERIAL_IMAGE_DELETE_SUCCESS, null);
    }


    private String deleteMaterial(String accessToken, String mediaId) {
        log.info("删除永久素材请求入参：accessToken:{},mediaId:{}", accessToken, mediaId);
        if (StringUtils.isEmpty(accessToken)) {
            log.info("accessToken不能为空");
            return null;
        }

        if (StringUtils.isEmpty(mediaId)) {
            log.info("mediaId不能为空");
            return null;
        }

        Map<String, Object> param = new HashMap<>();
        //param.put("accessToken",accessToken);
        param.put("media_id", mediaId);
        String url = WEIXIN_DELETE_MATERIAL + "?access_token=" + accessToken;
        log.debug("请求url：{}", url);
        return restTemplate.postForObject(url, param, String.class);
    }

    /**
     * 获取accessToken
     *
     * @param appid
     * @param appscret
     * @return
     */
    private String getAccesToken(String appid, String appscret) {
        String accessToken = null;
        try {
            String result = restTemplate.getForObject(System.getProperty("wechat_token_server") + "/access-token/" + appid + "/" + appscret, String.class);
            log.info("请求获取accessToken接口返回：{}", result);
            JSONObject jsonObject = JSONObject.parseObject(result);
            accessToken = (String) jsonObject.get("data");
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return accessToken;
    }

    private JSONObject response(Message msg, Object data) {
        return getJsonObject(msg, data);
    }

    public static JSONObject getJsonObject(Message msg, Object data) {
        JSONObject json = new JSONObject();
        json.put("resultCode", msg.getCode().toString());
        json.put("msg", msg.name());
        json.put("data", data);
        return json;
    }

    @Override
    public void deleteImageText(Integer wechatId, User user, Integer id)
     throws WechatException {
        notBlank(id, Message.MATERIAL_ID_NOT_BLANK);
        MaterialDto materialDto = materialMapper.getImageText(wechatId, id);
        Material material = new Material();
        material.setWechatId(wechatId);
        material.setId(id);
        material.setStatus((byte) 1);
        material = materialMapper.selectOne(material);
        notBlank(material, Message.MATERIAL_NOT_EXIST);
        material.setStatus((byte) 0);
        int res = materialMapper.updateByPrimaryKey(material);
        // 删除微信上的永久素材
        if (res > 0) {
            if (StringUtils.isNotBlank(material.getMediaId())) {
                WechatClientDelegate.deleteMaterial(wechatId, material.getMediaId());
            }
        }

        //set disabled status to all imageTexts of this material
        if (materialDto != null) {
            List<ImageTextDto> items = materialDto.getItems();
            for (ImageTextDto imageText : items) {
                MaterialImageTextDetail imageTextData = new MaterialImageTextDetail();
                imageTextData.setWechatId(wechatId);
                imageTextData.setId(imageText.getId());
                imageTextData.setStatus((byte) 1);
                imageTextData = materialImageTextDetailMapper.selectOne(imageTextData);
                notBlank(imageTextData, Message.MATERIAL_NOT_EXIST);
                imageTextData.setStatus((byte) 0);
                materialImageTextDetailMapper.updateByPrimaryKey(imageTextData);
            }
        }
    }

    @Override
    @Deprecated
    public void deleteImageTextDetail(Integer wechatId, User user, Integer id)
     throws WechatException {
        notBlank(id, Message.MATERIAL_IMAGE_TEXT_DETAIL_ID_NOT_BLANK);
        MaterialImageTextDetail detail = new MaterialImageTextDetail();
        detail.setWechatId(wechatId);
        detail.setId(id);
        detail.setStatus((byte) 1);
        detail = materialImageTextDetailMapper.selectOne(detail);
        notBlank(detail, Message.MATERIAL_IMAGE_TEXT_DETAIL_NOT_EXIST);
        detail.setStatus((byte) 0);
        // TODO inused??

        materialImageTextDetailMapper.updateByPrimaryKey(detail);
    }

    @Override
    public void renameImage(Integer wechatId, User user, Integer id, String name)
     throws WechatException {
        notBlank(name, Message.MATERIAL_IMAGE_NAME_NOT_BLANK);
        Material material = getMaterial(wechatId, id);
        notBlank(material, Message.MATERIAL_NOT_EXIST);
        material.setName(name);
        materialMapper.updateByPrimaryKey(material);
    }

    @Override
    @Deprecated
    public MaterialImageTextDetail updateImageTextDetail(Integer wechatId,
                                                         User user, Integer imageTextDetailId,
                                                         MaterialImageTextDetail materialImageTextDetail, boolean pushToWx)
     throws WechatException {
        notBlank(imageTextDetailId,
         Message.MATERIAL_IMAGE_TEXT_DETAIL_ID_NOT_BLANK);
        notBlank(materialImageTextDetail,
         Message.MATERIAL_IMAGE_TEXT_DETAIL_ID_NOT_BLANK);
        notBlank(materialImageTextDetail.getTitle(),
         Message.MATERIAL_IMAGE_TEXT_DETAIL_TITLE_NOT_BLANK);
        notBlank(materialImageTextDetail.getContent(),
         Message.MATERIAL_IMAGE_TEXT_DETAIL_CONTENT_NOT_BLANK);
        notBlank(materialImageTextDetail.getMaterialCoverId(),
         Message.MATERIAL_IMAGE_TEXT_DETAIL_THUMB_MEDIA_NOT_BLANK);
        Material materialCover = new Material();
        materialCover.setId(materialImageTextDetail.getMaterialCoverId());
        materialCover.setWechatId(wechatId);
        materialCover.setMaterialType(MaterialType.IMAGE.getValue());
        materialCover = materialMapper.selectOne(materialCover);
        notBlank(materialCover, Message.MATERIAL_IMAGE_NOT_EXIST);
        if (materialImageTextDetail.getContentSourceChecked()) {
            notBlank(
             materialImageTextDetail.getContentSourceUrl(),
             Message.MATERIAL_IMAGE_TEXT_DETAIL_CONTENT_SOURCE_URL_NOT_BLANK);
        }

        MaterialImageTextDetail record = new MaterialImageTextDetail();
        record.setId(imageTextDetailId);
        record.setWechatId(wechatId);
        record = materialImageTextDetailMapper.selectOne(record);
        notBlank(record, Message.MATERIAL_IMAGE_TEXT_DETAIL_NOT_BLANK);
        record.setAuthor(materialImageTextDetail.getAuthor());
        record.setContent(materialImageTextDetail.getContent());
        record.setContentSourceChecked(materialImageTextDetail
         .getContentSourceChecked());
        record.setContentSourceUrl(materialImageTextDetail
         .getContentSourceUrl());
        record.setMaterialCoverId(materialImageTextDetail.getMaterialCoverId());
        record.setShowCover(materialImageTextDetail.getShowCover());
        record.setSummary(StringUtils.isBlank(materialImageTextDetail
         .getSummary()) ? StringUtils.substring(
         materialImageTextDetail.getContent(), 0, 54)
         : materialImageTextDetail.getSummary());
        record.setTitle(materialImageTextDetail.getTitle());
        record.setComment(materialImageTextDetail.getComment());
        materialImageTextDetailMapper.updateByPrimaryKey(record);
        // Material material = getMaterial(wechatId, record.getMaterialId());
        // if (pushToWx && StringUtils.isNotBlank(material.getMediaId())) {
        // MaterialDto imageText = getImageText(wechatId, material.getId());
        // WxUpdateArticle wxUpdateArticle = new WxUpdateArticle();
        // wxUpdateArticle.setArticle(article);
        // wxUpdateArticle.setIndex(index);
        // wxUpdateArticle.setMedia_id(media_id);

        // JwMediaAPI
        // .updateArticlesByMaterial(
        // AccessTokenThread.getAccessToken(wechatId)
        // .getAccessToken(), wxUpdateArticle);
        // }
        return record;
    }

    @Override
    public Material updateImageText(Integer wechatId, User user, Integer id,
                                    MaterialModel materialModel) throws WechatException {
        Material material = getMaterial(wechatId, id);
        notBlank(material, Message.MATERIAL_NOT_EXIST);
        List<ImageTextModel> imagetexts = materialModel.getImagetexts();
        if (imagetexts == null || imagetexts.isEmpty()) {
            throw new WechatException(Message.MATERIAL_IMAGE_TEXT_NOT_BLANK);
        }
        Date current = new Date();
        List<MaterialImageTextDetail> materialImageTextDetails = getMaterialImageTextDetails(
         wechatId, id, imagetexts, current);
        MaterialImageTextDetail detail = new MaterialImageTextDetail();
        detail.setWechatId(wechatId);
        detail.setStatus((byte) 1);
        detail.setMaterialId(id);
        List<MaterialImageTextDetail> existDetails = materialImageTextDetailMapper
         .select(detail);
        List<MaterialImageTextDetail> deleteList = new ArrayList<MaterialImageTextDetail>();
        List<MaterialImageTextDetail> addList = new ArrayList<MaterialImageTextDetail>();
        for (MaterialImageTextDetail materialImageTextDetail : existDetails) {
            if (!contains(materialImageTextDetails, materialImageTextDetail)) {
                deleteList.add(materialImageTextDetail);
            }
        }
        for (MaterialImageTextDetail materialImageTextDetail : materialImageTextDetails) {
            if (!contains(existDetails, materialImageTextDetail)) {
                addList.add(materialImageTextDetail);
                continue;
            }
            detail = materialImageTextDetailMapper
             .selectByPrimaryKey(materialImageTextDetail.getId());
            detail.setAuthor(materialImageTextDetail.getAuthor());
            detail.setContent(materialImageTextDetail.getContent());
            detail.setContentSourceChecked(materialImageTextDetail
             .getContentSourceChecked());
            detail.setContentSourceUrl(materialImageTextDetail
             .getContentSourceUrl());
            detail.setShowCover(materialImageTextDetail.getShowCover());
            detail.setSummary(materialImageTextDetail.getSummary());
            detail.setTitle(materialImageTextDetail.getTitle());
            detail.setSequence(materialImageTextDetail.getSequence());
            detail.setMaterialCoverId(materialImageTextDetail
             .getMaterialCoverId());
            detail.setComment(materialImageTextDetail.getComment());
            materialImageTextDetailMapper.updateByPrimaryKey(detail);
        }
        for (MaterialImageTextDetail materialImageTextDetail : deleteList) {
            materialImageTextDetail.setStatus((byte) 0);
            materialImageTextDetailMapper
             .updateByPrimaryKey(materialImageTextDetail);
        }
        for (MaterialImageTextDetail materialImageTextDetail : addList) {
            materialImageTextDetail.setStatus((byte) 1);
            materialImageTextDetail.setMaterialId(id);
        }
        if (!addList.isEmpty()) {
            materialImageTextDetailMapper.insertList(addList);
        }
        material.setModifyAt(current);
        material.setModifyById(user.getId());
//		material.setComment(materialModel.getComment());
        materialMapper.updateByPrimaryKey(material);
        return material;
    }

    @Override
    public void updateMaterialAndImageText(Material material, List<MaterialImageTextDetail> imageTextDetail) {
        final int result1 = materialMapper.updateByPrimaryKeySelective(material);
        int result2 = 0;
        int collectionSize = -1;
        if (CollectionUtils.isNotEmpty(imageTextDetail)) {
            result2 = imageTextDetail.stream().mapToInt(materialImageTextDetailMapper::updateByPrimaryKeySelective).sum();
            collectionSize = imageTextDetail.size();
        }
        if (result1 != 1 || result2 != collectionSize) {
            throw new WechatException(Message.MATERIAL_IMAGE_TEXT_DETAIL_NOT_EXIST);
        }
    }

    public Material getMaterial(Integer wechatId, Integer id) {
        Material material = new Material();
        material.setWechatId(wechatId);
        material.setId(id);
        material.setStatus(MaterialStatus.INUSED.getValue());
        material = materialMapper.selectOne(material);
        return material;
    }

    private boolean contains(List<MaterialImageTextDetail> list,
                             MaterialImageTextDetail detail) {
        if (detail == null) {
            return false;
        }
        for (MaterialImageTextDetail materialImageTextDetail : list) {
            if (null != materialImageTextDetail.getId() && materialImageTextDetail.getId().equals(detail.getId())) {
                return true;
            }
        }
        return false;
    }

    public List<MaterialImageTextDetail> getMaterialImageTextDetails(
     Integer wechatId, Integer materialId,
     List<ImageTextModel> imageTexts, Date current)
     throws WechatException {
        List<MaterialImageTextDetail> materialImageTextDetails = new ArrayList<MaterialImageTextDetail>();
        MaterialImageTextDetail detail, existDetail = null;
        String title, author, content, contentSourceUrl, summary = null;
        Integer materialCoverId = null;
        Boolean showCover, contentSourceUrlChecked = null;
        Material materialCover = null;
        Integer materialImageTextDetailId = null;
        int sequence = 1;
        for (ImageTextModel imageTextModel : imageTexts) {
            title = imageTextModel.getTitle();
            content = imageTextModel.getContent();
            materialCoverId = imageTextModel.getMaterialCoverId();
            notBlank(title, Message.MATERIAL_IMAGE_TEXT_DETAIL_TITLE_NOT_BLANK);
            notBlank(content,
             Message.MATERIAL_IMAGE_TEXT_DETAIL_CONTENT_NOT_BLANK);
            notBlank(materialCoverId,
             Message.MATERIAL_IMAGE_TEXT_DETAIL_THUMB_MEDIA_NOT_BLANK);
            materialCover = new Material();
            materialCover.setId(materialCoverId);
            materialCover.setWechatId(wechatId);
            materialCover.setMaterialType(MaterialType.IMAGE.getValue());
            materialCover.setStatus(MaterialStatus.INUSED.getValue());
            materialCover = materialMapper.selectOne(materialCover);
            notBlank(materialCover, Message.MATERIAL_IMAGE_NOT_EXIST);
            author = imageTextModel.getAuthor();
            contentSourceUrlChecked = ParamUtil.getBoolean(
             imageTextModel.getContentSourceChecked(), false);
            contentSourceUrl = imageTextModel.getContentSourceUrl();
            summary = imageTextModel.getSummary();
            showCover = imageTextModel.isShowCover();
            if (contentSourceUrlChecked) {
                notBlank(
                 contentSourceUrl,
                 Message.MATERIAL_IMAGE_TEXT_DETAIL_CONTENT_SOURCE_URL_NOT_BLANK);
            }
            detail = new MaterialImageTextDetail();
            detail.setAuthor(author);
            detail.setContent(content);
            detail.setContentSourceChecked(contentSourceUrlChecked);
            detail.setContentSourceUrl(contentSourceUrl);
            detail.setShowCover(showCover);
            detail.setMaterialCoverId(materialCoverId);
            detail.setComment(imageTextModel.getComment());
            if (StringUtils.isBlank(summary)) {
                String delHTMLTagContent = HtmlUtils.delHTMLTag(content);
                if (delHTMLTagContent.length() > 54) {
                    delHTMLTagContent = delHTMLTagContent.substring(0, 54);
                }
                detail.setSummary(delHTMLTagContent);
            } else {
                detail.setSummary(summary);
            }
            detail.setTitle(title);
            detail.setWechatId(wechatId);
            if (materialId != null) {
                materialImageTextDetailId = imageTextModel.getId();
                if (materialImageTextDetailId != null) {
                    existDetail = materialImageTextDetailMapper
                     .selectByPrimaryKey(materialImageTextDetailId);
                    if (!existDetail.getMaterialId().equals(materialId)) {
                        throw new WechatException(
                         Message.MATERIAL_IMAGE_TEXT_DETAIL_NOT_BELONGS_TO_MATERIAL);
                    }
                    detail.setId(materialImageTextDetailId);
                }
            }
            detail.setSequence(sequence);
            materialImageTextDetails.add(detail);
            sequence++;
        }
        return materialImageTextDetails;
    }

    @Override
    public Page<MaterialDto> searchImageText(Integer wechatId,
                                             ImageTextModel imageTextModel, boolean queryCount) {
        if (imageTextModel == null) {
            imageTextModel = new ImageTextModel();
        }
        PageHelper.startPage(imageTextModel.getPageNum(),
         imageTextModel.getPageSize(), queryCount);
        final HashMap<String, Object> params = Maps.newHashMap();
        params.put("wechatId", wechatId);
        params.put("query", imageTextModel.getQuery());
        params.put("pushed", imageTextModel.getPushed());
        params.put("materialCategoryId", imageTextModel.getMaterialCategoryId());

        return materialMapper.searchImageText(params);
    }

    @Override
    public Page<MaterialDto> searchImage(Integer wechatId,
                                         ImageModel imageModel, boolean queryCount) {
        if (imageModel == null) {
            imageModel = new ImageModel();
        }
        PageHelper.startPage(imageModel.getPageNum(), imageModel.getPageSize(),
         queryCount);
        return materialMapper.searchImage(wechatId,
         imageModel.getMaterialImageTypeId(), imageModel.getQuery(),
         imageModel.getPushed(), imageModel.getMaterialType());
    }

    @Override
    public Page<MaterialDto> searchVoice(Integer wechatId,
                                         Integer materialType, String sortName, String sortDir,
                                         Integer pageNum, Integer pageSize, boolean queryCount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<MaterialDto> searchVideo(Integer wechatId,
                                         Integer materialType, String sortName, String sortDir,
                                         Integer pageNum, Integer pageSize, boolean queryCount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Page<MaterialDto> searchLittleVideo(Integer wechatId,
                                               Integer materialType, String sortName, String sortDir,
                                               Integer pageNum, Integer pageSize, boolean queryCount) {
        // TODO Auto-generated method stub
        return null;
    }

    @Override
    public Mapper<Material> getGenericMapper() {
        return materialMapper;
    }

    @Override
    public synchronized void pushMaterialImageTextToWx(Integer wechatId,
                                                       User user, Integer materialId) {
        MaterialDto imageText = getImageText(wechatId, materialId);
        notBlank(imageText, Message.MATERIAL_NOT_EXIST);
        if (imageText.getItems() == null || imageText.getItems().isEmpty()) {
            throw new WechatException(Message.MATERIAL_IMAGE_TEXT_NOT_BLANK);
        }
        Material material = getMaterial(wechatId, materialId);
        if (StringUtils.isNotBlank(material.getMediaId())) {
            WechatClientDelegate.deleteMaterial(wechatId, material.getMediaId());
        }
        List<WxArticle> wxArticles = new ArrayList<>();
        WxArticle wxArticle = null;
        List<ImageTextDto> items = imageText.getItems();

        String patternString = "(?i)<img(.*?)src=\"(.*?)\"\\s*data-wx-src=\"(.*?)\"(.*?)>";
        String backgroundPatternString = "(.*?)url\\(&quot;(.*?)&quot;(.*?)background-wx-src=\"(.*?)\"(.*?)";
        for (ImageTextDto imageTextDto : items) {
            wxArticle = new WxArticle();
            wxArticle.setAuthor(imageTextDto.getAuthor());
            String textContent = imageTextDto.getContent();
            textContent = textContent.replaceAll(patternString, "<img $1 src=\"$3\" data-wx-src=\"$2\"$4>");
            textContent = textContent.replaceAll(backgroundPatternString, "$1url\\(&quot;$4&quot;$3background-wx-src=\"$2\"$5");
            wxArticle.setContent(textContent);
            wxArticle.setContentSourceUrl(imageTextDto.getContentSourceUrl());
            wxArticle.setDigest(imageTextDto.getSummary());
            wxArticle.setShowCoverPic(imageTextDto.isShowCover() ? "1" : "0");
            wxArticle.setThumbMediaId(imageTextDto.getMaterialCoverMediaId());
            wxArticle.setTitle(imageTextDto.getTitle());
            int comment = 0;
            wxArticle.setNeedOpenComment(0);
            wxArticle.setOnlyFansCanComment(0);
            if (imageTextDto.getComment() != null) {
                comment = imageTextDto.getComment();
            }
            if (comment > 0)
                wxArticle.setNeedOpenComment(1);
            if (comment > 1)
                wxArticle.setOnlyFansCanComment(1);
            wxArticles.add(wxArticle);
        }
        WxMedia wxMedia = WechatClientDelegate.addNews(wechatId, wxArticles);
        material.setMediaId(wxMedia.getMediaId());
        Date current = new Date();
        material.setLastPushAt(current);
        material.setModifyAt(current);
        material.setModifyById(user.getId());
        materialMapper.updateByPrimaryKey(material);
    }

    @Override
    public void checkMaterialInvalidInWeiXin(Integer wechatId, String mediaId) {

        boolean exist = WechatClientDelegate.getMaterial(wechatId, mediaId).success();
        if (!exist) {
            throw new WechatException(Message.MATERIAL_NOT_EXIST_IN_WX);
        }
    }

    @Deprecated
    @Override
    public void previewMaterial(Integer wechatId, MaterialModel materialModel) {
        Integer id = materialModel.getId();
        notBlank(id, Message.MATERIAL_ID_NOT_BLANK);
        notBlank(materialModel.getMemberId(), Message.MEMBER_ID_NOT_EMPTY);
        Member member = memberService.getMember(wechatId,
         materialModel.getMemberId());
        notBlank(member, Message.MEMBER_NOT_EXIST);

        Material material = getMaterial(wechatId, id);
        if (material.getLastPushAt() == null
         || material.getModifyAt() == null
         || material.getLastPushAt().compareTo(material.getModifyAt()) != 0
         || StringUtils.isBlank(material.getMediaId())) {
            ConversationModel conversationModel = new ConversationModel();
            conversationModel.setMaterialId(materialModel.getId());
            conversationModel.setMemberId(materialModel.getMemberId());
            User user = (User) SecurityUtils.getSubject().getPrincipal();
//            conversationService.wechatToMember(wechatId, user, conversationModel);
            return;
        }

        WxMessage wxMessage = WechatClientDelegate.previewMessage(wechatId, member.getOpenId(), "mpnews", material.getMediaId());
        if (wxMessage.fail()) {
            throw new WechatException(Message.WEIXIN_HTTPS_REQUEST_ERROR);
        }
    }

    @Override
    @Transactional
    public Material createMiniProgram(Integer userId, Integer wechatId, MaterialModel materialModel) {
        Material material = new Material();
        material.setCreatedAt(new Date());
        material.setCreatorId(userId);
        material.setMaterialType(MaterialType.MINI_PROGRAM.getValue());

        MiniProgramModel miniProgramModel = materialModel.getMiniProgram();
        material.setName(miniProgramModel.getTitle());
        material.setStatus(MaterialStatus.INUSED.getValue());
        material.setWechatId(wechatId);

        materialMapper.insert(material);

        MaterialMiniProgram materialMiniProgram = new MaterialMiniProgram();
        // 小程序的属性
        materialMiniProgram.setAppid(miniProgramModel.getAppid());
        materialMiniProgram.setTitle(miniProgramModel.getTitle());
        materialMiniProgram.setPagepath(miniProgramModel.getPagepath());
        materialMiniProgram.setThumbMaterialId(miniProgramModel.getThumbMaterialId());
        // 素材相关属性
        materialMiniProgram.setMaterialId(material.getId());
        materialMiniProgram.setWechatId(material.getWechatId());
        materialMiniProgram.setCreatedAt(material.getCreatedAt());
        materialMiniProgram.setCreatorId(material.getCreatorId());
        materialMiniProgram.setStatus(material.getStatus());
        materialMiniProgramMapper.insert(materialMiniProgram);

        return material;
    }

    @Override
    public int updateMiniProgram(Integer userId, Integer wechatId, MaterialModel materialModel) {
        MiniProgramModel miniProgramModel = materialModel.getMiniProgram();
        MaterialMiniProgram materialMiniProgram = materialMiniProgramMapper.selectByPrimaryKey(miniProgramModel.getId());
        if (materialMiniProgram == null) {
            return 0;
        }
        materialMiniProgram.setAppid(miniProgramModel.getAppid());
        materialMiniProgram.setTitle(miniProgramModel.getTitle());
        materialMiniProgram.setPagepath(miniProgramModel.getPagepath());
        materialMiniProgram.setThumbMaterialId(miniProgramModel.getThumbMaterialId());

        int ret = materialMiniProgramMapper.updateByPrimaryKeySelective(materialMiniProgram);

        Material material = materialMapper.selectByPrimaryKey(materialMiniProgram.getMaterialId());
        Material updateMaterial = new Material();
        updateMaterial.setId(material.getId());
        updateMaterial.setModifyById(userId);
        updateMaterial.setModifyAt(new Date());
        materialMapper.updateByPrimaryKeySelective(updateMaterial);
        return ret;
    }

    @Override
    public int deleteMiniProgram(Integer userId, Integer wechatId, Integer miniProgramId) {
        MaterialMiniProgram materialMiniProgram = materialMiniProgramMapper.selectByPrimaryKey(miniProgramId);
        if (materialMiniProgram == null) {
            return 0;
        }
        MaterialMiniProgram update = new MaterialMiniProgram();
        update.setId(materialMiniProgram.getId());
        update.setStatus(MaterialStatus.INUSED.getValue());
        int ret = materialMiniProgramMapper.updateByPrimaryKeySelective(update);

        Material material = materialMapper.selectByPrimaryKey(materialMiniProgram.getMaterialId());
        Material updateMaterial = new Material();
        updateMaterial.setId(material.getId());
        updateMaterial.setModifyById(userId);
        updateMaterial.setModifyAt(new Date());
        updateMaterial.setStatus(MaterialStatus.INUSED.getValue());
        materialMapper.updateByPrimaryKeySelective(updateMaterial);
        return ret;
    }

    @Override
    public Page<MiniProgramDto> searchMiniProgram(MiniProgramModel miniProgramModel, boolean queryCount) {
        PageHelper.startPage(miniProgramModel.getPageNum(), miniProgramModel.getPageSize(), queryCount);
        return materialMapper.searchMiniProgram(miniProgramModel);
    }

    @Override
    public MiniProgramDto getMiniProgramByMaterialId(Integer wechatId, Integer materialId) {
        return materialMapper.getMiniProgramByMaterialId(wechatId, materialId);
    }

}
