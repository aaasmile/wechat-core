package com.d1m.wechat.controller.template;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.pamametermodel.TemplateEncryptModel;
import com.d1m.wechat.pamametermodel.TemplateModel;
import com.d1m.wechat.service.TemplateService;
import com.d1m.wechat.util.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Api(value="模板API", tags="模板接口")
@Controller
@RequestMapping("/template")
public class TemplateController extends BaseController {

	@Autowired
	private TemplateService templateService;
	
	@ApiOperation(value="获取模板参数", tags="模板接口")
	@ApiResponse(code=200, message="获取模板参数")
	@RequestMapping(value="get", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject get(
			@ApiParam(name="TemplateEncryptModel", required=true)
				@RequestBody(required = true) TemplateEncryptModel templateEncryptModel){
		try {
			return templateService.getParams(templateEncryptModel);
		} catch (Exception e) {
			return wrapException(e);
		}

	}
	
	@ApiOperation(value="发送给微信", tags="模板接口")
	@ApiResponse(code=200, message="发送给微信")
	@RequestMapping(value="send", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject send(
			@ApiParam(name="TemplateEncryptModel", required=true)
				@RequestBody(required = true) TemplateEncryptModel templateEncryptModel){
		try {
			return templateService.sendToWX(templateEncryptModel);
		} catch (Exception e) {
			return wrapException(e);
		}

	}
	
	@ApiOperation(value="微信模板列表", tags="模板接口")
	@ApiResponse(code=200, message="1-微信模板列表成功")
	@RequestMapping(value = "list", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject get() {
		try {
			return representation(Message.TEMPLATE_LIST_SUCCESS,
					templateService.list(getWechatId()));
		} catch (Exception e) {
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="微信模板删除", tags="模板接口")
	@ApiResponse(code=200, message="1-微信模板删除成功")
	@RequestMapping(value = "delete", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject delete(
			@ApiParam(name="TemplateModel", required=true)
				@RequestBody(required = true) TemplateModel templateModel) {
		try {
			templateService.delete(getWechatId(), templateModel.getId());
			return representation(Message.TEMPLATE_DELETE_SUCCESS);
		} catch (Exception e) {
			return wrapException(e);
		}
	}

	@ApiOperation(value="同步微信模板", tags="模板接口")
	@ApiResponse(code=200, message="1-同步微信模板成功")
	@RequestMapping(value = "pull-wx", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject pullWxTemplate() {
		try {
			templateService.pullWxTemplate(getWechatId());
			return representation(Message.TEMPLATE_PULL_WX_SUCCESS);
		} catch (Exception e) {
			return wrapException(e);
		}
	}

}
