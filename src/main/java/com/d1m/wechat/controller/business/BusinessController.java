package com.d1m.wechat.controller.business;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

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
import com.d1m.wechat.dto.BusinessDto;
import com.d1m.wechat.model.Business;
import com.d1m.wechat.pamametermodel.BusinessModel;
import com.d1m.wechat.service.BusinessService;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.Message;

@Controller
@RequestMapping("/business")
@Api(value="门店API", tags="门店接口")
public class BusinessController extends BaseController {

	private Logger log = LoggerFactory.getLogger(BusinessController.class);

	@Autowired
	private BusinessService businessService;
	
	@ApiOperation(value="上传图片", tags="门店接口")
	@ApiResponse(code=200, message="1-上传文件成功")
	@RequestMapping(value = "photo/upload.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("outlets:list")
	public JSONObject uploadImage(
			@ApiParam("上传的图片")
			@RequestParam(required = false) MultipartFile file,
			HttpServletResponse response, HttpSession session) {
		try {
			Upload upload = UploadController.upload(getWechatId(), file, Constants.IMAGE,
					Constants.BUSINESS);
			JSONObject json = new JSONObject();
			json.put("url", upload.getAccessPath());
			json.put("absoluteUrl", upload.getAbsolutePath());
			return representation(Message.FILE_UPLOAD_SUCCESS, json);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="创建新的门店", tags="门店接口")
	@ApiResponse(code=200, message="1-创建门店成功")
	@RequestMapping(value = "create.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("outlets:list")
	public JSONObject create(
			@ApiParam("BusinessModel")
			@RequestBody(required = false) BusinessModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (model == null) {
				model = new BusinessModel();
			}
			
			Business business = businessService.create(getWechatId(session), getUser(session),
					model);
			if (model.getPush() != null && model.getPush()){
				businessService.pushBusinessToWx(getWechatId(session), business, model);
			}
			return representation(Message.BUSINESS_CREATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="获取门店列表", tags="门店接口")
	@ApiResponse(code=200, message="1-获取门店列表成功")
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("outlets:list")
	public JSONObject list(
			@ApiParam("BusinessModel")
			@RequestBody(required = false) BusinessModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (model == null) {
				model = new BusinessModel();
			}
			Page<BusinessDto> page = businessService.search(
					getWechatId(session), model, true);
			return representation(Message.BUSINESS_LIST_SUCCESS,
					page.getResult(), model.getPageNum(), model.getPageSize(),
					page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="获取门店详情", tags="门店接口")
	@ApiResponse(code=200, message="1-获取门店详情成功")
	@RequestMapping(value = "detail.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("outlets:list")
	public JSONObject detail(
			@ApiParam("门店ID")
			@RequestParam(required = true) Integer id,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			BusinessDto businessDto = businessService.get(getWechatId(session),
					id);
			return representation(Message.BUSINESS_GET_SUCCESS, businessDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value="删除门店", tags="门店接口")
	@ApiResponse(code=200, message="1-删除门店成功")
	@RequestMapping(value = "delete.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("outlets:list")
	public JSONObject delete(
			@ApiParam("BusinessModel")
			@RequestBody(required = false) BusinessModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			businessService.delete(getWechatId(session), model);
			return representation(Message.BUSINESS_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value="更新门店", tags="门店接口")
	@ApiResponse(code=200, message="1-更新门店成功")
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("outlets:list")
	public JSONObject update(
			@ApiParam("BusinessModel")
			@RequestBody(required = false) BusinessModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			businessService.update(getWechatId(session), model);
			return representation(Message.BUSINESS_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="获取门店百度经纬度信息", tags="门店接口")
	@ApiResponse(code=200, message="1-门店获取百度经纬度成功")
	@RequestMapping(value = "init-business.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject initBusinessLatAndLng(
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			businessService.initBusinessLatAndLng(getWechatId(session),
					getUser(session));
			return representation(Message.MAP_BUSINESS_LAT_LNG_GET_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
