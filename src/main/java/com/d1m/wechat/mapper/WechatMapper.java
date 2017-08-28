package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

import java.util.List;

public interface WechatMapper extends MyMapper<Wechat> {
	
	Page<Wechat> search(@Param("name") String name,
			@Param("status") Byte status, @Param("sortName") String sortName,
			@Param("sortDir") String sortDir, @Param("companyId") Integer companyId);

	Integer getIsSystemRole(@Param("roleId") Integer roleId);

	List<Wechat> getWechatList();
}
