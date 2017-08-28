package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.RoleDto;
import com.d1m.wechat.dto.UserDto;
import com.d1m.wechat.dto.WechatDto;
import com.d1m.wechat.model.User;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface UserMapper extends MyMapper<User> {
	
	User login(@Param("username") String username,
			@Param("password") String password);

	List<WechatDto> listVisibleWechat(@Param("userId") Integer userId, 
			@Param("companyId") Integer companyId);
	
	List<WechatDto> listAvailableWechat(@Param("companyId") Integer companyId);

	Page<UserDto> search(@Param("sortName") String sortName,
			@Param("sortDir") String sortDir, @Param("companyId") Integer companyId);

	UserDto getProfile(@Param("userId") Integer userId, @Param("companyId") Integer companyId);

	UserDto searchById(@Param("id") Integer id);

	List<RoleDto> listAvailableRole(@Param("companyId") Integer companyId);
	
}