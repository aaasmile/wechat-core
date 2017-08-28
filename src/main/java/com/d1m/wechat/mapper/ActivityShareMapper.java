package com.d1m.wechat.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.PieBaseDto;
import com.d1m.wechat.dto.ReportActivityItemDto;
import com.d1m.wechat.model.ActivityShare;
import com.d1m.wechat.util.MyMapper;

public interface ActivityShareMapper extends MyMapper<ActivityShare> {

	List<ReportActivityItemDto> activityShare(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end, @Param("activityId") Integer activityId);

	List<PieBaseDto> pieActivityShareReport(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end, @Param("activityId") Integer activityId);
	
}
