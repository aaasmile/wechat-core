package com.d1m.wechat.controller.user;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import io.swagger.annotations.ApiResponses;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.RandomStringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.SimplePrincipalCollection;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.controller.file.Upload;
import com.d1m.wechat.controller.file.UploadController;
import com.d1m.wechat.dto.FunctionDto;
import com.d1m.wechat.dto.RoleDto;
import com.d1m.wechat.dto.UserDto;
import com.d1m.wechat.dto.WechatDto;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.pamametermodel.FunctionModel;
import com.d1m.wechat.pamametermodel.UserModel;
import com.d1m.wechat.security.AuthcAuthenticationFilter;
import com.d1m.wechat.service.*;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.CookieUtils;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.wechatclient.WechatClientDelegate;

@Api(value="用户API", tags="用户接口")
@Controller
@RequestMapping("/user")
public class UserController extends BaseController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private UserService userService;
	
	@Autowired
	private RoleService roleService;

	@Autowired
	private FunctionService functionService;
	
	@Autowired
	private LoginTokenService loginTokenService;
	
	@Autowired
	private WechatService wechatService;
	
	@ApiOperation(value="登录", tags="用户接口")
	@ApiResponses({
		@ApiResponse(code=200, message="1-用户登录成功"),
		@ApiResponse(code=200, message="401-未登录"),
		@ApiResponse(code=200, message="23003-用户名或者密码错误")
	})
	@RequestMapping(value = "login", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject login(
			@ApiParam("UserModel")
				@RequestBody(required = false) UserModel userModel,
		HttpServletRequest request,HttpServletResponse response, HttpSession session) {
		UsernamePasswordToken token = new UsernamePasswordToken(userModel.getUsername(), DigestUtils.sha256Hex(userModel.getPassword()));  
	    //token.setRememberMe(true);  
	    Subject subject = SecurityUtils.getSubject();  
	    User user = userService.getByUsername(userModel.getUsername());
	    try {  
	        subject.login(token);  
	        if (subject.isAuthenticated()) {
	        	UserDto profile = userService.getProfile(getUser(session));
	    		RoleDto roleDto = roleService.getById(profile.getRoleId(), profile.getCompanyId());
	    		profile.setFunctionIds(roleDto.getFunctionIds());
	    		profile.setFunctionCodes(roleDto.getFunctionCodes());
	    		FunctionModel functionModel = new FunctionModel();
	    		List<FunctionDto> functionDtos = functionService.search(functionModel);
	    		profile.setFunctionDtos(functionDtos);
	    		user.setCounter(0);
	    		userService.updateNotNull(user);
				CookieUtils.setCookie(request, response, AuthcAuthenticationFilter.DEFAULT_PARAM_TOKEN, RandomStringUtils.randomAlphanumeric(16));
	        	return representation(Message.USER_LOGIN_SUCCESS,profile);
	        } else {
	        	return representation(Message.NOT_LOGGED_IN);
	        }  
	    } catch (Exception e) { 
	    	if(Message.getByName(e.getMessage())!=null){
	    		return representation(Message.getByName(e.getMessage()));
	    	}
	    	if(user!=null){
        		user.setCounter(user.getCounter() + 1);
        		userService.updateNotNull(user);
        	}
	    	return representation(Message.USER_NAME_OR_PASSWORD_ERROR);
	    }
//		try {
//			User user = userService.login(userModel.getUsername(),
//					userModel.getPassword());
//			LoginToken loginToken = new LoginToken();
//			List<WechatDto> wechats = userService.listVisibleWechat(user);
//			if (!wechats.isEmpty()) {
//				loginToken.setWechatId(wechats.get(0).getId());
//			}
//
//			String supportMultiplePeopleLogin = FileUploadConfigUtil
//					.getInstance().getValue("support_multiple_people_login");
//			supportMultiplePeopleLogin = StringUtils
//					.trim(supportMultiplePeopleLogin);
//			if (!StringUtils.equals(supportMultiplePeopleLogin,
//					Constants.SUPPORT_MULTIPLE_PEOPLE_LOGIN)) {
//				List<LoginToken> existLoginTokens = loginTokenService
//						.getActiveTokens(user);
//				for (LoginToken lt : existLoginTokens) {
//					lt.setExpiredAt(new Date());
//					loginTokenService.updateAll(lt);
//				}
//			}
//
//			Date current = new Date();
//			loginToken.setCreatedAt(current);
//			loginToken.setExpiredAt(DateUtil.changeDate(current,
//					Calendar.HOUR_OF_DAY, 8));
//			String token = DigestUtils.sha256Hex(user.getUsername() + "@"
//					+ System.currentTimeMillis());
//			loginToken.setToken(token);
//			loginToken.setUserId(user.getId());
//			loginTokenService.save(loginToken);
//
//			user.setLastLoginAt(current);
//			userService.updateAll(user);
//
//			response.addCookie(addCookie(Constants.TOKEN, token, 60 * 60 * 12));
//
//			return representation(Message.USER_LOGIN_SUCCESS);
//		} catch (Exception e) {
//			log.error(e.getMessage());
//			return wrapException(e);
//		}
	}
	
	@ApiOperation(value="注销", tags="用户接口")
	@ApiResponse(code=200, message="1-退出成功")
	@RequestMapping(value = "logout.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject logout(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		Subject subject = SecurityUtils.getSubject();
		User user = (User) subject.getPrincipal();
        WechatClientDelegate.destroy(user.getWechatId());
		subject.logout();
		CookieUtils.deleteCookie(request,response,AuthcAuthenticationFilter.DEFAULT_PARAM_TOKEN);
//		String token = getCookie(request, Constants.TOKEN);
//		log.info("token logout : {}", token);
//		if (token != null) {
//			LoginToken loginToken = loginTokenService.getByToken(token);
//			if (loginToken != null) {
//				loginToken.setExpiredAt(new Date());
//				loginTokenService.updateAll(loginToken);
//			}
//			response.addCookie(addCookie(Constants.TOKEN, token, 0));
//		}
		return representation(Message.LOGOUT_SUCCESS);
	}
	
	@ApiOperation(value="获取用户信息", tags="用户接口")
	@ApiResponse(code=200, message="1-获取成功")
	@RequestMapping(value = "profile.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getProfile(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		User user = (User) SecurityUtils.getSubject().getPrincipal();
		List<WechatDto> wechats = userService.listVisibleWechat(user);
		for(WechatDto wechatDto : wechats){
			if(wechatDto.getIsUse() == 1){
				user.setWechatId(wechatDto.getId());
				break;
			}
		}
		UserDto profile = userService.getProfile(getUser(session));
		RoleDto roleDto = roleService.getById(profile.getRoleId(), profile.getCompanyId());
		profile.setFunctionIds(roleDto.getFunctionIds());
		profile.setFunctionCodes(roleDto.getFunctionCodes());
		FunctionModel functionModel = new FunctionModel();
		List<FunctionDto> functionDtos = functionService.search(functionModel);
		profile.setFunctionDtos(functionDtos);
		return representation(Message.MEMBER_PROFILE_GET_SUCCESS, profile);
	}
	
	@ApiOperation(value="获取用户列表", tags="用户接口")
	@ApiResponse(code=200, message="1-获取用户列表成功")
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:user-list")
	public JSONObject listUser(
			@ApiParam("UserModel")
				@RequestBody(required = false) UserModel userModel,
			HttpServletResponse response, HttpSession session) {
		try {
			if (userModel == null) {
				userModel = new UserModel();
			}
			userModel.setCompanyId(getCompanyId(session));
			Page<UserDto> page = userService.search(userModel, true);
			JSONObject json = representation(Message.USER_LIST_SUCCESS, page.getResult(),
					userModel.getPageSize(), userModel.getPageNum(),
					page.getTotal());
			json.put("isSystemRole", getIsSystemRole(session));
			return json;
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="获取用户列表", tags="用户接口")
	@ApiResponse(code=200, message="1-获取用户列表成功")
	@RequestMapping(value = "avatar/upload.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:user-list")
	public JSONObject uploadAvatar(
			@ApiParam("上传文件")
				@RequestParam(required = false) MultipartFile file,
			HttpServletResponse response, HttpSession session) {
		try {
			Upload upload = UploadController.upload(getWechatId(), file, Constants.IMAGE,
					Constants.USER);
			JSONObject json = new JSONObject();
			json.put("url", upload.getAccessPath());
			return representation(Message.FILE_UPLOAD_SUCCESS, json);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="获取用户公众号列表", tags="用户接口")
	@ApiResponse(code=200, message="1-获取用户公众号列表成功")
	@RequestMapping(value = "wechat/list.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:user-list")
	public JSONObject listWechat(HttpServletResponse response,
			HttpSession session) {
		try {
			List<WechatDto> wechats = userService
					.listAvailableWechat(getUser(session));
			return representation(Message.USER_WECHAT_LIST_SUCCESS, wechats);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="获取角色列表", tags="用户接口")
	@ApiResponse(code=200, message="1-获取角色列表成功")
	@RequestMapping(value = "role/list.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:user-list")
	public JSONObject listRole(HttpServletResponse response,
			HttpSession session) {
		try {
			List<RoleDto> roleDtos = userService
					.listVisibleRole(getCompanyId(session));
			return representation(Message.USER_ROLE_LIST_SUCCESS, roleDtos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="创建用户", tags="用户接口")
	@ApiResponse(code=200, message="1-创建用户成功")
	@RequestMapping(value = "new.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:user-list")
	public JSONObject create(
			@ApiParam("UserModel")
				@RequestBody(required = false) UserModel userModel,
			HttpSession session) {
		try {
			userService.create(getUser(session), userModel);
			return representation(Message.USER_CREATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}

	}
	
	@ApiOperation(value="获取用户", tags="用户接口")
	@ApiResponse(code=200, message="1-获取用户成功")
	@RequestMapping(value = "{id}/get.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:user-list")
	public JSONObject get(
			@ApiParam("用户ID")
				@PathVariable Integer id){
		try {
			UserDto userDto = userService.getById(id);
			return representation(Message.USER_GET_SUCCESS, userDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="更新用户", tags="用户接口")
	@ApiResponse(code=200, message="1-更新用户成功")
	@RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:user-list")
	public JSONObject update(
			@ApiParam("用户ID")
				@PathVariable Integer id,
			@ApiParam("UserModel")
				@RequestBody(required = false) UserModel userModel,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response){
		try {
			int resultCode = userService.update(id, userModel, getUser(session));
			return representation(Message.USER_UPDATE_SUCCESS, resultCode);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
		
	}
	
	@ApiOperation(value="删除用户", tags="用户接口")
	@ApiResponse(code=200, message="1-删除用户成功")
	@RequestMapping(value = "{id}/delete.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:user-list")
	public JSONObject delete(
			@ApiParam("用户ID")
				@PathVariable Integer id, HttpSession session){
		try {
			int resultCode = userService.deleteById(id, getCompanyId(session));
			return representation(Message.USER_DELETE_SUCCESS, resultCode);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="用户修改密码", tags="用户接口")
	@ApiResponse(code=200, message="1-用户修改密码成功")
	@RequestMapping(value = "resetPassword.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:user-list")
	public JSONObject updatePwd(
			@ApiParam("UserModel")
				@RequestBody(required = false) UserModel userModel,
			HttpSession session, HttpServletRequest request){
		try {
			int resultCode = userService.updatePwd(userModel.getUserId(), userModel.getPassword());
			return representation(Message.USER_UPDATE_PASSWORD_SUCCESS, resultCode);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="用户切换公众号", tags="用户接口")
	@ApiResponse(code=200, message="1-用户切换公众号成功")
	@RequestMapping(value = "change-wechat.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject changeWechat(
			@ApiParam(name="公众号ID", required=true)
				@RequestParam(required = true) Integer wechatId, 
			HttpServletRequest request, HttpServletResponse response,
			HttpSession session) {
		try {
			
			wechatService.updateIsUse(getUser(session), wechatId);
			Subject currentUser = SecurityUtils.getSubject();
			User user = (User) currentUser.getPrincipal();
			user.setWechatId(wechatId);
			Session siss = currentUser.getSession();
			siss.setAttribute("CURRENT_WECHAT",wechatId);

            //初始化WechatClientDelegate
            try {
                Wechat wechat = wechatService.selectByKey(wechatId);
                WechatClientDelegate.create(wechatId, wechat.getAppid(), wechat.getAppscret());
            } catch (Exception e) {
                log.error("WechatClientDelegate设置失败", e);
            }

			JSONObject json = new JSONObject();
			json.put("user", user);
			return representation(Message.USER_CHANGE_WECHAT_SUCCESS, json);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
		
	}
	
	@ApiOperation(value="数据初始化", tags="用户接口")
	@ApiResponse(code=200, message="1-数据初始化成功")
	@RequestMapping(value = "init.json", method = RequestMethod.GET)
	public JSONObject init(
			@ApiParam(name="送死", required=true)
				@RequestParam(required = true) String company,
			@ApiParam(name="公众号", required=true)
				@RequestParam(required = true) String wechat,
			HttpServletRequest request, HttpServletResponse response){
		try {
			userService.init(company, wechat);
			return representation(Message.USER_INIT_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
		
	}
	

}
