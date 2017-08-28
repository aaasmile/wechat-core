package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.CouponDto;
import com.d1m.wechat.dto.HolaCouponDto;
import com.d1m.wechat.model.Coupon;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface CouponMapper extends MyMapper<Coupon> {

	Page<CouponDto> search(@Param("wechatId") Integer wechatId,
			@Param("activityId") Integer activityId,
			@Param("status") Byte status, @Param("except") Byte except,
			@Param("businessId") Integer businessId,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

	Page<HolaCouponDto> searchHola(@Param("wechatId") Integer wechatId,
			@Param("memberId") Integer memberId,
			@Param("couponSettingId") Integer couponSettingId,
			@Param("grnos") List<String> grnos,
			@Param("giftType") String giftType, @Param("status") Byte status,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

	Page<HolaCouponDto> searchCrm(@Param("wechatId") Integer wechatId,
								   @Param("grnos") List<String> grnos,
								   @Param("giftType") String giftType,
								   @Param("sortName") String sortName, @Param("sortDir") String sortDir);

	Coupon receiveOne(@Param("wechatId") Integer wechatId,
			@Param("couponSettingId") Integer couponSettingId);

}