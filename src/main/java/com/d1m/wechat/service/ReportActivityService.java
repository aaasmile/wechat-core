package com.d1m.wechat.service;

import java.util.Date;
import java.util.List;

import com.d1m.wechat.dto.ActivityListDto;
import com.d1m.wechat.dto.ReportActivityDto;
import com.d1m.wechat.model.Activity;

public interface ReportActivityService extends IService<Activity>{

	ReportActivityDto activityReport(Integer wechatId, Date startDate, Date endDate,
			Integer activityId, Integer couponSettingId);

	List<ActivityListDto> activityList(Integer wechatId);

}
