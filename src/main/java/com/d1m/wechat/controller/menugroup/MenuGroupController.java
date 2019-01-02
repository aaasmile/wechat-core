package com.d1m.wechat.controller.menugroup;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.MenuGroupDto;
import com.d1m.wechat.model.MenuGroup;
import com.d1m.wechat.pamametermodel.MaterialModel;
import com.d1m.wechat.pamametermodel.MenuGroupModel;
import com.d1m.wechat.service.MenuGroupService;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.github.pagehelper.Page;

import cn.d1m.wechat.client.model.WxTag;
import cn.d1m.wechat.client.model.common.WxList;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping("/menugroup")
@Api(value = "菜单组别API", tags = "菜单组别接口")
public class MenuGroupController extends BaseController {

	private Logger log = LoggerFactory.getLogger(MenuGroupController.class);

	@Autowired
	private MenuGroupService menuGroupService;

	@ApiOperation(value = "菜单组推送微信", tags = "菜单组别接口")
	@ApiResponse(code = 200, message = "1-菜单组推送微信成功")
	@RequestMapping(value = "pushwx.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject pushMenuGroupToWx(HttpSession session, @ApiParam(name = "MenuGroupModel", required = false) @RequestBody(required = false) MenuGroupModel menuGroupModel, HttpServletRequest request, HttpServletResponse response) {
		try {
			menuGroupService.pushMenuGroupToWx(getWechatId(session), menuGroupModel.getId());
			return representation(Message.MENU_GROUP_PUSH_WX_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "获取默认菜单组", tags = "菜单组别接口")
	@ApiResponse(code = 200, message = "获取默认菜单组")
	@RequestMapping(value = "getdefault.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject getdefault(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {
			MenuGroup defaultMenuGroup = menuGroupService.getDefaultMenuGroup(getUser(session), getWechatId(session));
			return representation(Message.MENU_GROUP_CREATE_SUCCESS, defaultMenuGroup);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "创建菜单组", tags = "菜单组别接口")
	@ApiResponse(code = 200, message = "1-创建菜单组成功")
	@RequestMapping(value = "new.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject create(@ApiParam(name = "MenuGroupModel", required = false) @RequestBody(required = false) MenuGroupModel menuGroupModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer wechatId = getWechatId();
			MenuGroup menuGroup = menuGroupService.create(getUser(), wechatId, menuGroupModel);
			if (menuGroupModel.getPush() != null && menuGroupModel.getPush()) {
				menuGroupService.pushMenuGroupToWx(wechatId, menuGroup.getId());
			}
			return representation(Message.MENU_GROUP_CREATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "更新菜单组", tags = "菜单组别接口")
	@ApiResponse(code = 200, message = "1-更新菜单组成功")
	@RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject update(@ApiParam("菜单组ID") @PathVariable Integer id, @ApiParam(name = "MenuGroupModel", required = false) @RequestBody(required = false) MenuGroupModel menuGroupModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {
			Integer wechatId = getWechatId();
			menuGroupService.update(getUser(), wechatId, id, menuGroupModel);
			if (menuGroupModel.getPush() != null && menuGroupModel.getPush()) {
				menuGroupService.pushMenuGroupToWx(wechatId, id);
			}
			return representation(Message.MENU_GROUP_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "删除菜单组", tags = "菜单组别接口")
	@ApiResponse(code = 200, message = "1-删除菜单组成功")
	@RequestMapping(value = "{id}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject delete(@ApiParam("菜单组ID") @PathVariable Integer id, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {
			menuGroupService.delete(getUser(session), getWechatId(session), id);
			return representation(Message.MENU_GROUP_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "获取菜单组列表", tags = "菜单组别接口")
	@ApiResponse(code = 200, message = "1-获取菜单组列表成功")
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject list(@ApiParam(name = "MenuGroupModel", required = false) @RequestBody(required = false) MenuGroupModel menuGroupModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		if (menuGroupModel == null) {
			menuGroupModel = new MenuGroupModel();
		}
		Page<MenuGroupDto> page = menuGroupService.find(getWechatId(), menuGroupModel, true);
		return representation(Message.MENU_GROUP_LIST_SUCCESS, page.getResult(), menuGroupModel.getPageNum(), menuGroupModel.getPageSize(), page.getTotal());
	}

	@ApiOperation(value = "获取菜单组详情", tags = "菜单组别接口")
	@ApiResponse(code = 200, message = "1-获取菜单组详情成功")
	@RequestMapping(value = "{id}/get.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject get(@ApiParam("菜单组ID") @PathVariable Integer id, @ApiParam(name = "MenuGroupModel", required = false) @RequestParam(required = false, defaultValue = "10") Integer pageSize, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		MenuGroupDto menuGroupDto = menuGroupService.get(getWechatId(), id);
		return representation(Message.MENU_GROUP_GET_SUCCESS, menuGroupDto);
	}

	@ApiOperation(value = "获取微信标签列表", tags = "菜单组别接口")
	@ApiResponse(code = 200, message = "1-操作成功")
	@RequestMapping(value = "wxTagList.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("menu:list")
	public JSONObject wxTagList() {
		Integer wechatId = getWechatId();
		WxList<WxTag> wxTagList = WechatClientDelegate.getTags(wechatId);
		return representation(Message.SUCCESS, wxTagList.get());
	}
}
