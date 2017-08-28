package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.ActivityCouponSettingDto;
import com.d1m.wechat.model.ActivityCouponSetting;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface ActivityCouponSettingMapper extends
		MyMapper<ActivityCouponSetting> {

	ActivityCouponSettingDto get(@Param("wechatId") Integer wechatId,
			@Param("id") Integer id);

	Page<ActivityCouponSettingDto> search(@Param("wechatId") Integer wechatId,
			@Param("activityId") Integer activityId,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

	List<Integer> getCouponSettingList(@Param("wechatId") Integer wechatId, 
			@Param("id") Integer id);

}
