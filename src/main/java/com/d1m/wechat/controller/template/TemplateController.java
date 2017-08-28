package com.d1m.wechat.controller.template;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.pamametermodel.TemplateEncryptModel;
import com.d1m.wechat.pamametermodel.TemplateModel;
import com.d1m.wechat.service.TemplateService;
import com.d1m.wechat.util.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
@RequestMapping("/template")
public class TemplateController extends BaseController {

	@Autowired
	private TemplateService templateService;

	@RequestMapping(value="get", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject get(
			@RequestBody(required = true) TemplateEncryptModel templateEncryptModel){
		try {
			return templateService.getParams(templateEncryptModel);
		} catch (Exception e) {
			return wrapException(e);
		}

	}

	@RequestMapping(value="send", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject send(
			@RequestBody(required = true) TemplateEncryptModel templateEncryptModel){
		try {
			return templateService.sendToWX(templateEncryptModel);
		} catch (Exception e) {
			return wrapException(e);
		}

	}

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

	@RequestMapping(value = "delete", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject delete(
			@RequestBody(required = true) TemplateModel templateModel) {
		try {
			templateService.delete(getWechatId(), templateModel.getId());
			return representation(Message.TEMPLATE_DELETE_SUCCESS);
		} catch (Exception e) {
			return wrapException(e);
		}
	}

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
