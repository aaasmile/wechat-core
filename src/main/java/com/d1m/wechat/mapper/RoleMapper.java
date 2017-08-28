package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.RoleDto;
import com.d1m.wechat.model.Role;
import com.d1m.wechat.util.MyMapper;

public interface RoleMapper extends MyMapper<Role> {

	List<RoleDto> search(@Param("sortName") String sortName,
			@Param("sortDir") String sortDir, @Param("companyId") Integer companyId);

	RoleDto searchById(@Param("id") Integer id, @Param("companyId") Integer companyId);
}
