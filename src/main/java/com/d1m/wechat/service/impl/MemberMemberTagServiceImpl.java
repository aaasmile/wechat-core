package com.d1m.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.MemberMemberTagMapper;
import com.d1m.wechat.model.MemberMemberTag;
import com.d1m.wechat.service.MemberMemberTagService;

@Service
public class MemberMemberTagServiceImpl extends BaseService<MemberMemberTag>
		implements MemberMemberTagService {

	@Autowired
	private MemberMemberTagMapper memberMemberTagMapper;

	@Override
	public Mapper<MemberMemberTag> getGenericMapper() {
		return memberMemberTagMapper;
	}

	@Override
	public MemberMemberTag get(Integer wechatId, Integer memberId,
			Integer memberTagId) {
		MemberMemberTag record = new MemberMemberTag();
		record.setMemberId(memberId);
		record.setMemberTagId(memberTagId);
		record.setWechatId(wechatId);
		return memberMemberTagMapper.selectOne(record);
	}

	@Override
	public void insertList(List<MemberMemberTag> memberMemberAddTags) {
		memberMemberTagMapper.insertList(memberMemberAddTags);
	}

	@Override
	public void deleteByPrimaryKey(Integer memberMemberTagId) {
		memberMemberTagMapper.deleteByPrimaryKey(memberMemberTagId);
	}

}
