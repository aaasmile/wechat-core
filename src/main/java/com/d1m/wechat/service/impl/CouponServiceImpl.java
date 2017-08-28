package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.CouponDto;
import com.d1m.wechat.dto.HolaCouponDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.CouponMapper;
import com.d1m.wechat.model.Coupon;
import com.d1m.wechat.pamametermodel.CouponModel;
import com.d1m.wechat.service.CouponService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class CouponServiceImpl extends BaseService<Coupon> implements
		CouponService {

	@Autowired
	CouponMapper couponMapper;

	@Override
	public Mapper<Coupon> getGenericMapper() {
		return couponMapper;
	}

	@Override
	public Page<CouponDto> search(Integer wechatId, CouponModel couponModel,
			boolean queryCount) throws WechatException {
		if (couponModel.pagable()) {
			PageHelper.startPage(couponModel.getPageNum(),
					couponModel.getPageSize(), queryCount);
		}
		return couponMapper.search(wechatId, couponModel.getActivityId(),
				couponModel.getStatus(), couponModel.getExcept(),
				couponModel.getBusinessId(), couponModel.getSortName(),
				couponModel.getSortDir());
	}

	@Override
	public Page<HolaCouponDto> searchHola(Integer wechatId,
			CouponModel couponModel, boolean queryCount) throws WechatException {
		if (couponModel.pagable()) {
			PageHelper.startPage(couponModel.getPageNum(),
					couponModel.getPageSize(), queryCount);
		}
		return couponMapper.searchHola(wechatId, couponModel.getMemberId(),
				couponModel.getCouponSettingId(), couponModel.getGrnos(),
				couponModel.getGiftType(), couponModel.getStatus(),
				couponModel.getSortName(), couponModel.getSortDir());
	}

	@Override
	public Page<HolaCouponDto> searchCrm(Integer wechatId, CouponModel couponModel, boolean queryCount) throws WechatException {
		if (couponModel.pagable()) {
			PageHelper.startPage(couponModel.getPageNum(),
					couponModel.getPageSize(), queryCount);
		}
		return couponMapper.searchCrm(wechatId, couponModel.getGrnos(),
				couponModel.getGiftType(), couponModel.getSortName(), couponModel.getSortDir());
	}

	@Override
	public Coupon getByCode(Integer wechatId, String code) {
		Coupon coupon = new Coupon();
		coupon.setCode(code);
		coupon.setWechatId(wechatId);
		return couponMapper.selectOne(coupon);
	}

}
