package com.d1m.wechat.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.MemberClickMenuMapper;
import com.d1m.wechat.model.MemberClickMenu;
import com.d1m.wechat.service.MemberClickMenuService;

@Service
public class MemberClickMenuServiceImpl extends BaseService<MemberClickMenu>
		implements MemberClickMenuService {

	private static Logger log = LoggerFactory
			.getLogger(MemberClickMenuServiceImpl.class);

	@Autowired
	private MemberClickMenuMapper memberClickMenuMapper;

	@Override
	public Mapper<MemberClickMenu> getGenericMapper() {
		return memberClickMenuMapper;
	}
}
