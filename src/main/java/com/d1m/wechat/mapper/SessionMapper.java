package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.SessionDto;
import com.d1m.wechat.model.Session;
import com.d1m.wechat.util.MyMapper;

public interface SessionMapper extends MyMapper<Session> {

	void deleteSessionList(@Param("offlineActivityId") Integer offlineActivityId, 
			@Param("sessionIds") List<Integer> sessionIds);

	List<Integer> selectByOfflineActivityId(@Param("offlineActivityId") Integer offlineActivityId);

	List<SessionDto> searchByOfflineActivityId(@Param("offlineActivityId") Integer offlineActivityId);

	SessionDto searchById(@Param("id") Integer id);
}
