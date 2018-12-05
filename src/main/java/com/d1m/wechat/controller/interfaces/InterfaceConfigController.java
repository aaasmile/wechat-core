package com.d1m.wechat.controller.interfaces;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.service.InterfaceConfigService;
import com.d1m.wechat.util.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@Api(value = "第三方接口API", tags = "第三方接口API")
@RestController
@RequestMapping("interfaceConfig")
public class InterfaceConfigController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(InterfaceConfigController.class);
	@Autowired
	private InterfaceConfigService interfaceConfigService;

	@ApiOperation(value = "第三方接口列表", tags = "第三方接口列表")
	@ApiResponse(code = 200, message = "获取第三方接口信息成功")
	@RequestMapping(name = "selectItems.json", method = RequestMethod.POST)
	public JSONObject selectItems() {
		try {
			List<Map<String, String>> list = interfaceConfigService.selectItems();
			return representation(Message.INTERFACECONFIG_SELECT_SUCCESS, list);
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return representation(Message.INTERFACECONFIG_SELECT_FAIL);
		}
	}
}
