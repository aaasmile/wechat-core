package com.d1m.wechat.service;

import java.util.Date;
import java.util.List;
import java.util.Map;

import com.d1m.wechat.model.CouponMember;

public interface CouponMemberService extends IService<CouponMember> {
	List<Map<String, Object>> getUnPushedCoupons(String gifyType);

	CouponMember getByCouponId(Integer couponId);

	CouponMember getByOpenIdAndCoupon(String openId,String code);

	List<String> getPushedCoupons(String gifyType, Date start, Date end);

	List<Map<String, Object>> getUnPushedCouponsByCodes(List<String> list);

	Integer getWinCount(Integer wechatId, Integer couponSettingId,
			Integer memberId, Date startAt, Date endAt);

	Map<String, Object> getRelationIds(String code);
}
