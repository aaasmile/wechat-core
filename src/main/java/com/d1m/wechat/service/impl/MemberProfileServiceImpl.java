package com.d1m.wechat.service.impl;

import com.d1m.wechat.mapper.MemberProfileMapper;
import com.d1m.wechat.model.MemberProfile;
import com.d1m.wechat.service.MemberProfileService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.List;

@Service
public class MemberProfileServiceImpl extends BaseService<MemberProfile>
		implements MemberProfileService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private MemberProfileMapper memberProfileMapper;

	@Override
	public Mapper<MemberProfile> getGenericMapper() {
		return memberProfileMapper;
	}

	@Override
	public MemberProfile getByMemberId(Integer wechatId, Integer memberId) {
		return memberProfileMapper.getByMemberId(wechatId, memberId);
	}

	@Override
	public Integer getMemberBindStatus(Integer id, Integer wechatId) {
		MemberProfile record = new MemberProfile();
		record.setMemberId(id);
		record.setWechatId(wechatId);
		Integer resultCode = null;
		int count = memberProfileMapper.selectCount(record);
		if (count == 0) {
			resultCode = null;
		} else {
			resultCode = memberProfileMapper.getMemberBindStatus(id, wechatId);
		}
		return resultCode;
	}

	@Override
	public List<MemberProfile> getByWechatId(Integer wechatId) {
		return memberProfileMapper.getByWechatId(wechatId);
	}

}
