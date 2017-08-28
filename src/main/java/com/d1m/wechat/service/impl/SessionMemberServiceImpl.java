package com.d1m.wechat.service.impl;

import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.OfflineActivityDto;
import com.d1m.wechat.dto.SessionMemberDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.AreaInfoMapper;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.mapper.SessionMapper;
import com.d1m.wechat.mapper.SessionMemberMapper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.Session;
import com.d1m.wechat.model.SessionMember;
import com.d1m.wechat.pamametermodel.SessionMemberApplyModel;
import com.d1m.wechat.pamametermodel.SessionMemberModel;
import com.d1m.wechat.service.SessionMemberService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class SessionMemberServiceImpl extends BaseService<SessionMember> implements SessionMemberService{

	@Autowired
	private SessionMemberMapper sessionMemberMapper;
	
	@Autowired
	private AreaInfoMapper areaInfoMapper;
	
	@Autowired
	private MemberMapper memberMapper;
	
	@Autowired
	private SessionMapper sessionMapper;
	
	@Override
	public Mapper<SessionMember> getGenericMapper() {
		return sessionMemberMapper;
	}

	@Override
	public Page<SessionMemberDto> search(Integer wechatId,
			SessionMemberModel model, boolean queryCount) {
		if (model.pagable()) {
			PageHelper.startPage(model.getPageNum(),
					model.getPageSize(), queryCount);
		}
		return sessionMemberMapper.search(wechatId, model.getOfflineActivityId(), 
				model.getPhone(), model.getDate(), model.getSession(),
				model.getSortName(), model.getSortDir());
	}

	@Override
	public int insert(SessionMemberApplyModel model, Member member) {
		SessionMember record = new SessionMember();
		record.setOpenId(member.getOpenId());
		record.setName(model.getName());
		record.setPhone(model.getPhone());
		Integer country = areaInfoMapper.selectIdByName("中国", null);
		Integer province = areaInfoMapper.selectIdByName(model.getProvince(), country);
		Integer city = areaInfoMapper.selectIdByName(model.getCity(), province);
		record.setProvince(province);
		record.setCity(city);
		record.setOfflineActivityId(model.getOfflineActivityId());
		record.setBusinessId(model.getBusinessId());
		record.setSessionId(model.getSessionId());
		record.setCreatedAt(new Date());
		record.setWechatId(member.getWechatId());
		
		int count = sessionMemberMapper.selectCount(record);
		if (count>0){
			throw new WechatException(Message.HOLA_SESSION_MEMBER_ALREADY_APPLY);
		}
		int resultCode = sessionMemberMapper.insertSelective(record);
		
		Session session = new Session();
		session.setId(model.getSessionId());
		session = sessionMapper.selectOne(session);
		if(session.getApply() < session.getMemberLimit()){
			session.setApply(session.getApply()+1);
			sessionMapper.updateByPrimaryKeySelective(session);
		}else{
			throw new WechatException(Message.HOLA_SESSION_MEMBER_APPLY_FAIL);
		}
		
		return resultCode;
	}

	@Override
	public OfflineActivityDto searchById(Member member, Integer offlineActivityId) {
		
		return sessionMemberMapper.searchById(member.getWechatId(), offlineActivityId);
	}

	@Override
	public List<OfflineActivityDto> searchList(Member member) {
		return sessionMemberMapper.searchWxList(member.getWechatId());
	}
	

}
