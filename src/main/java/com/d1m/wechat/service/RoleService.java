package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.RoleDto;
import com.d1m.wechat.model.Role;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.RoleModel;

public interface RoleService extends IService<Role>{

//	List<FunctionDto> listVisibleFunction(User user);

	List<RoleDto> search(RoleModel roleModel, Integer companyId);

	int insert(User user, RoleModel roleModel);

	RoleDto getById(Integer id, Integer companyId);

	int update(Integer id, RoleModel roleModel, User user);

	RoleDto listByUser(User user);

	int deleteById(Integer id);
	
	

}
