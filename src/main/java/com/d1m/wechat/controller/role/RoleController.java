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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

@Api(value="权限API", tags="权限接口")
@Controller
@RequestMapping("/role")
public class RoleController extends BaseController{
	
	private Logger log = LoggerFactory.getLogger(this.getClass());
	
	@Autowired
	private RoleService roleService;
	
	@ApiOperation(value="获取用户权限列表", tags="权限接口")
	@ApiResponse(code=200, message="1-用户权限列表成功")
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
	
	@ApiOperation(value="查询权限", tags="权限接口")
	@ApiResponse(code=200, message="1-角色列表成功")
	@RequestMapping(value = "search.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:role-list")
	public JSONObject search(
			@ApiParam("RoleModel")
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
	
	@ApiOperation(value="角色新增", tags="权限接口")
	@ApiResponse(code=200, message="1-角色新增成功")
	@RequestMapping(value = "insert.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:role-list")
	public JSONObject create(
			@ApiParam("RoleModel")
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
	
	@ApiOperation(value="角色获取", tags="权限接口")
	@ApiResponse(code=200, message="1-角色获取成功")
	@RequestMapping(value = "{id}/get.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:role-list")
	public JSONObject get(
			@ApiParam("权限ID")
				@PathVariable Integer id){
		try {
			RoleDto roleDto = roleService.getById(id, null);
			return representation(Message.ROLE_GET_SUCCESS, roleDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="角色更新", tags="权限接口")
	@ApiResponse(code=200, message="1-角色更新成功")
	@RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:role-list")
	public JSONObject update(
			@ApiParam("权限ID")
				@PathVariable Integer id,
			@ApiParam("RoleModel")
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
	
	@ApiOperation(value="角色删除", tags="权限接口")
	@ApiResponses({
		@ApiResponse(code=200, message="28003-使用过的角色不能删除"),
		@ApiResponse(code=200, message="1-角色删除成功")
	})
	@RequestMapping(value = "{id}/delete.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:role-list")
	public JSONObject delete(
			@ApiParam("权限ID")
				@PathVariable Integer id){
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
