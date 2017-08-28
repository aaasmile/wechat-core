package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.SessionDto;
import com.d1m.wechat.model.Session;
import com.d1m.wechat.pamametermodel.SessionModel;

public interface SessionService extends IService<Session>{

	SessionDto insert(Integer wechatId, Integer userId, SessionModel model);

	int updateStatus(Integer id, Byte status);

	Session get(Integer id);

	int update(Integer id, SessionModel model);

	int deleteById(Integer sessionId, Integer offlineActivityId);

	List<SessionDto> searchByOfflineActivityId(Integer offlineActivityId);

}
