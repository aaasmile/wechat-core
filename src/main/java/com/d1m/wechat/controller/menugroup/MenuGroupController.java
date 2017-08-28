package com.d1m.wechat.controller.menugroup;

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

import cn.d1m.wechat.client.model.WxTag;
import cn.d1m.wechat.client.model.common.WxList;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.MenuGroupDto;
import com.d1m.wechat.model.MenuGroup;
import com.d1m.wechat.pamametermodel.MenuGroupModel;
import com.d1m.wechat.service.MenuGroupService;
import com.d1m.wechat.util.Message;

@Controller
@RequestMapping("/menugroup")
public class MenuGroupController extends BaseController {

	private Logger log = LoggerFactory.getLogger(MenuGroupController.class);

	@Autowired
	private MenuGroupService menuGroupService;

	@RequestMapping(value = "pushwx.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject pushMenuGroupToWx(HttpSession session,
			@RequestBody(required = false) MenuGroupModel menuGroupModel,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			menuGroupService.pushMenuGroupToWx(getWechatId(session),
					menuGroupModel.getId());
			return representation(Message.MENU_GROUP_PUSH_WX_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "getdefault.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject getdefault(HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			MenuGroup defaultMenuGroup = menuGroupService.getDefaultMenuGroup(
					getUser(session), getWechatId(session));
			return representation(Message.MENU_GROUP_CREATE_SUCCESS,
					defaultMenuGroup);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "new.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject create(
			@RequestBody(required = false) MenuGroupModel menuGroupModel,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Integer wechatId = getWechatId(session);
			MenuGroup menuGroup = menuGroupService.create(getUser(session),
					wechatId, menuGroupModel);
			if (menuGroupModel.getPush() != null && menuGroupModel.getPush()) {
				menuGroupService.pushMenuGroupToWx(wechatId, menuGroup.getId());
			}
			return representation(Message.MENU_GROUP_CREATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject update(@PathVariable Integer id,
			@RequestBody(required = false) MenuGroupModel menuGroupModel,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Integer wechatId = getWechatId(session);
			menuGroupService.update(getUser(session), wechatId, id,
					menuGroupModel);
			if (menuGroupModel.getPush() != null && menuGroupModel.getPush()) {
				menuGroupService.pushMenuGroupToWx(wechatId, id);
			}
			return representation(Message.MENU_GROUP_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{id}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject delete(@PathVariable Integer id, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			menuGroupService.delete(getUser(session), getWechatId(session), id);
			return representation(Message.MENU_GROUP_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject list(
			@RequestBody(required = false) MenuGroupModel menuGroupModel,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		if (menuGroupModel == null) {
			menuGroupModel = new MenuGroupModel();
		}
		Page<MenuGroupDto> page = menuGroupService.find(getWechatId(session),
				menuGroupModel, true);
		return representation(Message.MENU_GROUP_LIST_SUCCESS,
				page.getResult(), menuGroupModel.getPageNum(),
				menuGroupModel.getPageSize(), page.getTotal());
	}

	@RequestMapping(value = "{id}/get.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject get(
			@PathVariable Integer id,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		MenuGroupDto menuGroupDto = menuGroupService.get(getWechatId(session),
				id);
		return representation(Message.MENU_GROUP_GET_SUCCESS, menuGroupDto);
	}

	@RequestMapping(value = "wxTagList.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject wxTagList() {
		Integer wechatId = getWechatId();
        WxList<WxTag> wxTagList = WechatClientDelegate.getTags(wechatId);
		return representation(Message.SUCCESS, wxTagList.get());
	}

}
