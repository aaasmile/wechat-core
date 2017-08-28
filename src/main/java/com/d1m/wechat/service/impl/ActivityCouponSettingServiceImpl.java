package com.d1m.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.ActivityCouponSettingDto;
import com.d1m.wechat.dto.ActivityDto;
import com.d1m.wechat.dto.CouponSettingDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.ActivityCouponSettingMapper;
import com.d1m.wechat.mapper.ActivityMapper;
import com.d1m.wechat.mapper.CouponSettingMapper;
import com.d1m.wechat.model.ActivityCouponSetting;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.ActivityModel;
import com.d1m.wechat.service.ActivityCouponSettingService;
import com.d1m.wechat.util.IllegalArgumentUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ActivityCouponSettingServiceImpl extends
		BaseService<ActivityCouponSetting> implements
		ActivityCouponSettingService {

	@Autowired
	private ActivityMapper activityMapper;

	@Autowired
	private CouponSettingMapper couponSettingMapper;

	@Autowired
	private ActivityCouponSettingMapper activityCouponSettingMapper;

	@Override
	public Mapper<ActivityCouponSetting> getGenericMapper() {
		return activityCouponSettingMapper;
	}

	@Override
	public Page<ActivityCouponSettingDto> search(Integer wechatId,
			ActivityModel activityModel, boolean queryCount)
			throws WechatException {
		if (activityModel.pagable()) {
			PageHelper.startPage(activityModel.getPageNum(),
					activityModel.getPageSize(), queryCount);
		}
		return activityCouponSettingMapper.search(wechatId,
				activityModel.getId(), activityModel.getSortName(),
				activityModel.getSortDir());
	}

	@Override
	public List<ActivityCouponSettingDto> create(Integer wechatId, User user,
			ActivityModel activityModel) throws WechatException {
		ActivityDto activity = activityMapper.get(wechatId,
				activityModel.getId());
		IllegalArgumentUtil.notBlank(activity, null);
		List<Integer> couponSettings = activityModel.getCouponSettings();
		if (couponSettings == null || couponSettings.isEmpty()) {
			throw new WechatException(Message.COUPON_SETTING_NOT_BLANK);
		}
		List<ActivityCouponSetting> activityCouponSettings = new ArrayList<ActivityCouponSetting>();
		ActivityCouponSetting activityCouponSetting = null;
		CouponSettingDto couponSetting = null;
		List<ActivityCouponSettingDto> dtos = new ArrayList<ActivityCouponSettingDto>();
		for (Integer couponSettingId : couponSettings) {
			couponSetting = couponSettingMapper.get(wechatId, couponSettingId);
			IllegalArgumentUtil.notBlank(couponSetting,
					Message.COUPON_SETTING_NOT_EXIST);
			activityCouponSetting = new ActivityCouponSetting();
			activityCouponSetting.setActivityId(activityModel.getId());
			activityCouponSetting.setCouponSettingId(couponSettingId);
			activityCouponSetting.setWechatId(wechatId);
			ActivityCouponSetting exist = activityCouponSettingMapper
					.selectOne(activityCouponSetting);
			if (exist != null) {
				dtos.add(activityCouponSettingMapper.get(wechatId,
						exist.getId()));
			} else {
				activityCouponSettings.add(activityCouponSetting);
			}
		}
		if (!activityCouponSettings.isEmpty()) {
			activityCouponSettingMapper.insertList(activityCouponSettings);
			for (ActivityCouponSetting acs : activityCouponSettings) {
				dtos.add(activityCouponSettingMapper.get(wechatId, acs.getId()));
			}
		}
		return dtos;
	}

	@Override
	public void delete(Integer wechatId, User user, Integer id)
			throws WechatException {
		ActivityCouponSetting activityCouponSetting = new ActivityCouponSetting();
		activityCouponSetting.setId(id);
		activityCouponSetting.setWechatId(wechatId);
		if (activityCouponSettingMapper.selectOne(activityCouponSetting) == null) {
			throw new WechatException(Message.COUPON_SETTING_NOT_EXIST);
		}
		activityCouponSettingMapper.delete(activityCouponSetting);
	}

}
