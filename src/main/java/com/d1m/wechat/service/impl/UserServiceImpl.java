package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.RoleDto;
import com.d1m.wechat.dto.UserDto;
import com.d1m.wechat.dto.WechatDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.CompanyMapper;
import com.d1m.wechat.mapper.FunctionMapper;
import com.d1m.wechat.mapper.RoleFunctionMapper;
import com.d1m.wechat.mapper.RoleMapper;
import com.d1m.wechat.mapper.UserMapper;
import com.d1m.wechat.mapper.UserWechatMapper;
import com.d1m.wechat.mapper.WechatMapper;
import com.d1m.wechat.model.Company;
import com.d1m.wechat.model.Role;
import com.d1m.wechat.model.RoleFunction;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.UserWechat;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.model.enums.UserStatus;
import com.d1m.wechat.model.enums.WechatStatus;
import com.d1m.wechat.pamametermodel.UserModel;
import com.d1m.wechat.service.InitDataService;
import com.d1m.wechat.service.UserService;
import com.d1m.wechat.util.AppContextUtils;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class UserServiceImpl extends BaseService<User> implements UserService {

	@Autowired
	private UserMapper userMapper;

	@Autowired
	private WechatMapper wechatMapper;

	@Autowired
	private UserWechatMapper userWechatMapper;
	
	@Autowired
	private CompanyMapper companyMapper;
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private FunctionMapper functionMapper;
	
	@Autowired
	private RoleFunctionMapper roleFunctionMapper;

	public void setUserMapper(UserMapper userMapper) {
		this.userMapper = userMapper;
	}

	@Override
	public Mapper<User> getGenericMapper() {
		return userMapper;
	}

	@Override
	public User login(String username, String password) {
		notBlank(username, Message.USER_NAME_NOT_BLANK);
		notBlank(password, Message.USER_PASSWORD_NOT_BLANK);
		User user = userMapper.login(username, DigestUtils.sha256Hex(password));
		if (user == null) {
			throw new WechatException(Message.USER_NAME_OR_PASSWORD_ERROR);
		}
		return user;
	}

	@Override
	public synchronized User create(User user, UserModel userModel) {
		notBlank(userModel, Message.USER_NOT_BLANK);
		notBlank(userModel.getUsername(), Message.USER_NAME_NOT_BLANK);
		notBlank(userModel.getPassword(), Message.USER_PASSWORD_NOT_BLANK);
		notBlank(userModel.getConfirmPassword(),
				Message.USER_PASSWORD_NOT_BLANK);
		if (!StringUtils.equals(userModel.getPassword(),
				userModel.getConfirmPassword())) {
			throw new WechatException(Message.USER_NAME_OR_PASSWORD_ERROR);
		}
		User record = new User();
		record.setUsername(userModel.getUsername());
		
		checkUserNameRepeat(userModel.getUsername(), user.getCompanyId());

		List<Integer> wechatIds = userModel.getWechatIds();
		List<Wechat> wechats = new ArrayList<Wechat>();
		if (wechatIds != null && !wechatIds.isEmpty()) {
			Wechat wechat = null;
			for (Integer wechatId : wechatIds) {
				wechat = wechatMapper.selectByPrimaryKey(wechatId);
				if (wechat == null
						|| wechat.getStatus() == null
						|| wechat.getStatus() == WechatStatus.DELETED
								.getValue()) {
					throw new WechatException(Message.WECHAT_NOT_EXIST);
				}
				wechats.add(wechat);
			}
		}

		Date current = new Date();
		record.setCreatedAt(current);
		if (user != null) {
			record.setCreatorId(user.getId());
			record.setCompanyId(user.getCompanyId());
		}
		record.setEmail(userModel.getEmail());
		record.setLocalHeadImgUrl(userModel.getAvatar());
		record.setMobile(userModel.getMobile());
		record.setPassword(DigestUtils.sha256Hex(userModel.getPassword()));
		record.setStatus(UserStatus.INUSED.getValue());
		record.setRoleId(userModel.getRoleId());
		userMapper.insertSelective(record);

		List<UserWechat> userWechats = new ArrayList<UserWechat>();
		for (Wechat wechat : wechats) {
			UserWechat userWechat = new UserWechat();
			userWechat.setCreatedAt(current);
			userWechat.setUserId(record.getId());
			userWechat.setWechatId(wechat.getId());
			if(user.getWechatId().equals(wechat.getId().intValue())){
				userWechat.setIsUse((byte)1);
			}else{
				userWechat.setIsUse((byte)0);
			}
			userWechats.add(userWechat);
		}
		if (!userWechats.isEmpty()) {
			userWechatMapper.insertList(userWechats);
		}
		
		return record;
	}

	@Override
	public List<WechatDto> listVisibleWechat(User user) {
		return userMapper.listVisibleWechat(user.getId(), user.getCompanyId());
	}
	
	@Override
	public List<WechatDto> listAvailableWechat(User user) {
		return userMapper.listAvailableWechat(user.getCompanyId());
	}

	@Override
	public Page<UserDto> search(UserModel userModel, boolean queryCount) {
		if (userModel.pagable()) {
			PageHelper.startPage(userModel.getPageNum(),
					userModel.getPageSize(), queryCount);
		}
		return userMapper.search(userModel.getSortName(),
				userModel.getSortDir(), userModel.getCompanyId());
	}

	@Override
	public UserDto getProfile(User user) {
		return userMapper.getProfile(user.getId(), user.getCompanyId());
	}

	@Override
	public UserDto getById(Integer id) {
		notBlank(id, Message.USER_NOT_BLANK);
		return userMapper.searchById(id);
	}

	@Override
	public synchronized int update(Integer id, UserModel userModel, User user) {
		notBlank(id, Message.USER_NOT_BLANK);
		notBlank(userModel.getUsername(), Message.USER_NAME_NOT_BLANK);
		User record = new User();
		record.setUsername(userModel.getUsername());
		
		userModel.setId(id);
		userModel.setCompanyId(user.getCompanyId());
		checkUserNameRepeat(userModel);
		
		List<Integer> wechatIds = userModel.getWechatIds();
		List<Wechat> wechats = new ArrayList<Wechat>();
		if (wechatIds != null && !wechatIds.isEmpty()) {
			Wechat wechat = null;
			for (Integer wechatId : wechatIds) {
				wechat = wechatMapper.selectByPrimaryKey(wechatId);
				if (wechat == null
						|| wechat.getStatus() == null
						|| wechat.getStatus() == WechatStatus.DELETED
								.getValue()) {
					throw new WechatException(Message.WECHAT_NOT_EXIST);
				}
				wechats.add(wechat);
			}
		}

		Date current = new Date();
		record.setEmail(userModel.getEmail());
		record.setLocalHeadImgUrl(userModel.getAvatar());
		record.setMobile(userModel.getMobile());
		record.setId(id);
		record.setRoleId(userModel.getRoleId());
		
		List<UserWechat> userWechats = new ArrayList<UserWechat>();
		List<UserWechat> result = null;
		List<Integer> ids = new ArrayList<Integer>();
		int num = 1;
		int mark = 0;
		for(Wechat wechat : wechats){
			if(wechat.getId().equals(user.getWechatId().intValue())){
				mark = 1;
			}
		}
		
		for (Wechat wechat : wechats) {
			UserWechat userWechat = new UserWechat();
			userWechat.setCreatedAt(current);
			userWechat.setUserId(record.getId());
			userWechat.setWechatId(wechat.getId());
			
			result = userWechatMapper.search(userWechat.getUserId(), userWechat.getWechatId(), user.getCompanyId());
			if (result == null || result.isEmpty()){
				if(mark == 1){
					userWechat.setIsUse((byte)0);
				}else{
					if(num == 1){
						userWechat.setIsUse((byte)1);
						num = 0;
					}else{
						userWechat.setIsUse((byte)0);
					}
				}
				userWechats.add(userWechat);
			}else{
				ids.add(wechat.getId());	
			}
			
		}
		
		result = userWechatMapper.search(record.getId(), null, user.getCompanyId());
		for (UserWechat userWechat : result){
			if(!ids.contains(userWechat.getWechatId())){
				userWechatMapper.delete(userWechat);
			}
		}
		
		if (!userWechats.isEmpty()) {
			userWechatMapper.insertList(userWechats);
		}
		
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteById(Integer id, Integer companyId) {
		List<UserWechat> result = userWechatMapper.search(id, null, companyId);
		for (UserWechat userWechat : result){
			userWechatMapper.delete(userWechat);
		}
		User user = new User();
		user.setId(id);
		user.setStatus(UserStatus.DELETED.getValue());
		return userMapper.updateByPrimaryKeySelective(user);
	}

	@Override
	public List<RoleDto> listVisibleRole(Integer companyId) {
		return userMapper.listAvailableRole(companyId);
	}

	@Override
	public User getByUsername(String username) {
		User user = new User();
		user.setUsername(username);
		return userMapper.selectOne(user);
	}

	@Override
	public int updatePwd(Integer id, String password) {
		notBlank(id, Message.USER_NOT_BLANK);
		notBlank(password, Message.USER_PASSWORD_NOT_BLANK);
		User record = new User();
		record.setId(id);
		record = userMapper.selectOne(record);
		record.setPassword(DigestUtils.sha256Hex(password));
		record.setCounter(0);
		return userMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public void init(String companyName, String wechatName) {
		Company company = new Company();
		company.setName(companyName);
		company.setStatus((byte)1);
		int companyResult = companyMapper.selectCount(company);
		if (companyResult == 0){
			companyMapper.insertSelective(company);
		}else{
			throw new WechatException(Message.USER_ALREADY_FINISH_INIT);
		}
		company = companyMapper.selectOne(company);
		
		Role role = new Role();
		role.setName("系统管理员");
		role.setStatus((byte)1);
		role.setCompanyId(company.getId());
		int roleResult = roleMapper.selectCount(role);
		if (roleResult == 0){
			createSystemRole(role);
		}else{
			role = roleMapper.selectOne(role);
		}
		
		User user = new User();
		user.setUsername(companyName);
		user.setStatus((byte)1);
		user.setCompanyId(company.getId());
		user.setRoleId(role.getId());
		int userResult = userMapper.selectCount(user);
		if (userResult == 0){
			user.setPassword(DigestUtils.sha256Hex("12345"));
			user.setCreatedAt(new Date());
			userMapper.insertSelective(user);
		}
		
		Wechat wechat = new Wechat();
		wechat.setName(wechatName);
		wechat.setStatus((byte)1);
		wechat.setCompanyId(company.getId());
		int wechatResult = wechatMapper.selectCount(wechat);
		if (wechatResult == 0){
			createInitWechat(wechat, user.getId());
		}
		
		InitDataService initDataService = AppContextUtils.getBean(InitDataService.class);
		initDataService.initDefaultAutoReply();
		initDataService.initSubscribeAutoReply();
	}

	@Override
	public Integer updateGuide(Integer id) {
		User user =new User();
		user.setId(id);
		user.setNoviceGuide((byte)1);
		Integer result =userMapper.updateByPrimaryKeySelective(user);
		return  result;
	}

	private void createInitWechat(Wechat wechat, Integer userId) {
		wechat.setPriority(1);
		wechat.setCreatedAt(new Date());
		wechat.setUserId(userId);
		wechatMapper.insertSelective(wechat);
		
		UserWechat userWechat = new UserWechat();
		userWechat.setCreatedAt(new Date());
		userWechat.setUserId(userId);
		userWechat.setWechatId(wechat.getId());
		userWechat.setIsUse((byte)1);
		userWechatMapper.insertSelective(userWechat);
		
	}

	private void createSystemRole(Role role) {
		role.setCreatedAt(new Date());
		role.setIssystemrole((byte)1);
		roleMapper.insertSelective(role);
		
		List<Integer> allFunctionIds = functionMapper.getAll();
		List<RoleFunction> roleFunctions = new ArrayList<RoleFunction>();
		for(Integer functionId : allFunctionIds){
			RoleFunction roleFunction = new RoleFunction();
			roleFunction.setCreatedAt(new Date());
			roleFunction.setRoleId(role.getId());
			roleFunction.setFunctionId(functionId);
			roleFunctions.add(roleFunction);
		}
		if (!roleFunctions.isEmpty()) {
			roleFunctionMapper.insertList(roleFunctions);
		}
		
	}
	
	private void checkUserNameRepeat(String userName, Integer companyId){		
		User record = new User();
		record.setUsername(userName);
		record.setStatus(UserStatus.INUSED.getValue());
		record.setCompanyId(companyId);
		record = userMapper.selectOne(record);
		if (record != null){
			throw new WechatException(Message.USER_NAME_EXIST);
		}
	}
	
	private void checkUserNameRepeat(UserModel userModel){
		User record = new User();
		record.setUsername(userModel.getUsername());
		record.setStatus(UserStatus.INUSED.getValue());
		record.setCompanyId(userModel.getCompanyId());
		record = userMapper.selectOne(record);
		if (record != null){
			if (record.getId() != userModel.getId()){
				throw new WechatException(Message.USER_NAME_EXIST);
			}
		}
	}

}
