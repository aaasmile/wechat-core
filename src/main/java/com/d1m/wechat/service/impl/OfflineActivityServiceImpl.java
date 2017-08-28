package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.BusinessItemDto;
import com.d1m.wechat.dto.OfflineActivityDto;
import com.d1m.wechat.dto.SessionDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.OfflineActivityBusinessMapper;
import com.d1m.wechat.mapper.OfflineActivityMapper;
import com.d1m.wechat.mapper.SessionMapper;
import com.d1m.wechat.model.OfflineActivity;
import com.d1m.wechat.model.OfflineActivityBusiness;
import com.d1m.wechat.model.Session;
import com.d1m.wechat.pamametermodel.OfflineActivityModel;
import com.d1m.wechat.service.OfflineActivityService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class OfflineActivityServiceImpl extends BaseService<OfflineActivity> implements OfflineActivityService{

	@Autowired
	private OfflineActivityMapper offlineActivityMapper;
	
	@Autowired
	private OfflineActivityBusinessMapper offlineActivityBusinessMapper;
	
	@Autowired
	private SessionMapper sessionMapper;
	
	@Override
	public Mapper<OfflineActivity> getGenericMapper() {
		return offlineActivityMapper;
	}

	@Override
	public Page<OfflineActivityDto> search(Integer wechatId,
			OfflineActivityModel model, boolean queryCount) {
		if (model.pagable()) {
			PageHelper.startPage(model.getPageNum(),
					model.getPageSize(), queryCount);
		}
		return offlineActivityMapper.search(wechatId, model.getQuery(),
				model.getActivityStatus(), model.getStatus(),
				model.getSortName(), model.getSortDir());
	}

	@Override
	public OfflineActivity insert(Integer wechatId, Integer userId,
			OfflineActivityModel model) {
		if (model == null) {
			model = new OfflineActivityModel();
		}
		
		notBlank(model.getName(), Message.HOLA_OFFLINE_ACTIVITY_NAME_NOT_BLANK);
		notBlank(model.getStart(), Message.HOLA_OFFLINE_ACTIVITY_START_DATE_NOT_BLANK);
		notBlank(model.getEnd(), Message.HOLA_OFFLINE_ACTIVITY_END_DATE_NOT_BLANK);
		
		OfflineActivity offlineActivity = new OfflineActivity();
		offlineActivity.setName(model.getName());
		int count = offlineActivityMapper.selectCount(offlineActivity);
		if (count > 0){
			throw new WechatException(Message.HOLA_OFFLINE_ACTIVITY_NAME_NOT_REPEAT);
		}
		offlineActivity.setCreatorId(userId);
		offlineActivity.setWechatId(wechatId);
		if(model.getSharePic() != null){
			offlineActivity.setSharePic(model.getSharePic());
		}
		Date current = new Date();
		offlineActivity.setStartDate(model.getStart());
		offlineActivity.setEndDate(model.getEnd());
		offlineActivity.setCreatedAt(current);
		offlineActivity.setStatus((byte)1);
		offlineActivityMapper.insertSelective(offlineActivity);
		
		OfflineActivity record = new OfflineActivity();
		record.setName(model.getName());
		record = offlineActivityMapper.selectOne(record);
		List<Integer> businessIds = model.getBusinessIds();
		List<OfflineActivityBusiness> list = new ArrayList<OfflineActivityBusiness>();
		if (businessIds != null && !businessIds.isEmpty()) {
			for (Integer businessId : businessIds){
				OfflineActivityBusiness offlineActivityBusiness = new OfflineActivityBusiness();
				offlineActivityBusiness.setBusinessId(businessId);
				offlineActivityBusiness.setOfflineActivityId(record.getId());
				offlineActivityBusiness.setWechatId(wechatId);
				offlineActivityBusiness.setCreatorId(userId);
				offlineActivityBusiness.setCreatedAt(current);
				list.add(offlineActivityBusiness);
			}
		}
		
		List<Integer> sessionIds = model.getSessionIds();
		if (sessionIds != null && !sessionIds.isEmpty()) {
			for (Integer sessionId : sessionIds){
				Session session = new Session();
				session.setId(sessionId);
				session.setOfflineActivityId(record.getId());
				sessionMapper.updateByPrimaryKeySelective(session);
			}
		}
		
		if(!list.isEmpty()){
			offlineActivityBusinessMapper.insertList(list);
		}
		
		return record;
	}

	@Override
	public OfflineActivityDto get(Integer wechatId, Integer id) {
		return offlineActivityMapper.get(wechatId, id);
	}

	@Override
	public int update(Integer wechatId, Integer userId, Integer id, OfflineActivityModel model) {
		if (model == null) {
			model = new OfflineActivityModel();
		}
		notBlank(id, Message.HOLA_OFFLINE_ACTIVITY_NOT_EXIST);
		notBlank(model.getName(), Message.HOLA_OFFLINE_ACTIVITY_NAME_NOT_BLANK);
		notBlank(model.getStart(), Message.HOLA_OFFLINE_ACTIVITY_START_DATE_NOT_BLANK);
		notBlank(model.getEnd(), Message.HOLA_OFFLINE_ACTIVITY_END_DATE_NOT_BLANK);
		
		OfflineActivity offlineActivity = new OfflineActivity();
		offlineActivity.setName(model.getName());
		OfflineActivity count = offlineActivityMapper.selectOne(offlineActivity);
		if (count != null){
			if (count.getId() != id){
				throw new WechatException(Message.HOLA_OFFLINE_ACTIVITY_NAME_NOT_REPEAT);
			}
		}
		
		offlineActivity.setId(id);
		if(model.getSharePic() != null){
			offlineActivity.setSharePic(model.getSharePic());
		}
		offlineActivity.setStartDate(model.getStart());
		offlineActivity.setEndDate(model.getEnd());
		int resultCode = offlineActivityMapper.updateByPrimaryKeySelective(offlineActivity);
		
		OfflineActivityDto oriDto = offlineActivityMapper.get(wechatId, id);
		List<BusinessItemDto> oriBusinessList = oriDto.getBusinessList();
		List<Integer> oriBusinessIds = new ArrayList<Integer>();
		for(BusinessItemDto item:oriBusinessList){
			if(item!=null){
				oriBusinessIds.add(item.getId());
			}
		}
		List<Integer> businessIds = model.getBusinessIds();
		List<OfflineActivityBusiness> addList = new ArrayList<OfflineActivityBusiness>();
		List<Integer> deleteBusinessIds = new ArrayList<Integer>();
		if (businessIds != null && !businessIds.isEmpty()) {
			outter: for(Integer businessId:businessIds){
				for(Integer oriBusinessId:oriBusinessIds){
					if(oriBusinessId.equals(businessId)){
						continue outter;
					}
				}
				OfflineActivityBusiness offlineActivityBusiness = new OfflineActivityBusiness();
				offlineActivityBusiness.setOfflineActivityId(id);
				offlineActivityBusiness.setBusinessId(businessId);
				offlineActivityBusiness.setWechatId(wechatId);
				offlineActivityBusiness.setCreatorId(userId);
				offlineActivityBusiness.setCreatedAt(new Date());
				addList.add(offlineActivityBusiness);
			}
		
			outter: for(Integer oriBusinessId:oriBusinessIds){
				for(Integer businessId:businessIds){
					if(businessId.equals(oriBusinessId)){
						continue outter;
					}
				}
				deleteBusinessIds.add(oriBusinessId);
			}
			
		}else{
			deleteBusinessList(id, oriBusinessIds);
		}
		if(!addList.isEmpty()){
			offlineActivityBusinessMapper.insertList(addList);
		}
		if(!deleteBusinessIds.isEmpty()){
			deleteBusinessList(id, deleteBusinessIds);
		}
		
		List<SessionDto> oriSessionList = oriDto.getSessionList();
		List<Integer> oriSessionIds = new ArrayList<Integer>();
		for (SessionDto item:oriSessionList){
			if(item!=null){
				oriSessionIds.add(item.getId());
			}
		}
		List<Integer> sessionIds = model.getSessionIds();
		List<Integer> deleteSessionIds = new ArrayList<Integer>();
		if (sessionIds != null && !sessionIds.isEmpty()) {
			outter: for(Integer sessionId:sessionIds){
				for(Integer oriSessionId:oriSessionIds){
					if(oriSessionId.equals(sessionId)){
						continue outter;
					}
				}
				Session session = new Session();
				session.setId(sessionId);
				session.setOfflineActivityId(id);
				sessionMapper.updateByPrimaryKeySelective(session);
			}
		
			outter: for(Integer oriSessionId:oriSessionIds){
				for(Integer sessionId:sessionIds){
					if(sessionId.equals(oriSessionId)){
						continue outter;
					}
				}
				deleteSessionIds.add(oriSessionId);
			}
			
		}else{
			deleteSessionList(id, oriSessionIds);
		}
		if(!deleteSessionIds.isEmpty()){
			deleteSessionList(id, deleteSessionIds);
		}
		
		
		return resultCode;
	}
	
	public void deleteBusinessList(Integer offlineActivityId, List<Integer> businessIds){
		offlineActivityBusinessMapper.deleteBusinessList(offlineActivityId, businessIds);
	}
	
	private void deleteSessionList(Integer offlineActivityId, List<Integer> sessionIds) {
		sessionMapper.deleteSessionList(offlineActivityId, sessionIds);
	}

	@Override
	public int deleteById(Integer wechatId, Integer id) {
		notBlank(id, Message.HOLA_OFFLINE_ACTIVITY_NOT_EXIST);
		OfflineActivity record = new OfflineActivity();
		record.setId(id);
		record.setWechatId(wechatId);
		record = offlineActivityMapper.selectOne(record);
		int resultCode = 0;
		if(record!=null){
			Date current = new Date();
			Date startDate = record.getStartDate();
			if(current.before(startDate)){
				record.setStatus((byte)0);
				resultCode = offlineActivityMapper.updateByPrimaryKeySelective(record);
				
				List<Integer> businessIds = offlineActivityBusinessMapper.selectByOfflineActivityId(id);
				if(businessIds!=null && !businessIds.isEmpty()){
					deleteBusinessList(id, businessIds);
				}
				
				List<Integer> sessionIds = sessionMapper.selectByOfflineActivityId(id);
				if(sessionIds!=null && !sessionIds.isEmpty()){
					deleteSessionList(id, sessionIds);
				}
				
			}else{
				throw new WechatException(Message.HOLA_NOT_PREPARE_OFFLINE_ACTIVITY_NOT_DELETE);
			}
		}else{
			throw new WechatException(Message.HOLA_OFFLINE_ACTIVITY_NOT_EXIST);
		}
		return resultCode;
	}

	@Override
	public List<BusinessItemDto> getBusiness(Integer wechatId, String query) {
		return offlineActivityMapper.getVisibleBusiness(wechatId, query);
	}
	
}
