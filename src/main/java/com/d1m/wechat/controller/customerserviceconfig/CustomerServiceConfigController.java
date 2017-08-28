package com.d1m.wechat.controller.customerserviceconfig;

import java.util.List;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.CustomerServiceConfigDto;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.CustomerServiceConfigModel;
import com.d1m.wechat.service.CustomerServiceConfigService;
import com.d1m.wechat.service.UserWechatService;
import com.d1m.wechat.util.Message;

@Slf4j
@Controller
@RequestMapping("/customer-service-config")
public class CustomerServiceConfigController extends BaseController {

	@Resource
	private CustomerServiceConfigService customerServiceConfigService;

	@Resource
	private UserWechatService userWechatService;

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

	@RequestMapping(value = "updateGroup", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:customer-service-config-update")
	public JSONObject updateGroup(
			@RequestBody(required = false) List<CustomerServiceConfigModel> list) {
		try {
			customerServiceConfigService.updateGroup(getWechatId(), list);
			return representation(Message.CUSTOMER_SERVICE_CONFIG_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "update", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:customer-service-config-update")
	public JSONObject update(
			@RequestBody(required = false) CustomerServiceConfigModel model) {
		try {
			customerServiceConfigService.update(getWechatId(), model);
			return representation(Message.CUSTOMER_SERVICE_CONFIG_UPDATE_SUCCESS, model.getId());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{id}/delete", method = RequestMethod.DELETE)
	@ResponseBody
	@RequiresPermissions("system-setting:customer-service-config-delete")
	public JSONObject delete(@PathVariable Integer id) {
		try {
			customerServiceConfigService.delete(getWechatId(), id);
			return representation(Message.CUSTOMER_SERVICE_CONFIG_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "add", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:customer-service-config-delete")
	public JSONObject add(@RequestBody(required = false) CustomerServiceConfigModel model) {
		try {
			int addedId = customerServiceConfigService.add(model, getWechatId());
			return representation(Message.CUSTOMER_SERVICE_CONFIG_ADD_SUCCESS, addedId);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

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