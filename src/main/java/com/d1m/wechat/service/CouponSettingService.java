package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.ActivityCouponSettingDto;
import com.d1m.wechat.dto.CouponDto;
import com.d1m.wechat.dto.CouponSettingDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.CouponSetting;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.CouponSettingModel;
import com.github.pagehelper.Page;

public interface CouponSettingService extends IService<CouponSetting> {

	void saveOrUpdate(CouponSetting couponSetting, List<String> setStoresList,
			List<String> useStoresList);

	Page<CouponSettingDto> search(Integer wechatId,
			CouponSettingModel couponSettingModel, boolean queryCount)
			throws WechatException;

	CouponSettingDto get(Integer wechatId, Integer id) throws WechatException;

	List<ActivityCouponSettingDto> update(Integer wechatId, User user,
			CouponSettingModel couponSettingModel) throws WechatException;

	CouponSetting getByGrno(String grno);

	CouponDto receiveOne(Integer wechatId, Integer memberId,
			Integer couponSettingId);

}
