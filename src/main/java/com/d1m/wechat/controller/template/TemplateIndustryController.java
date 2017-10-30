package com.d1m.wechat.controller.template;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.service.TemplateIndustryService;
import com.d1m.wechat.util.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

@Api(value="模板行业API", tags="模板行业接口")
@Controller
@RequestMapping("/template-industry")
public class TemplateIndustryController extends BaseController {

	@Autowired
	private TemplateIndustryService templateIndustryService;
	
	@ApiOperation(value="模板行业获取", tags="模板行业接口")
	@ApiResponse(code=200, message="1-模板行业获取成功")
	@RequestMapping(value = "get", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject get(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		try {
			return representation(Message.TEMPLATE_INDUSTRY_GET_SUCCESS,
					templateIndustryService.get(getWechatId()));
		} catch (Exception e) {
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="模板行业同步微信", tags="模板行业接口")
	@ApiResponse(code=200, message="1-模板行业同步微信成功")
	@RequestMapping(value = "pull-wx", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject pullWxTemplateIndustry(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		try {
			templateIndustryService
					.pullWxTemplateIndustry(getWechatId(session));
			return representation(Message.TEMPLATE_INDUSTRY_PULL_WX_SUCCESS);
		} catch (Exception e) {
			return wrapException(e);
		}
	}

}
