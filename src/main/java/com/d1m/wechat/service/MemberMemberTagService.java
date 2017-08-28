package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.model.MemberMemberTag;

public interface MemberMemberTagService extends IService<MemberMemberTag> {

	MemberMemberTag get(Integer wechatId, Integer memberId, Integer memberTagId);

	void insertList(List<MemberMemberTag> memberMemberAddTags);

	void deleteByPrimaryKey(Integer memberMemberTagId);

}
