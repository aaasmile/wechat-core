package com.d1m.wechat.controller.role;

import java.util.List;

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
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.RoleDto;
import com.d1m.wechat.pamametermodel.RoleModel;
import com.d1m.wechat.service.RoleService;
import com.d1m.wechat.util.Message;

@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RoleService roleService;
	
	@RequestMapping(value = "user/list.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject listByUser(HttpServletResponse response,
			HttpSession session) {
		try {
			
			RoleDto roleDto = roleService.listByUser(getUser(session));
			return representation(Message.USER_FUNCTION_LIST_SUCCESS, roleDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@RequestMapping(value = "search.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:role-list")
	public JSONObject search(
			@RequestBody(required = false) RoleModel roleModel,
			HttpServletResponse response, HttpSession session) {
		try {
			if (roleModel == null) {
				roleModel = new RoleModel();
			}
			List<RoleDto> roleDtos = roleService.search(roleModel, getCompanyId(session));
			JSONObject json  = representation(Message.ROLE_SEARCH_SUCCESS, roleDtos);
			json.put("isSystemRole", getIsSystemRole(session));
			return json;
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@RequestMapping(value = "insert.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:role-list")
	public JSONObject create(
			@RequestBody(required = false) RoleModel roleModel,
			HttpSession session) {
		try {
			int resultCode = roleService.insert(getUser(session), roleModel);
			return representation(Message.ROLE_INSERT_SUCCESS, resultCode);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}

	}
	
	@RequestMapping(value = "{id}/get.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:role-list")
	public JSONObject get(@PathVariable Integer id){
		try {
			RoleDto roleDto = roleService.getById(id, null);
			return representation(Message.ROLE_GET_SUCCESS, roleDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:role-list")
	public JSONObject update(@PathVariable Integer id,
			@RequestBody(required = false) RoleModel roleModel,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response){
		try {
			int resultCode = roleService.update(id, roleModel, getUser(session));
			return representation(Message.ROLE_UPDATE_SUCCESS, resultCode);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
		
	}
	
	@RequestMapping(value = "{id}/delete.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:role-list")
	public JSONObject delete(@PathVariable Integer id){
		try {
			int resultCode = roleService.deleteById(id);
			if (resultCode == -1){
				return representation(Message.ROLE_USED_NOT_DELETE, resultCode);
			}else{
				return representation(Message.ROLE_DELETE_SUCCESS, resultCode);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
