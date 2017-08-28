package com.d1m.wechat.mapper;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.CouponMember;
import com.d1m.wechat.util.MyMapper;

public interface CouponMemberMapper extends MyMapper<CouponMember> {

	List<Map<String, Object>> getUnPushedCoupons(
			@Param("giftType") String gifyType);

	List<String> getPushedCoupons(@Param("giftType") String gifyType,
			@Param("start") Date start, @Param("end") Date end);

	List<Map<String, Object>> getUnPushedCouponsByCodes(
			@Param("list") List<String> list);

	Integer getWinCount(@Param("wechatId") Integer wechatId,
			@Param("couponSettingId") Integer couponSettingId,
			@Param("memberId") Integer memberId,
			@Param("startAt") Date startAt, @Param("endAt") Date endAt);

	int countMember(@Param("start") Date start, @Param("end") Date end,
			@Param("list") List<Integer> list);

	int countReceive(@Param("start") Date start, @Param("end") Date end,
			@Param("list") List<Integer> list);

	int countValidity(@Param("start") Date start, @Param("end") Date end,
			@Param("list") List<Integer> list);

	CouponMember getByOpenIdAndCoupon(@Param("openId") String openId,@Param("code") String code);

	Map<String,Object> getRelationIds(@Param("code") String code);
}
