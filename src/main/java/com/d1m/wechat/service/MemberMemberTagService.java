package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.model.MemberMemberTag;
import com.d1m.wechat.model.Tag;

public interface MemberMemberTagService extends IService<MemberMemberTag> {

	public MemberMemberTag get(Integer wechatId, Integer memberId, Integer memberTagId);

	public void insertList(List<MemberMemberTag> memberMemberAddTags);
	public void insertOrUpdateList(List<Tag> tags);

	public void deleteByPrimaryKey(Integer memberMemberTagId);

	public List<MemberMemberTag> getMemberMemberTagList(MemberMemberTag memberMemberTag);
}
