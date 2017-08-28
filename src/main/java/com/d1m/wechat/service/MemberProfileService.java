package com.d1m.wechat.service;

import com.alibaba.fastjson.JSONArray;
import com.d1m.wechat.dto.MemberProfileDto;
import com.d1m.wechat.model.MemberProfile;
import com.d1m.wechat.pamametermodel.MemberProfileModel;

public interface MemberProfileService extends IService<MemberProfile> {

	MemberProfile getByMemberId(Integer wechatId, Integer memberId);

	Integer getMemberBindStatus(Integer id, Integer wechatId);

}
