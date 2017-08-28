package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.ActivityCouponSettingDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.ActivityCouponSetting;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.ActivityModel;
import com.github.pagehelper.Page;

public interface ActivityCouponSettingService extends
		IService<ActivityCouponSetting> {

	Page<ActivityCouponSettingDto> search(Integer wechatId,
			ActivityModel activityModel, boolean queryCount)
			throws WechatException;

	List<ActivityCouponSettingDto> create(Integer wechatId, User user,
			ActivityModel activityModel) throws WechatException;

	void delete(Integer wechatId, User user, Integer id) throws WechatException;

}
