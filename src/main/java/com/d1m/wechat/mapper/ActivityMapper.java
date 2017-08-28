package com.d1m.wechat.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.ActivityDto;
import com.d1m.wechat.dto.ActivityListDto;
import com.d1m.wechat.model.Activity;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface ActivityMapper extends MyMapper<Activity> {

	Page<ActivityDto> search(@Param("wechatId") Integer wechatId,
			@Param("query") String query,
			@Param("activityType") Byte activityType,
			@Param("activityStatus") Byte activityStatus,
			@Param("status") Byte status, @Param("sortName") String sortName,
			@Param("sortDir") String sortDir);

	ActivityDto get(@Param("wechatId") Integer wechatId, @Param("id") Integer id);

	ActivityDto getById(@Param("id") Integer id);

	int countActivity(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end);

	List<ActivityListDto> getActivityList(@Param("wechatId") Integer wechatId);

}
