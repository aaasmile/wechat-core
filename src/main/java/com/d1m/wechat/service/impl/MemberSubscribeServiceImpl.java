package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.MemberSubscribeMapper;
import com.d1m.wechat.model.MemberSubscribe;
import com.d1m.wechat.service.MemberSubscribeService;

@Service
public class MemberSubscribeServiceImpl extends BaseService<MemberSubscribe>
		implements MemberSubscribeService {

	@Autowired
	private MemberSubscribeMapper memberSubscribeMapper;

	@Override
	public Mapper<MemberSubscribe> getGenericMapper() {
		return memberSubscribeMapper;
	}

}
