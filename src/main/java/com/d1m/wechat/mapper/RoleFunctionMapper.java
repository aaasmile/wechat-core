package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.RoleFunction;
import com.d1m.wechat.util.MyMapper;

public interface RoleFunctionMapper extends MyMapper<RoleFunction> {

	List<RoleFunction> search(@Param("roleId")Integer roleId, 
			@Param("functionId")Integer functionId);
}
