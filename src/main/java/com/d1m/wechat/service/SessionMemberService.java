package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.OfflineActivityDto;
import com.d1m.wechat.dto.SessionMemberDto;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.SessionMember;
import com.d1m.wechat.pamametermodel.SessionMemberApplyModel;
import com.d1m.wechat.pamametermodel.SessionMemberModel;
import com.github.pagehelper.Page;

public interface SessionMemberService extends IService<SessionMember>{

	Page<SessionMemberDto> search(Integer wechatId, SessionMemberModel model,
			boolean queryCount);

	int insert(SessionMemberApplyModel model, Member member);

	OfflineActivityDto searchById(Member member, Integer offlineActivityId);

	List<OfflineActivityDto> searchList(Member member);

}
