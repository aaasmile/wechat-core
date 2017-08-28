package com.d1m.wechat.controller.material;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.controller.file.Upload;
import com.d1m.wechat.controller.file.UploadController;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.dto.MaterialImageTextDetailDto;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.pamametermodel.ImageModel;
import com.d1m.wechat.pamametermodel.ImageTextModel;
import com.d1m.wechat.pamametermodel.MaterialModel;
import com.d1m.wechat.service.MaterialImageTextDetailService;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.Message;

@Controller
@RequestMapping("/material")
public class MaterialController extends BaseController {

	private Logger log = LoggerFactory.getLogger(MaterialController.class);

	@Autowired
	private MaterialService materialService;

	@Autowired
	private MaterialImageTextDetailService materialImageTextDetailService;

	/**
	 * 图片素材推送到微信
	 * @param materialModel
	 * @param session
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping(value = "pushwx.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("app-msg:list")
	public JSONObject pushToWx(
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
	 * @param materialModel
	 * @param session
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping(value = "preview.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("app-msg:list")
	public JSONObject previewMaterial(
			@RequestBody(required = false) MaterialModel materialModel,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			materialService
					.previewMaterial(getWechatId(session), materialModel);
			return representation(Message.MATERIAL_IMAGE_TEXT_PUSH_WX_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	/**
	 * 图片素材列表
	 * @param imageModel
	 * @param session
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping(value = "image/list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value={"app-msg:list","member:list","message:message-list"},logical=Logical.OR)
	public JSONObject searchImage(
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
	 *
	 *  图文列表
	 * query : 标题/作者/简介 模糊查询
	 * @param imageTextModel
	 * @param session
	 * @param request
	 * @param response
     * @return
     */
	@RequestMapping(value = "imagetext/list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("app-msg:list")
	public JSONObject searchImageText(
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
	 *
	 * @param file
	 * @param request
	 * @param response
	 * @param session
     * @return
     */
	@RequestMapping(value = "image/new.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("app-msg:list")
	public JSONObject uploadImage(
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
	
	@RequestMapping(value = "mediaimage/new.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("app-msg:list")
	public JSONObject uploadMediaImage(
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
	 * @param request HttpServletRequest
	 * @param response HttpServletResponse
	 * @param session HttpSession
	 * @return upload result
	 */
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
	 * 
	 * imageTexts String like '[{"title" : "title","author" : "author",
	 * "content" : "content", "contentSourceChecked" : true, "contentSourceUrl"
	 * : "http://www.xxxx.com", "materialCoverId" : 1, "showCover" : true,
	 * "summary" : "summary"}....]'
	 * 
	 * materialCoverId : 封面图片(图片素材ID)
	 * 
	 * required : title, content, materialCoverId
	 *
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "imagetext/new.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("app-msg:list")
	public JSONObject createImageText(
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
	 * 
	 * 更新图文
	 * 
	 * materialId : 图文ID
	 * 
	 * imageTexts String like '[{"title" : "title","author" : "author",
	 * "content" : "content", "contentSourceChecked" : true, "contentSourceUrl"
	 * : "http://www.xxxx.com", "materialCoverId" : 1, "showCover" : true,
	 * "summary" : "summary"}....]'
	 * 
	 * materialCoverId : 封面图片(图片素材ID)
	 * 
	 * required : title, content, materialCoverId
	 * 
	 * @param materialId
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "imagetext/{materialId}/update.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("app-msg:list")
	public JSONObject updateImageText(@PathVariable Integer materialId,
			@RequestBody(required = false) MaterialModel materialModel,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			materialService.updateImageText(getWechatId(session),
					getUser(session), materialId, materialModel);
			if (materialModel.getPush() != null && materialModel.getPush()) {
				materialService.pushMaterialImageTextToWx(getWechatId(session),
						getUser(session), materialId);
			}
			return representation(Message.MATERIAL_IMAGE_TEXT_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	/**
	 * 
	 * 删除图文
	 * 
	 * @param materialId
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "imagetext/{materialId}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	@RequiresPermissions("app-msg:list")
	public JSONObject deleteImageText(@PathVariable Integer materialId,
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
	 * 
	 * 删除图文详情
	 *
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "imagetextdetail/{materialImageTextDetailId}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	@Deprecated
	@RequiresPermissions("app-msg:list")
	public JSONObject deleteImageTextDetail(
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
	 * 
	 * 查看图文详情
	 * 
	 * id : 图文素材ID
	 * 
	 * @param id
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@RequestMapping(value = "imagetextdetail/{id}/get.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("app-msg:list")
	public JSONObject getImageText(@PathVariable Integer id,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		MaterialDto materialDto = materialService.getImageText(
				getWechatId(session), id);
		return representation(Message.MATERIAL_IMAGE_TEXT_GET_SUCCESS,
				materialDto);
	}

	/**
	 * 
	 * 图文素材详情列表
	 * 
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
	@RequestMapping(value = "imagetextdetail/list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("app-msg:list")
	public JSONObject listImageTextDetail(
			@RequestParam(required = false) Integer materialId,
			@RequestParam(required = false) String query,
			@RequestParam(required = false) String sortName,
			@RequestParam(required = false) String sortDir,
			@RequestParam(required = false, defaultValue = "1") Integer pageNum,
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
	 * 
	 * 更新图片名称
	 * 
	 */
	@RequestMapping(value = "image/{materialId}/rename.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("app-msg:list")
	public JSONObject updateImageName(@PathVariable Integer materialId,
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
	 * 
	 * 删除图片
	 * 
	 */
	@RequestMapping(value = "image/{materialId}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	@RequiresPermissions("app-msg:list")
	public JSONObject deleteImage(@PathVariable Integer materialId,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			materialService.deleteImage(getWechatId(session), getUser(session),
					materialId);
			return representation(Message.MATERIAL_IMAGE_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
}
