package com.d1m.wechat.service;

import com.d1m.wechat.dto.CouponSettingBusinessDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.CouponBusiness;
import com.d1m.wechat.pamametermodel.CouponSettingModel;
import com.github.pagehelper.Page;

public interface CouponSettingBusinessService extends IService<CouponBusiness> {

	Page<CouponSettingBusinessDto> search(Integer wechatId,
			CouponSettingModel couponSettingModel, boolean queryCount)
			throws WechatException;

}
