package com.d1m.wechat.controller.wechat;

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
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.pamametermodel.WechatModel;
import com.d1m.wechat.pamametermodel.WechatTagModel;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.Message;


@Controller
@RequestMapping("/wechat")
public class WechatController extends BaseController{
	
	private Logger log = LoggerFactory.getLogger(WechatController.class);

	@Autowired
	private WechatService wechatService;
	

	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:official-account-list")
	public JSONObject list(
			@RequestBody(required = false) WechatModel wechatModel,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (wechatModel == null) {
				wechatModel = new WechatModel();
			}
			wechatModel.setCompanyId(getCompanyId(session));
			Page<Wechat> page = wechatService.search(wechatModel, true);
			JSONObject json =  representation(Message.WECHAT_LIST_SUCCESS, page.getResult(),
					wechatModel.getPageNum(), wechatModel.getPageSize(), 
					page.getTotal());
			json.put("isSystemRole", getIsSystemRole(session));
			return json;
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
		
	
	@RequestMapping(value = "insert.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:official-account-list")
	public JSONObject insertWechat(
			@RequestBody(required = false) WechatTagModel wechatTagModel,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			int resultCode = wechatService.insert(wechatTagModel, getUser(session));
			return representation(Message.WECHAT_INSERT_SUCCESS, resultCode);

		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
		
	}
	
	@RequestMapping(value = "{id}/get.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:official-account-list")
	public JSONObject getWechatById(@PathVariable Integer id){
		try {
			Wechat wechat = wechatService.getById(id);
			return representation(Message.WECHAT_GET_SUCCESS, wechat);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
		
	}
	
	@RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:official-account-list")
	public JSONObject updateWechat(@PathVariable Integer id,
			@RequestBody(required = false) WechatTagModel wechatTagModel,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response){
		try {
			int resultCode = wechatService.update(id, wechatTagModel, getUser(session));
			return representation(Message.WECHAT_UPDATE_SUCCESS, resultCode);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
		
	}
	
	@RequestMapping(value = "{id}/delete.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:official-account-list")
	public JSONObject delete(@PathVariable Integer id){
		try {
			int resultCode = wechatService.delete(id);
			return representation(Message.WECHAT_DELETE_SUCCESS, resultCode);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@RequestMapping(value = "{id}/update/{status}/status.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:official-account-list")
	public JSONObject updateStatus(@PathVariable Integer id, @PathVariable Byte status){
		try {
			int resultCode = wechatService.updateStatus(id, status);
			return representation(Message.WECHAT_UPDATE_STATUS_SUCCESS, resultCode);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@RequestMapping(value = "avatar/upload.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:official-account-list")
	public JSONObject uploadAvatar(
			@RequestParam(required = false) MultipartFile file,
			HttpServletResponse response, HttpSession session) {
		try {
			Upload upload = UploadController.upload(getWechatId(), file, Constants.IMAGE,
					Constants.WECHAT);
			JSONObject json = new JSONObject();
			json.put("headImgUrl", upload.getAccessPath());
			return representation(Message.FILE_UPLOAD_SUCCESS, json);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
