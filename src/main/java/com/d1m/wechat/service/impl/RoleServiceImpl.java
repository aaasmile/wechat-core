package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.RoleDto;
import com.d1m.wechat.dto.UserDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.FunctionMapper;
import com.d1m.wechat.mapper.RoleFunctionMapper;
import com.d1m.wechat.mapper.RoleMapper;
import com.d1m.wechat.mapper.UserMapper;
import com.d1m.wechat.model.Function;
import com.d1m.wechat.model.Role;
import com.d1m.wechat.model.RoleFunction;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.RoleStatus;
import com.d1m.wechat.pamametermodel.RoleModel;
import com.d1m.wechat.service.RoleService;
import com.d1m.wechat.util.Message;

@Service
public class RoleServiceImpl extends BaseService<Role> implements RoleService{
	
	@Autowired
	private RoleMapper roleMapper;
	
	@Autowired
	private FunctionMapper functionMapper;
	
	@Autowired
	private RoleFunctionMapper roleFunctionMapper;
	
	@Autowired
	private UserMapper userMapper;
	
	private void checkRoleNameRepeat(String roleName, Integer companyId){		
		Role record = new Role();
		record.setName(roleName);
		record.setStatus(RoleStatus.INUSED.getValue());
		record.setCompanyId(companyId);
		record = roleMapper.selectOne(record);
		if (record != null){
			throw new WechatException(Message.ROLE_NAME_NOT_REPEAT);
		}
	}
	
	private void checkRoleNameRepeat(RoleModel roleModel){
		Role record = new Role();
		record.setName(roleModel.getName());
		record.setStatus(RoleStatus.INUSED.getValue());
		record.setCompanyId(roleModel.getCompanyId());
		record = roleMapper.selectOne(record);
		if (record != null){
			if (record.getId() != roleModel.getId()){
				throw new WechatException(Message.WECHAT_NAME_NOT_REPEAT);
			}
		}
	}
	
	@Override
	public Mapper<Role> getGenericMapper() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<RoleDto> search(RoleModel roleModel, Integer companyId) {
		roleModel.setCompanyId(companyId);
		List<RoleDto> roleDtos = roleMapper.search(roleModel.getSortName(),
				roleModel.getSortDir(), roleModel.getCompanyId());
		return roleDtos;
	}
	
	@Override
	public RoleDto listByUser(User user) {
		UserDto userDto = userMapper.searchById(user.getId());
		return roleMapper.searchById(userDto.getRoleId(), null);
	}

	@Override
	public synchronized int insert(User user, RoleModel roleModel) {
		notBlank(roleModel, Message.ROLE_NOT_BLANK);
		notBlank(roleModel, Message.ROLE_NAME_NOT_BLANK);
		
		checkRoleNameRepeat(roleModel.getName(), user.getCompanyId());
		
		Role record = new Role();
		record.setName(roleModel.getName());
		
		List<Integer> functionIds = roleModel.getFunctionIds();
		List<Function> functions = new ArrayList<Function>();
		if (functionIds != null && !functionIds.isEmpty()) {
			Function function = null;
			for (Integer functionId : functionIds) {
				function = functionMapper.selectByPrimaryKey(functionId);
				if (function == null) {
					throw new WechatException(Message.FUNCTION_NOT_EXIST);
				}
				functions.add(function);
			}
		}

		Date current = new Date();
		record.setCreatedAt(current);
		if (user != null) {
			record.setCreatorId(user.getId());
			record.setCompanyId(user.getCompanyId());
		}
		record.setDescription(roleModel.getDescription());
		record.setStatus(RoleStatus.INUSED.getValue());
		
		int result = roleMapper.insertSelective(record);
		
		List<RoleFunction> roleFunctions = new ArrayList<RoleFunction>();
		for (Function function : functions) {
			RoleFunction roleFunction = new RoleFunction();
			roleFunction.setCreatedAt(current);
			roleFunction.setCreatorId(user.getId());
			roleFunction.setRoleId(record.getId());
			roleFunction.setFunctionId(function.getId());
			roleFunctions.add(roleFunction);
		}
		if (!roleFunctions.isEmpty()) {
			roleFunctionMapper.insertList(roleFunctions);
		}

		return result;
	}

	@Override
	public RoleDto getById(Integer id, Integer companyId) {
		notBlank(id, Message.ROLE_NOT_BLANK);
		
		return roleMapper.searchById(id, companyId);
	}

	@Override
	public int update(Integer id, RoleModel roleModel, User user) {
		notBlank(id, Message.ROLE_NOT_BLANK);
		notBlank(roleModel.getName(), Message.ROLE_NAME_NOT_BLANK);
		
		roleModel.setCompanyId(user.getCompanyId());
		checkRoleNameRepeat(roleModel);
		
		Role record = new Role();
		record.setName(roleModel.getName());
		List<Integer> functionIds = roleModel.getFunctionIds();
		List<Function> functions = new ArrayList<Function>();
		if (functionIds != null && !functionIds.isEmpty()) {
			Function function = null;
			for (Integer functionId : functionIds) {
				function = functionMapper.selectByPrimaryKey(functionId);
				if (function == null) {
					throw new WechatException(Message.WECHAT_NOT_EXIST);
				}
				functions.add(function);
			}
		}

		Date current = new Date();
		record.setCreatedAt(current);
		if (user != null) {
			record.setCreatorId(user.getId());
		}
		record.setDescription(roleModel.getDescription());
		record.setStatus((byte)1);
		record.setId(id);
		
		List<RoleFunction> roleFunctions = new ArrayList<RoleFunction>();
		List<RoleFunction> result = null;
		List<Integer> ids = new ArrayList<Integer>();
		for (Function function : functions) {
			RoleFunction roleFunction = new RoleFunction();
			roleFunction.setCreatedAt(current);
			roleFunction.setRoleId(record.getId());
			roleFunction.setFunctionId(function.getId());
			roleFunction.setCreatorId(user.getId());
			
			result = roleFunctionMapper.search(roleFunction.getRoleId(), roleFunction.getFunctionId());
			if (result == null || result.isEmpty()){
				roleFunctions.add(roleFunction);
			}else{
				ids.add(function.getId());	
			}
			
		}
		
		result = roleFunctionMapper.search(record.getId(), null);
		for (RoleFunction roleFunction : result){
			if(!ids.contains(roleFunction.getFunctionId())){
				roleFunctionMapper.delete(roleFunction);
			}
		}
		
		if (!roleFunctions.isEmpty()) {
			roleFunctionMapper.insertList(roleFunctions);
		}
		
		return roleMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteById(Integer id) {
		User user = new User();
		user.setRoleId(id);
		user.setStatus((byte)1);
		int count = userMapper.selectCount(user);
		if (count > 0){
			return -1;
		}
		List<RoleFunction> result = roleFunctionMapper.search(id, null);
		for (RoleFunction roleFunction : result){
			roleFunctionMapper.delete(roleFunction);
		}
		Role role = new Role();
		role.setId(id);
		role.setStatus(RoleStatus.DELETED.getValue());
		return roleMapper.updateByPrimaryKeySelective(role);
		 
	}


}
