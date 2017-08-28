package com.d1m.wechat.service;

import com.d1m.wechat.dto.ActivityDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.Activity;
import com.d1m.wechat.model.ActivityQrcode;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.ActivityModel;
import com.d1m.wechat.pamametermodel.ActivityQrcodeModel;
import com.d1m.wechat.pamametermodel.ReceiveCoupon;
import com.github.pagehelper.Page;

public interface ActivityService extends IService<Activity> {

	ActivityDto get(Integer id) throws WechatException;

	ActivityDto get(Integer wechatId, Integer id) throws WechatException;

	void delete(Integer wechatId, Integer id) throws WechatException;

	Page<ActivityDto> search(Integer wechatId, ActivityModel activityModel,
			boolean queryCount) throws WechatException;

	Activity create(Integer wechatId, User user, ActivityModel activityModel)
			throws WechatException;

	Activity update(Integer wechatId, User user, ActivityModel activityModel)
			throws WechatException;

	void receiveCoupon(Integer activityId, Integer couponSettingId,
			String openId);

	void share(ReceiveCoupon receiveCoupon);

	ActivityQrcode createActivityQrcode(Integer wechatId, User user,
			ActivityQrcodeModel activityQrcodeModel);

}
