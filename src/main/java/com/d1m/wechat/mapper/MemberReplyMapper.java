package com.d1m.wechat.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.ReportKeyMatchTopDto;
import com.d1m.wechat.dto.ReportKeyMatchTrendDto;
import com.d1m.wechat.model.MemberReply;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface MemberReplyMapper extends MyMapper<MemberReply> {
	List<ReportKeyMatchTrendDto> matchTrend(
			@Param("wechatId") Integer wechatId, @Param("start") Date start,
			@Param("end") Date end);
	
	Page<ReportKeyMatchTopDto> matchTop(
			@Param("wechatId") Integer wechatId, 
			@Param("start") Date start, 
			@Param("end") Date end,
			@Param("top") Integer top);
	
	Page<ReportKeyMatchTopDto> unMatchTop(
			@Param("wechatId") Integer wechatId, 
			@Param("start") Date start, 
			@Param("end") Date end,
			@Param("top") Integer top);

}