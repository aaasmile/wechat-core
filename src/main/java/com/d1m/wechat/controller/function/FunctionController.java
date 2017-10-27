package com.d1m.wechat.controller.function;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.controller.wechat.WechatController;
import com.d1m.wechat.dto.FunctionDto;
import com.d1m.wechat.pamametermodel.FunctionModel;
import com.d1m.wechat.service.FunctionService;
import com.d1m.wechat.util.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;


@Controller
@RequestMapping("/function")
@Api(value="功能API", tags="功能接口")
public class FunctionController extends BaseController{
	
	private Logger log = LoggerFactory.getLogger(WechatController.class);
	
	@Autowired
	private FunctionService functionService;
	
	@ApiOperation(value="获取功能列表", tags="功能接口")
	@ApiResponse(code=200, message="1-功能列表成功")
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject list(
			@ApiParam(name="FunctionModel",required=false)
				@RequestBody(required = false) FunctionModel functionModel,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (functionModel == null) {
				functionModel = new FunctionModel();
			}
			List<FunctionDto> functionDtos = functionService.search(functionModel);
			return representation(Message.FUNCTION_LIST_SUCCESS, functionDtos);

		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	

}
