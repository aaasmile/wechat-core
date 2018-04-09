package com.d1m.wechat.controller.customerserviceconfig;

import java.util.List;

import javax.annotation.Resource;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.CustomerServiceConfigDto;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.CustomerServiceConfigModel;
import com.d1m.wechat.service.CustomerServiceConfigService;
import com.d1m.wechat.service.UserWechatService;
import com.d1m.wechat.util.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping("/customer-service-config")
@Api(value="CS设置API", tags="CS设置接口")
public class CustomerServiceConfigController extends BaseController {
	
	private static final Logger log = LoggerFactory.getLogger(CustomerServiceConfigController.class);

	@Resource
	private CustomerServiceConfigService customerServiceConfigService;

	@Resource
	private UserWechatService userWechatService;
	
	@ApiOperation(value="获取客服配置列表", tags="CS设置接口")
	@ApiResponse(code=200, message="1-客服配置列表成功")
	@RequestMapping(value = "list", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:customer-service-config-list")
	public JSONObject get() {
		try {
			List<CustomerServiceConfigDto> list = customerServiceConfigService
					.search(getWechatId(), null);
			return representation(Message.CUSTOMER_SERVICE_CONFIG_LIST_SUCCESS,
					list);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="客服配置批量更新", tags="CS设置接口")
	@ApiResponse(code=200, message="1-客服配置更新成功")
	@RequestMapping(value = "updateGroup", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:customer-service-config-update")
	public JSONObject updateGroup(
			@ApiParam(name="CustomerServiceConfigModel列表", required=false)
			@RequestBody(required = false) List<CustomerServiceConfigModel> list) {
		try {
			customerServiceConfigService.updateGroup(getWechatId(), list);
			return representation(Message.CUSTOMER_SERVICE_CONFIG_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="客服配置单个更新", tags="CS设置接口")
	@ApiResponse(code=200, message="1-客服配置更新成功")
	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:customer-service-config-update")
	public JSONObject update(
			@ApiParam("CustomerServiceConfigModel")
				@RequestBody(required = false) CustomerServiceConfigModel model) {
		try {
			customerServiceConfigService.update(getWechatId(), model);
			return representation(Message.CUSTOMER_SERVICE_CONFIG_UPDATE_SUCCESS, model.getId());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="根据ID删除客服配置", tags="CS设置接口")
	@ApiResponse(code=200, message="1-客服配置删除成功")
	@RequestMapping(value = "{id}/delete", method = RequestMethod.DELETE)
	@ResponseBody
	@RequiresPermissions("system-setting:customer-service-config-delete")
	public JSONObject delete(
			@ApiParam("客服配置ID")
				@PathVariable Integer id) {
		try {
			customerServiceConfigService.delete(getWechatId(), id);
			return representation(Message.CUSTOMER_SERVICE_CONFIG_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="客服配置添加", tags="CS设置接口")
	@ApiResponse(code=200, message="1-客服配置添加成功")
	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:customer-service-config-delete")
	public JSONObject add(
			@ApiParam(name="CustomerServiceConfigModel", required=false)
				@RequestBody(required = false) CustomerServiceConfigModel model) {
		try {
			int addedId = customerServiceConfigService.add(model, getWechatId());
			return representation(Message.CUSTOMER_SERVICE_CONFIG_ADD_SUCCESS, addedId);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="获取微信用户列表", tags="CS设置接口")
	@ApiResponse(code=200, message="1-获取微信用户列表成功", response=List.class)
	@RequestMapping(value = "getCustomerServiceUsers", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:customer-service-config-update")
	public JSONObject getCustomerServiceUsers() {
		try {
			final List<User> users = userWechatService.listWechatUsers(getWechatId());
			return representation(Message.CUSTOMER_SERVICE_CONFIG_LIST_USERS_SUCCESS, users);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
}