package com.d1m.wechat.service;

import com.d1m.wechat.dto.CouponDto;
import com.d1m.wechat.dto.HolaCouponDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.Coupon;
import com.d1m.wechat.pamametermodel.CouponModel;
import com.github.pagehelper.Page;

public interface CouponService extends IService<Coupon> {

	Page<CouponDto> search(Integer wechatId, CouponModel couponModel,
			boolean queryCount) throws WechatException;

	Page<HolaCouponDto> searchHola(Integer wechatId, CouponModel couponModel,
			boolean queryCount) throws WechatException;

	Page<HolaCouponDto> searchCrm(Integer wechatId, CouponModel couponModel,
								   boolean queryCount) throws WechatException;

	Coupon getByCode(Integer wechatId, String code);

}
