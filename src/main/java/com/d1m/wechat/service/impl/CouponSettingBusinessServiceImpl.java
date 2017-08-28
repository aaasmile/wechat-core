package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.CouponSettingBusinessDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.CouponBusinessMapper;
import com.d1m.wechat.model.CouponBusiness;
import com.d1m.wechat.pamametermodel.CouponSettingModel;
import com.d1m.wechat.service.CouponSettingBusinessService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class CouponSettingBusinessServiceImpl extends
		BaseService<CouponBusiness> implements CouponSettingBusinessService {

	@Autowired
	private CouponBusinessMapper couponBusinessMapper;

	@Override
	public Mapper<CouponBusiness> getGenericMapper() {
		return couponBusinessMapper;
	}

	@Override
	public Page<CouponSettingBusinessDto> search(Integer wechatId,
			CouponSettingModel couponSettingModel, boolean queryCount)
			throws WechatException {
		if (couponSettingModel.pagable()) {
			PageHelper.startPage(couponSettingModel.getPageNum(),
					couponSettingModel.getPageSize(), queryCount);
		}
		return couponBusinessMapper.search(wechatId,
				couponSettingModel.getId(), couponSettingModel.getActivityId(),
				couponSettingModel.getType(), couponSettingModel.getSortName(),
				couponSettingModel.getSortDir());
	}

}
