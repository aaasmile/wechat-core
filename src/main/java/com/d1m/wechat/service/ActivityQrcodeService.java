package com.d1m.wechat.service;

import com.d1m.wechat.dto.ActivityQrcodeDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.ActivityQrcode;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.ActivityModel;
import com.github.pagehelper.Page;

public interface ActivityQrcodeService extends IService<ActivityQrcode> {

	ActivityQrcode get(Integer wechatId, Integer id);

	Page<ActivityQrcodeDto> search(Integer wechatId,
			ActivityModel activityModel, boolean queryCount)
			throws WechatException;

	void delete(Integer wechatId, User user, Integer id) throws WechatException;

}
