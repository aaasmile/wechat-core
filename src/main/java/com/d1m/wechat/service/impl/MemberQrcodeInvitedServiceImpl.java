package com.d1m.wechat.service.impl;

import com.d1m.wechat.mapper.MemberQrcodeInvitedMapper;
import com.d1m.wechat.model.MemberQrcodeInvited;
import com.d1m.wechat.service.MemberQrcodeInvitedService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import javax.annotation.Resource;
import java.util.List;

@Slf4j
@Service
public class MemberQrcodeInvitedServiceImpl extends BaseService<MemberQrcodeInvited> implements MemberQrcodeInvitedService {

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
