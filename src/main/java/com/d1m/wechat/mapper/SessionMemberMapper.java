package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.OfflineActivityDto;
import com.d1m.wechat.dto.SessionMemberDto;
import com.d1m.wechat.model.SessionMember;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface SessionMemberMapper extends MyMapper<SessionMember> {

	Page<SessionMemberDto> search(@Param("wechatId") Integer wechatId, 
			@Param("offlineActivityId") Integer offlineActivityId, @Param("phone") String phone, 
			@Param("date") String date, @Param("session") String session, 
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

	List<OfflineActivityDto> searchWxList(@Param("wechatId") Integer wechatId);

	OfflineActivityDto searchById(@Param("wechatId") Integer wechatId,
			@Param("offlineActivityId") Integer offlineActivityId);
	
}