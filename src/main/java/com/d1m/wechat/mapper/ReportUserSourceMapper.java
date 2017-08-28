package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.ReportUserSource;
import com.d1m.wechat.util.MyMapper;

public interface ReportUserSourceMapper extends MyMapper<ReportUserSource> {
	ReportUserSource getRecordByDate(@Param("wechatId") Integer wechatId,
			@Param("date") String date);
}