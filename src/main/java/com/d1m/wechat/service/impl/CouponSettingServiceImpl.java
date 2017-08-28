package com.d1m.wechat.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.ActivityCouponSettingDto;
import com.d1m.wechat.dto.CouponDto;
import com.d1m.wechat.dto.CouponSettingDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.BusinessMapper;
import com.d1m.wechat.mapper.CouponBusinessMapper;
import com.d1m.wechat.mapper.CouponMapper;
import com.d1m.wechat.mapper.CouponMemberMapper;
import com.d1m.wechat.mapper.CouponSettingMapper;
import com.d1m.wechat.model.Business;
import com.d1m.wechat.model.Coupon;
import com.d1m.wechat.model.CouponBusiness;
import com.d1m.wechat.model.CouponMember;
import com.d1m.wechat.model.CouponSetting;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.ActivityType;
import com.d1m.wechat.pamametermodel.ActivityModel;
import com.d1m.wechat.pamametermodel.CouponSettingModel;
import com.d1m.wechat.service.ActivityCouponSettingService;
import com.d1m.wechat.service.CouponSettingService;
import com.d1m.wechat.util.CouponUtil;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.IllegalArgumentUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class CouponSettingServiceImpl extends BaseService<CouponSetting>
		implements CouponSettingService {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	CouponSettingMapper couponSettingMapper;

	@Autowired
	private BusinessMapper businessMapper;

	@Autowired
	private CouponBusinessMapper couponBusinessMapper;

	@Autowired
	private ActivityCouponSettingService activityCouponSettingService;

	@Autowired
	private CouponMapper couponMapper;

	@Autowired
	private CouponMemberMapper couponMemberMapper;

	@Override
	public Mapper<CouponSetting> getGenericMapper() {
		return couponSettingMapper;
	}

	@Override
	public void saveOrUpdate(CouponSetting couponSetting,
			List<String> setStoresList, List<String> useStoresList) {
		if (StringUtils.isNotBlank(couponSetting.getGrno())) {
			CouponSetting cs = couponSettingMapper.getByGrno(couponSetting
					.getGrno());
			if (cs != null) {
				couponSetting.setId(cs.getId());
				couponSetting.setModifyAt(new Date());
				updateNotNull(couponSetting);

				// 插入领用门店
				// 先删除关系
				CouponBusiness cb = new CouponBusiness();
				cb.setCouponSettingId(cs.getId());
				couponBusinessMapper.delete(cb);
				saveCouponBusiness(couponSetting, setStoresList, useStoresList);
			} else {
				couponSetting.setCreatedAt(new Date());
				save(couponSetting);
				// 插入核销门店
				saveCouponBusiness(couponSetting, setStoresList, useStoresList);
			}
		}
	}

	private void saveCouponBusiness(CouponSetting couponSetting,
			List<String> setStoresList, List<String> useStoresList) {
		Business record = null;
		CouponBusiness cb = new CouponBusiness();
		if (setStoresList != null) {
			for (String outletCode : setStoresList) {
				record = new Business();
				record.setBusinessCode(outletCode);
				record.setWechatId(couponSetting.getWechatId());
				Business ret = businessMapper.selectOne(record);
				if (ret != null) {
					cb = new CouponBusiness();
					cb.setBusinessId(ret.getId());
					cb.setCouponSettingId(couponSetting.getId());
					cb.setType((byte) 0);
					cb.setCreatedAt(new Date());
					cb.setWechatId(couponSetting.getWechatId());
					couponBusinessMapper.insert(cb);
				}
			}
		}
		if (useStoresList != null) {
			for (String outletCode : useStoresList) {
				record = new Business();
				record.setBusinessCode(outletCode);
				record.setWechatId(couponSetting.getWechatId());
				Business ret = businessMapper.selectOne(record);
				if (ret != null) {
					cb = new CouponBusiness();
					cb.setBusinessId(ret.getId());
					cb.setCouponSettingId(couponSetting.getId());
					cb.setType((byte) 1);
					cb.setCreatedAt(new Date());
					cb.setWechatId(couponSetting.getWechatId());
					couponBusinessMapper.insert(cb);
				}
			}
		}
	}

	@Override
	public Page<CouponSettingDto> search(Integer wechatId,
			CouponSettingModel couponSettingModel, boolean queryCount)
			throws WechatException {
		if (couponSettingModel.pagable()) {
			PageHelper.startPage(couponSettingModel.getPageNum(),
					couponSettingModel.getPageSize(), queryCount);
		}
		ActivityType at = ActivityType.getValueByValue(couponSettingModel
				.getActivityType());
		String channel = at != null ? at.getChannel() : null;
		return couponSettingMapper.search(
				wechatId,
				couponSettingModel.getQuery(),
				channel,
				DateUtil.parse(DateUtil.utc2DefaultLocal(
						couponSettingModel.getEndAt(), true)),
				couponSettingModel.getSortName(),
				couponSettingModel.getSortDir());
	}

	@Override
	public CouponSettingDto get(Integer wechatId, Integer id)
			throws WechatException {
		return couponSettingMapper.get(wechatId, id);
	}

	@Override
	public List<ActivityCouponSettingDto> update(Integer wechatId, User user,
			CouponSettingModel couponSettingModel) throws WechatException {
		// IllegalArgumentUtil.notBlank(couponSettingModel.getCouponBgcolor(),
		// Message.COUPON_SETTING_BG_COLOR_NOT_BLANK);
		// IllegalArgumentUtil.notBlank(couponSettingModel.getCouponPic(),
		// Message.COUPON_SETTING_PIC_NOT_BLANK);
		CouponSetting record = new CouponSetting();
		record.setWechatId(wechatId);
		record.setId(couponSettingModel.getId());
		record = couponSettingMapper.selectOne(record);
		IllegalArgumentUtil.notBlank(record, Message.COUPON_SETTING_NOT_EXIST);

		if (StringUtils.equals(record.getGiftType(), "10")) {
			IllegalArgumentUtil.notBlank(couponSettingModel.getCouponSum(),
					Message.COUPON_SETTING_NUM_NOT_BLANK);
			if (couponSettingModel.getCouponSum() < 0) {
				throw new WechatException(
						Message.COUPON_SETTING_NUM_MUST_GE_ZERO);
			}
		}

		record.setCouponBgcolor(couponSettingModel.getCouponBgcolor());
		record.setCouponTitleBgcolor(couponSettingModel.getCouponTitleBgcolor());
		record.setTimesOfJoin(couponSettingModel.getTimesOfJoin());
		record.setTimesOfWin(couponSettingModel.getTimesOfWin());
		record.setWinProbability(couponSettingModel.getWinProbability());
		record.setCouponDescription(couponSettingModel.getCouponDescription());
		record.setCouponPic(couponSettingModel.getCouponPic());
		record.setModifyAt(new Date());
		record.setModifyById(user.getId());
		record.setFetchType(couponSettingModel.getFetchType());
		record.setShortName(couponSettingModel.getShortName());
		record.setConditions(couponSettingModel.getConditions());
		record.setCouponSum(couponSettingModel.getCouponSum());
		couponSettingMapper.updateByPrimaryKey(record);

		ActivityModel activityModel = new ActivityModel();
		activityModel.setId(couponSettingModel.getActivityId());
		List<Integer> couponSettings = new ArrayList<Integer>();
		couponSettings.add(record.getId());
		activityModel.setCouponSettings(couponSettings);
		return activityCouponSettingService.create(wechatId, user,
				activityModel);
	}

	@Override
	public CouponSetting getByGrno(String grno) {
		return couponSettingMapper.getByGrno(grno);
	}

	@Override
	public synchronized CouponDto receiveOne(Integer wechatId,
			Integer memberId, Integer couponSettingId) {
		log.info("couponSettingId : {}", couponSettingId);
		CouponSetting couponSetting = new CouponSetting();
		couponSetting.setWechatId(wechatId);
		couponSetting.setId(couponSettingId);
		couponSetting = couponSettingMapper.selectOne(couponSetting);
		Coupon coupon = null;
		Date current = new Date();
		if (StringUtils.equals(couponSetting.getGiftType(), "10")) {
			Integer couponSum = couponSetting.getCouponSum();
			log.info("couponSum : {}", couponSum);
			Coupon record = new Coupon();
			record.setWechatId(wechatId);
			record.setCouponSettingId(couponSettingId);
			int receiveCount = couponMapper.selectCount(record);
			log.info("receiveCount : {}", receiveCount);
			if (receiveCount >= couponSum) {
				throw new WechatException(
						Message.COUPON_SETTING_AVALIBALE_COUPON_IS_EMPTY);
			}
			coupon = new Coupon();
			coupon.setCode(CouponUtil.generateCode(couponSetting.getGrno()));
			log.info("code : {}", coupon.getCode());
			coupon.setCouponSettingId(couponSetting.getId());
			coupon.setCreatedAt(current);
			coupon.setSource((byte) 0);
			coupon.setStatus((byte) 1);
			coupon.setWechatId(couponSetting.getWechatId());
			couponMapper.insert(coupon);
		} else if (StringUtils.equals(couponSetting.getGiftType(), "20")) {
			coupon = couponMapper.receiveOne(wechatId, couponSettingId);
			if (coupon == null) {
				throw new WechatException(
						Message.COUPON_SETTING_AVALIBALE_COUPON_IS_EMPTY);
			}
			coupon.setStatus((byte) 1);
			couponMapper.updateByPrimaryKey(coupon);
		} else {
			throw new WechatException(Message.SYSTEM_ERROR);
		}
		CouponMember couponMember = new CouponMember();
		couponMember.setCouponId(coupon.getId());
		couponMember.setCouponSettingId(couponSettingId);
		couponMember.setMemberId(memberId);
		couponMember.setPushStatus((byte) 0);
		couponMember.setReceiveAt(current);
		couponMember.setStatus((byte) 0);
		couponMember.setWechatId(wechatId);
		couponMemberMapper.insert(couponMember);

		CouponDto couponDto = new CouponDto();
		couponDto.setCouponMemberId(couponMember.getId());
		couponDto.setCode(coupon.getCode());
		return couponDto;
	}

}
