package com.d1m.wechat.service;

import java.util.Date;

import com.d1m.wechat.dto.ReportKeyMatchDto;
import com.d1m.wechat.dto.ReportKeyMatchTopDto;
import com.d1m.wechat.model.MemberReply;
import com.github.pagehelper.Page;

public interface MemberReplyService extends IService<MemberReply> {
	ReportKeyMatchDto keyMatch(Integer wechatId, Date start, Date end);
	
	Page<ReportKeyMatchTopDto> matchTop(Integer wechatId, Date start, Date end, Integer top);
	
	Page<ReportKeyMatchTopDto> unMatchTop(Integer wechatId, Date start, Date end, Integer top);

	Page<ReportKeyMatchTopDto> match(Integer wechatId, Date start, Date end);
}
