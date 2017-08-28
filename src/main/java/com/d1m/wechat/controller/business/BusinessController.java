package com.d1m.wechat.controller.business;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
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
public class BusinessController extends BaseController {

	private Logger log = LoggerFactory.getLogger(BusinessController.class);

	@Autowired
	private BusinessService businessService;

	@RequestMapping(value = "photo/upload.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("outlets:list")
	public JSONObject uploadImage(
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

	@RequestMapping(value = "create.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("outlets:list")
	public JSONObject create(
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

	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("outlets:list")
	public JSONObject list(@RequestBody(required = false) BusinessModel model,
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

	@RequestMapping(value = "detail.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("outlets:list")
	public JSONObject detail(@RequestParam(required = true) Integer id,
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

	@RequestMapping(value = "delete.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("outlets:list")
	public JSONObject delete(
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

	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("outlets:list")
	public JSONObject update(
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
