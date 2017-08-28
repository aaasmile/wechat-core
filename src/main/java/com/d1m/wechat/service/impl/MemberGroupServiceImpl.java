package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.MemberGroupMapper;
import com.d1m.wechat.model.MemberGroup;
import com.d1m.wechat.service.MemberGroupService;

@Service
public class MemberGroupServiceImpl extends BaseService<MemberGroup> implements
		MemberGroupService {

	@Autowired
	private MemberGroupMapper memberGroupMapper;

	@Override
	public Mapper<MemberGroup> getGenericMapper() {
		return memberGroupMapper;
	}

}
