package com.d1m.wechat.service.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.CouponMemberMapper;
import com.d1m.wechat.model.CouponMember;
import com.d1m.wechat.service.CouponMemberService;

@Service
public class CouponMemberServiceImpl extends BaseService<CouponMember>
		implements CouponMemberService {

	@Autowired
	CouponMemberMapper couponMemberMapper;

	@Override
	public Mapper<CouponMember> getGenericMapper() {
		return couponMemberMapper;
	}

	@Override
	public List<Map<String, Object>> getUnPushedCoupons(String giftType) {
		return couponMemberMapper.getUnPushedCoupons(giftType);
	}

	@Override
	public List<Map<String, Object>> getUnPushedCouponsByCodes(List<String> list) {
		return couponMemberMapper.getUnPushedCouponsByCodes(list);
	}

	@Override
	public List<String> getPushedCoupons(String gifyType, Date start, Date end) {
		return couponMemberMapper.getPushedCoupons(gifyType, start, end);
	}

	@Override
	public CouponMember getByCouponId(Integer couponId) {
		CouponMember cm = new CouponMember();
		cm.setCouponId(couponId);
		return couponMemberMapper.selectOne(cm);
	}

	@Override
	public Integer getWinCount(Integer wechatId, Integer couponSettingId,
			Integer memberId, Date startAt, Date endAt) {
		return couponMemberMapper.getWinCount(wechatId, couponSettingId,
				memberId, startAt, endAt);
	}

	@Override
	public CouponMember getByOpenIdAndCoupon(String opendId, String code) {
		return couponMemberMapper.getByOpenIdAndCoupon(opendId, code);
	}

	@Override
	public Map<String, Object> getRelationIds(String code) {
		return couponMemberMapper.getRelationIds(code);
	}
}
