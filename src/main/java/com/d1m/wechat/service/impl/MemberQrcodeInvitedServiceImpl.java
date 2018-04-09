package com.d1m.wechat.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d1m.wechat.mapper.MemberQrcodeInvitedMapper;
import com.d1m.wechat.model.MemberQrcodeInvited;
import com.d1m.wechat.service.MemberQrcodeInvitedService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class MemberQrcodeInvitedServiceImpl extends BaseService<MemberQrcodeInvited> implements MemberQrcodeInvitedService {
	
	private static final Logger log = LoggerFactory.getLogger(MemberQrcodeInvitedServiceImpl.class);

	@Autowired
	private MemberQrcodeInvitedMapper invitedMapper;

	@Override
	public int create(MemberQrcodeInvited memberQrcodeInvited) {
		return invitedMapper.insert(memberQrcodeInvited);
	}

	@Override
	public List<MemberQrcodeInvited> getListOrderByCreatedAt(Integer wechatId, Integer memberId) {
		MemberQrcodeInvited memberQrcodeInvited = new MemberQrcodeInvited();
		memberQrcodeInvited.setMemberId(memberId);
		memberQrcodeInvited.setWechatId(wechatId);
		return invitedMapper.getListOrderByCreatedAt(memberQrcodeInvited);
	}

	@Override
	public Mapper<MemberQrcodeInvited> getGenericMapper() {
		return invitedMapper;
	}
}
