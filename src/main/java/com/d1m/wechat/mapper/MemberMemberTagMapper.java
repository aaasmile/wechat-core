package com.d1m.wechat.mapper;

import java.util.List;

import com.d1m.wechat.model.MemberMemberTag;
import com.d1m.wechat.util.MyMapper;

public interface MemberMemberTagMapper extends MyMapper<MemberMemberTag> {
	public List<MemberMemberTag> getMemberMemberTagList(MemberMemberTag memberMemberTag);
	public void insertOrUpdateList(List<MemberMemberTag> memberMemberAddTags);
}