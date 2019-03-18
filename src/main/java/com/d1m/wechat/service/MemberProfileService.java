package com.d1m.wechat.service;

import com.d1m.wechat.model.MemberProfile;

import java.util.List;

public interface MemberProfileService extends IService<MemberProfile> {

	MemberProfile getByMemberId(Integer wechatId, Integer memberId);

	Integer getMemberBindStatus(Integer id, Integer wechatId);

	List<MemberProfile> getByWechatId(Integer wechatId);
}
