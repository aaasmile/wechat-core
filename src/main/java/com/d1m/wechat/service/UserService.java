package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.RoleDto;
import com.d1m.wechat.dto.UserDto;
import com.d1m.wechat.dto.WechatDto;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.UserModel;
import com.github.pagehelper.Page;

public interface UserService extends IService<User> {

	User login(String username, String password);

	User create(User user, UserModel userModel);

	List<WechatDto> listVisibleWechat(User user);

	public Page<UserDto> search(UserModel userModel, boolean queryCount);

	UserDto getProfile(User user);

	UserDto getById(Integer id);

	int update(Integer id, UserModel userModel, User user);

	int deleteById(Integer id, Integer companyId);

	List<RoleDto> listVisibleRole(Integer companyId);

	User getByUsername(String username);

	int updatePwd(Integer id, String password);

	List<WechatDto> listAvailableWechat(User user);

	void init(String companyName, String wechatName);
}
