package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.SessionDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.OfflineActivityMapper;
import com.d1m.wechat.mapper.SessionMapper;
import com.d1m.wechat.model.OfflineActivity;
import com.d1m.wechat.model.Session;
import com.d1m.wechat.pamametermodel.SessionModel;
import com.d1m.wechat.service.SessionService;
import com.d1m.wechat.util.Message;

@Service
public class SessionServiceImpl extends BaseService<Session> implements SessionService{
	
	@Autowired
	private SessionMapper sessionMapper;
	
	@Autowired
	private OfflineActivityMapper offlineActivityMapper;

	@Override
	public Mapper<Session> getGenericMapper() {
		return sessionMapper;
	}

	@Override
	public SessionDto insert(Integer wechatId, Integer userId, SessionModel model) {
		if (model == null){
			model = new SessionModel();
		}
		
		notBlank(model.getCreatedAt(), Message.HOLA_SESSION_DATE_NOT_BLANK);
		notBlank(model.getSession(), Message.HOLA_SESSION_ACTIVITY_NOT_BLANK);
		notBlank(model.getMemberLimit(), Message.HOLA_SESSION_MEMBER_LIMIT_NOT_BLANK);
		
		Session record = new Session();
		if (model.getOfflineActivityId() != null){
			record.setOfflineActivityId(model.getOfflineActivityId());
		}
		record.setCreatedAt(model.getCreatedAt());
		record.setSession(model.getSession());
		record.setWechatId(wechatId);
		record.setCreatorId(userId);
		record.setStatus((byte)1);
		if(sessionMapper.selectCount(record) > 0){
			throw new WechatException(Message.HOLA_SESSION_INSERT_NOT_REPEAT);
		}
		record.setMemberLimit(model.getMemberLimit());
		sessionMapper.insertSelective(record);
		
		return sessionMapper.searchById(record.getId());
	}

	@Override
	public int updateStatus(Integer id, Byte status) {
		notBlank(id, Message.HOLA_SESSION_ID_NOT_BLANK);
		notBlank(status, Message.HOLA_SESSION_STATUS_NOT_BLANK);
		Session record = new Session();
		record.setId(id);
		record.setStatus(status);
		return sessionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public Session get(Integer id) {
		notBlank(id, Message.HOLA_SESSION_ID_NOT_BLANK);
		Session record = new Session();
		record.setId(id);
		return sessionMapper.selectOne(record);
	}

	@Override
	public int update(Integer id, SessionModel model) {
		notBlank(id, Message.HOLA_SESSION_ID_NOT_BLANK);
		if(model == null){
			model = new SessionModel();
		}
		Session record = new Session();
		record.setId(id);
		if(model.getMemberLimit() != null){
			record.setMemberLimit(model.getMemberLimit());
		}
		return sessionMapper.updateByPrimaryKeySelective(record);
	}

	@Override
	public int deleteById(Integer sessionId, Integer offlineActivityId) {
		int resultCode = 0;
		if(offlineActivityId != null){
			OfflineActivity offlineActivity = new OfflineActivity();
			offlineActivity.setId(offlineActivityId);
			offlineActivity = offlineActivityMapper.selectOne(offlineActivity);
			Date current = new Date();
			Date startDate = offlineActivity.getStartDate();
			Date endDate = offlineActivity.getEndDate();
			if(current.before(startDate)){
				Session record = new Session();
				record.setId(sessionId);
				record.setStatus((byte)0);
				resultCode = sessionMapper.updateByPrimaryKeySelective(record);
			}else if(current.after(endDate)){
				throw new WechatException(Message.HOLA_FINISH_SESSION_NOT_DELETE);
			}else{
				throw new WechatException(Message.HOLA_PROCESS_SESSION_NOT_DELETE);
			}
		}else{
			Session record = new Session();
			record.setId(sessionId);
			record.setStatus((byte)0);
			resultCode = sessionMapper.updateByPrimaryKeySelective(record);
		}
		
		return resultCode;
	}

	@Override
	public List<SessionDto> searchByOfflineActivityId(Integer offlineActivityId) {
		
		return sessionMapper.searchByOfflineActivityId(offlineActivityId);
	}

}
