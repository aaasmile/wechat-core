package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.dto.ActivityDto;
import com.d1m.wechat.dto.CouponDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.ActivityCouponSettingMapper;
import com.d1m.wechat.mapper.ActivityMapper;
import com.d1m.wechat.mapper.ActivityQrcodeMapper;
import com.d1m.wechat.mapper.ActivityShareMapper;
import com.d1m.wechat.mapper.CouponSettingMapper;
import com.d1m.wechat.model.Activity;
import com.d1m.wechat.model.ActivityCouponSetting;
import com.d1m.wechat.model.ActivityQrcode;
import com.d1m.wechat.model.ActivityShare;
import com.d1m.wechat.model.CouponMember;
import com.d1m.wechat.model.CouponSetting;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.OauthUrl;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.ActivityQrcodeStatus;
import com.d1m.wechat.model.enums.ActivityShareType;
import com.d1m.wechat.model.enums.ActivityStatus;
import com.d1m.wechat.model.enums.ActivityType;
import com.d1m.wechat.oauth.impl.ActivityIOauthImpl;
import com.d1m.wechat.pamametermodel.ActivityModel;
import com.d1m.wechat.pamametermodel.ActivityQrcodeModel;
import com.d1m.wechat.pamametermodel.ReceiveCoupon;
import com.d1m.wechat.service.ActivityService;
import com.d1m.wechat.service.ConfigService;
import com.d1m.wechat.service.CouponMemberService;
import com.d1m.wechat.service.CouponService;
import com.d1m.wechat.service.CouponSettingService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.OauthUrlService;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.FileUploadConfigUtil;
import com.d1m.wechat.util.FileUtils;
import com.d1m.wechat.util.IllegalArgumentUtil;
import com.d1m.wechat.util.MD5;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.util.QrcodeUtils;
import com.d1m.wechat.util.Rand;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ActivityServiceImpl extends BaseService<Activity> implements
		ActivityService {

	private static Logger log = LoggerFactory
			.getLogger(ActivityServiceImpl.class);

	@Autowired
	ActivityMapper activityMapper;

	@Autowired
	private ActivityQrcodeMapper activityQrcodeMapper;

	@Autowired
	private WechatService wechatService;

	@Autowired
	private OauthUrlService oauthUrlService;

	@Autowired
	private CouponSettingMapper couponSettingMapper;

	@Autowired
	private CouponSettingService couponSettingService;

	@Autowired
	private ActivityCouponSettingMapper activityCouponSettingMapper;

	@Autowired
	private MemberService memberService;

	@Autowired
	private CouponService couponService;

	@Autowired
	private ConfigService configService;

	@Autowired
	private CouponMemberService couponMemberService;

	@Autowired
	private ActivityShareMapper activityShareMapper;

	@Override
	public Mapper<Activity> getGenericMapper() {
		return activityMapper;
	}

	@Override
	public ActivityDto get(Integer wechatId, Integer id) throws WechatException {
		return activityMapper.get(wechatId, id);
	}

	@Override
	public ActivityDto get(Integer id) throws WechatException {
		return activityMapper.getById(id);
	}

	@Override
	public void delete(Integer wechatId, Integer id) throws WechatException {
		Activity record = new Activity();
		record.setWechatId(wechatId);
		record.setId(id);
		record = activityMapper.selectOne(record);
		IllegalArgumentUtil.notBlank(record, Message.ACTIVITY_NOT_EXIST);
		if (record.getStartDate().compareTo(new Date()) <= 0) {
			throw new WechatException(Message.ACTIVITY_IS_START);
		}
		record.setStatus(ActivityStatus.DELETED.getValue());
		activityMapper.updateByPrimaryKey(record);

		ActivityCouponSetting acs = new ActivityCouponSetting();
		acs.setActivityId(id);
		acs.setWechatId(wechatId);
		List<ActivityCouponSetting> list = activityCouponSettingMapper
				.select(acs);
		CouponSetting couponSetting = null;
		for (ActivityCouponSetting activityCouponSetting : list) {
			CouponSetting cs = new CouponSetting();
			cs.setWechatId(wechatId);
			cs.setId(activityCouponSetting.getCouponSettingId());
			couponSetting = couponSettingMapper.selectOne(cs);
			couponSetting.setCouponBgcolor(null);
			couponSetting.setCouponTitleBgcolor(null);
			couponSetting.setTimesOfJoin(null);
			couponSetting.setTimesOfWin(null);
			couponSetting.setWinProbability(null);
			couponSetting.setCouponDescription(null);
			couponSetting.setCouponPic(null);
			couponSetting.setModifyAt(null);
			couponSetting.setModifyById(null);
			couponSetting.setFetchType(null);
			couponSettingService.updateAll(couponSetting);
			activityCouponSettingMapper.delete(activityCouponSetting);
		}
	}

	@Override
	public Page<ActivityDto> search(Integer wechatId,
			ActivityModel activityModel, boolean queryCount)
			throws WechatException {
		if (activityModel.pagable()) {
			PageHelper.startPage(activityModel.getPageNum(),
					activityModel.getPageSize(), queryCount);
		}
		return activityMapper.search(wechatId, activityModel.getQuery(),
				activityModel.getActivityType(),
				activityModel.getActivityStatus(), null,
				activityModel.getSortName(), activityModel.getSortDir());
	}

	private Date getDate(String str) {
		if (str.contains("T")) {
			return DateUtil.parse(DateUtil.utc2DefaultLocal(str));
		} else {
			return DateUtil.parse(str);
		}
	}

	@Override
	public Activity create(Integer wechatId, User user,
			ActivityModel activityModel) throws WechatException {
		IllegalArgumentUtil.notBlank(activityModel.getName(),
				Message.ACTIVITY_NAME_NOT_BLANK);
		IllegalArgumentUtil.notBlank(activityModel.getStartDate(),
				Message.ACTIVITY_START_DATE_NOT_BLANK);
		IllegalArgumentUtil.notBlank(activityModel.getEndDate(),
				Message.ACTIVITY_END_DATE_NOT_BLANK);
		Date startDate = DateUtil.getDateBegin(getDate(activityModel
				.getStartDate()));
		Date endDate = DateUtil.getDateEnd(getDate(activityModel.getEndDate()));
		if (startDate == null) {
			throw new WechatException(Message.ACTIVITY_START_DATE_NOT_BLANK);
		}
		if (endDate == null) {
			throw new WechatException(Message.ACTIVITY_END_DATE_NOT_BLANK);
		}
		if (startDate.compareTo(endDate) > 0) {
			throw new WechatException(
					Message.ACTIVITY_START_DATE_MUST_BE_LT_END_DATE);
		}

		ActivityType at = ActivityType.getValueByValue(activityModel
				.getActivityType());
		IllegalArgumentUtil.notBlank(at, Message.ACTIVITY_TYPE_NOT_EXIST);

		Activity record = new Activity();
		record.setWechatId(wechatId);
		record.setName(activityModel.getName());
		int count = activityMapper.selectCount(record);
		if (count > 0) {
			throw new WechatException(Message.ACTIVITY_NAME_EXIST);
		}
		record.setDescription(activityModel.getDescription());
		record.setStartDate(startDate);
		record.setEndDate(endDate);
		Date current = new Date();
		record.setCreatedAt(current);
		record.setCreatorId(user.getId());
		record.setStatus(ActivityStatus.INUSED.getValue());
		// record.setCode(null);
		record.setType(at.getValue());
		activityMapper.insert(record);

		createActivityQrcode(wechatId, user, record, null);

		return record;
	}

	private ActivityQrcode createActivityQrcode(Integer wechatId, User user,
			Activity record, String channel) {
		Date current = new Date();
		String format = DateUtil.yyyyMMddHHmmss.format(current);
		String newFileName = FileUtils.generateNewFileName(
				MD5.MD5Encode(Rand.getRandom(32)), Constants.JPG.toLowerCase());

		File root = FileUtils.getUploadPathRoot(wechatId, Constants.IMAGE
				+ File.separator + Constants.ACTIVITY);
		File dir = new File(root, format.substring(0, 6));
		if (!dir.exists()) {
			dir.mkdirs();
		}
		String absolutePath = dir.getAbsoluteFile().getAbsolutePath();
		log.info("dir path : " + absolutePath);

		FileUploadConfigUtil config = FileUploadConfigUtil.getInstance();
		String accessPath = config.getValue(wechatId, "upload_url_base") + File.separator
				+ Constants.IMAGE + File.separator + Constants.ACTIVITY
				+ File.separator + dir.getName() + File.separator + newFileName;
		log.info("accessPath : {}", accessPath);

		OauthUrl ou = new OauthUrl();
		ou.setCreatedAt(current);
		JSONObject json = new JSONObject();
		json.put("activity_id", record.getId());
		ou.setParams(json.toString());
		ou.setProcessClass(ActivityIOauthImpl.class.getName());
		ou.setScope("snsapi_base");
		ou.setShortUrl(Rand.randomString(12));
		ou.setStatus((byte) 1);
		ou.setWechatId(wechatId);
		oauthUrlService.save(ou);

		String activityBackendUrl = String.format(
				config.getValue(null, "activity_backend_url"), ou.getShortUrl());
		log.info("activityBackendUrl : {}", activityBackendUrl);
		try {
			QrcodeUtils.encode(activityBackendUrl, dir.getAbsolutePath(),
					newFileName);
		} catch (Exception e) {
			log.error(e.getLocalizedMessage());
			throw new WechatException(Message.ACTIVITY_QRCODE_CREATE_ERROR);
		}

		ActivityQrcode activityQrcode = new ActivityQrcode();
		activityQrcode.setActivityId(record.getId());
		activityQrcode.setCreatedAt(record.getCreatedAt());
		activityQrcode.setCreatorId(user.getId());
		activityQrcode.setQrcodeImgUrl(accessPath);
		activityQrcode.setWechatId(wechatId);
		activityQrcode.setActyUrl(activityBackendUrl);
		activityQrcode.setStatus(ActivityQrcodeStatus.INUSED.getValue());
		activityQrcode.setChannel(channel);
		activityQrcodeMapper.insert(activityQrcode);
		return activityQrcode;
	}

	@Override
	public Activity update(Integer wechatId, User user,
			ActivityModel activityModel) throws WechatException {
		IllegalArgumentUtil.notBlank(activityModel.getName(), null);
		IllegalArgumentUtil.notBlank(activityModel.getStartDate(), null);
		IllegalArgumentUtil.notBlank(activityModel.getEndDate(), null);
		Date startDate = DateUtil.getDateBegin(getDate(activityModel
				.getStartDate()));
		Date endDate = DateUtil.getDateEnd(getDate(activityModel.getEndDate()));
		if (startDate == null) {
			throw new WechatException(Message.ACTIVITY_START_DATE_NOT_BLANK);
		}
		if (endDate == null) {
			throw new WechatException(Message.ACTIVITY_END_DATE_NOT_BLANK);
		}
		if (startDate.compareTo(endDate) > 0) {
			throw new WechatException(
					Message.ACTIVITY_START_DATE_MUST_BE_LT_END_DATE);
		}

		Activity record = new Activity();
		record.setWechatId(wechatId);
		record.setId(activityModel.getId());
		record = activityMapper.selectOne(record);
		IllegalArgumentUtil.notBlank(record, Message.ACTIVITY_NOT_EXIST);

		Activity nameExistRecord = new Activity();
		nameExistRecord.setWechatId(wechatId);
		nameExistRecord.setName(activityModel.getName());
		nameExistRecord = activityMapper.selectOne(nameExistRecord);
		if (nameExistRecord != null
				&& !nameExistRecord.getId().equals(activityModel.getId())) {
			throw new WechatException(Message.ACTIVITY_NAME_EXIST);
		}
		record.setName(activityModel.getName());
		record.setStartDate(startDate);
		record.setEndDate(endDate);
		record.setDescription(activityModel.getDescription());
		record.setSharePic(activityModel.getSharePic());
		record.setShareTitle(activityModel.getShareTitle());
		record.setShareDescription(activityModel.getShareDescription());
		record.setActyH5(activityModel.getActyH5());
		record.setActyOfflineH5(activityModel.getActyOfflineH5());
		record.setModifyAt(new Date());
		record.setModifyById(user.getId());
		activityMapper.updateByPrimaryKey(record);
		return record;
	}

	@Override
	public synchronized void receiveCoupon(Integer activityId, Integer couponSettingId,
			String openId) {
		log.info("activityId : {}", activityId);
		log.info("couponSettingId : {}", couponSettingId);
		log.info("openId : {}", openId);
		IllegalArgumentUtil.notBlank(activityId, Message.ACTIVITY_ID_NOT_BLANK);
		IllegalArgumentUtil.notBlank(couponSettingId,
				Message.COUPON_SETTING_ID_NOT_BLANK);
		IllegalArgumentUtil.notBlank(openId, Message.MEMBER_ID_NOT_EMPTY);
		Activity activity = activityMapper.selectByPrimaryKey(activityId);
		IllegalArgumentUtil.notBlank(activity, Message.ACTIVITY_NOT_EXIST);
		Date current = new Date();
		if (current.compareTo(activity.getStartDate()) < 0) {
			throw new WechatException(Message.ACTIVITY_IS_NOT_START);
		}
		if (current.compareTo(activity.getEndDate()) > 0) {
			throw new WechatException(Message.ACTIVITY_IS_END);
		}

		Integer wechatId = activity.getWechatId();
		CouponSetting couponSetting = getCouponSetting(couponSettingId,
				wechatId);
		checkCouponSettingInActivity(activityId, couponSettingId, wechatId);
		if (current.compareTo(couponSetting.getStartDate()) < 0) {
			throw new WechatException(
					Message.COUPON_SETTING_RECEIVE_TIME_IS_NOT_START);
		}
		if (current.compareTo(couponSetting.getEndDate()) > 0) {
			throw new WechatException(
					Message.COUPON_SETTING_RECEIVE_TIME_IS_END);
		}
		Member member = memberService.getMemberByOpenId(wechatId, openId);
		if (member == null) {
			member = new Member();
			member.setOpenId(openId);
			member.setActivity((byte) 5);
			member.setBatchsendMonth(0);
			member.setWechatId(wechatId);
			member.setCreatedAt(current);
			member.setIsSubscribe(false);
			memberService.save(member);
		}else{
			//老用户处理
			if(member.getMemberGroupId()!=null&&member.getMemberGroupId().intValue()==0){
				if(activityId==6&&couponSettingId==11){
					throw new WechatException(
							Message.COUPON_SETTING_RECEIVE_NO_PERMISSION);
				}
			}
		}
		checkJoinCount(couponSetting, member);
		checkWinCount(couponSetting, member);
		checkWinProbability(couponSetting);
		CouponDto couponDto = couponSettingService.receiveOne(wechatId,
				member.getId(), couponSettingId);
		String isRealTime = configService.getConfigValue(wechatId, "HOLA",
				"PUSH_COUPON_REALTIME");
		if (isRealTime != null
				&& !StringUtils.equalsIgnoreCase("false", isRealTime)) {
			List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("status", "1");
			map.put("openId", openId);
			map.put("skuSeq", couponDto.getCode());
			map.put("giftSeq", couponDto.getCode());
			map.put("grno", couponSetting.getGrno());
			map.put("cardId", null);
			list.add(map);

//			JSONArray retList = null;
//			if (StringUtils.equals(couponSetting.getGiftType(), "10")) {
//				retList = HolaApiUtils.receiveGift(list);
//			} else if (StringUtils.equals(couponSetting.getGiftType(), "20")) {
//				retList = HolaApiUtils.receiveCoupon(list);
//			}
//			if (retList != null) {
//				for (int i = 0; i < retList.size(); i++) {
//					JSONObject obj = (JSONObject) retList.get(i);
//					String resultCode = obj.getString("resultCode");
//					if ("200".equals(resultCode)) {
//						CouponMember couponMember = couponMemberService
//								.selectByKey(couponDto.getCouponMemberId());
//						couponMember.setPushAt(new Date());
//						couponMember.setPushStatus((byte) 1);
//						couponMemberService.updateNotNull(couponMember);
//					}
//				}
//			}
		}
	}

	private void checkCouponSettingInActivity(Integer activityId,
			Integer couponSettingId, Integer wechatId) {
		ActivityCouponSetting acs = new ActivityCouponSetting();
		acs.setActivityId(activityId);
		acs.setCouponSettingId(couponSettingId);
		acs.setWechatId(wechatId);
		IllegalArgumentUtil.notBlank(acs,
				Message.ACTIVITY_HAS_NO_THIS_COUPON_SETTING);
	}

	private CouponSetting getCouponSetting(Integer couponSettingId,
			Integer wechatId) {
		CouponSetting couponSetting = new CouponSetting();
		couponSetting.setWechatId(wechatId);
		couponSetting.setId(couponSettingId);
		couponSetting = couponSettingMapper.selectOne(couponSetting);
		IllegalArgumentUtil.notBlank(couponSetting,
				Message.COUPON_SETTING_NOT_EXIST);
		return couponSetting;
	}

	private void checkWinProbability(CouponSetting couponSetting) {
		Integer winProbability = couponSetting.getWinProbability();
		if (winProbability != null) {
			if (winProbability != 0 && winProbability != 100) {
				int randomInt = Rand.randomInt(1, 100);
				if (randomInt > winProbability) {
					throw new WechatException(Message.COUPON_SETTING_NOT_WIN);
				}
			}
		}
	}

	private void checkWinCount(CouponSetting couponSetting, Member member) {
		Integer timesOfWin = couponSetting.getTimesOfWin();
		if (timesOfWin != null) {
			Date current = new Date();
			Date startAt = DateUtil.getDateBegin(current);
			Date endAt = DateUtil.getDateEnd(current);
			Integer winCount = couponMemberService.getWinCount(
					couponSetting.getWechatId(), couponSetting.getId(),
					member.getId(), startAt, endAt);
			if (winCount >= timesOfWin) {
				throw new WechatException(
						Message.COUPON_SETTING_TIMES_OF_WIN_OVER_MAX);
			}
		}
	}

	private void checkJoinCount(CouponSetting couponSetting, Member member) {
		Integer timesOfJoin = couponSetting.getTimesOfJoin();
		if (timesOfJoin != null) {
			Integer joinCount = couponMemberService.getWinCount(
					couponSetting.getWechatId(), couponSetting.getId(),
					member.getId(), null, null);
			if (joinCount >= timesOfJoin) {
				throw new WechatException(
						Message.COUPON_SETTING_TIMES_OF_JOIN_OVER_MAX);
			}
		}
	}

	@Override
	public void share(ReceiveCoupon receiveCoupon) {
		IllegalArgumentUtil.notBlank(receiveCoupon.getActivityId(),
				Message.ACTIVITY_ID_NOT_BLANK);
		IllegalArgumentUtil.notBlank(receiveCoupon.getOpenId(),
				Message.MEMBER_ID_NOT_EMPTY);
		IllegalArgumentUtil.notBlank(receiveCoupon.getType(),
				Message.ACTIVITY_SHARE_TYPE_NOT_EMPTY);
		Activity activity = activityMapper.selectByPrimaryKey(receiveCoupon
				.getActivityId());
		IllegalArgumentUtil.notBlank(activity, Message.ACTIVITY_NOT_EXIST);
		Member member = memberService.getMemberByOpenId(activity.getWechatId(),
				receiveCoupon.getOpenId());
		IllegalArgumentUtil.notBlank(member, Message.MEMBER_NOT_EXIST);
		ActivityShareType activityShareType = ActivityShareType
				.getValueByValue(receiveCoupon.getType());
		IllegalArgumentUtil.notBlank(activityShareType,
				Message.ACTIVITY_SHARE_TYPE_NOT_EXIST);
		ActivityShare activityShare = new ActivityShare();
		activityShare.setWechatId(activity.getWechatId());
		activityShare.setMemberId(member.getId());
		activityShare.setActivityId(activity.getId());
		activityShare.setType(activityShareType.getValue());
		activityShare.setShareAt(new Date());
		activityShareMapper.insert(activityShare);
	}

	@Override
	public ActivityQrcode createActivityQrcode(Integer wechatId, User user,
			ActivityQrcodeModel activityQrcodeModel) {
		Integer activityId = activityQrcodeModel.getActivityId();
		notBlank(activityId, Message.ACTIVITY_ID_NOT_BLANK);
		String channel = activityQrcodeModel.getChannel();
		notBlank(channel, Message.ACTIVITY_CHANNEL_NOT_BLANK);
		Activity activity = new Activity();
		activity.setWechatId(wechatId);
		activity.setId(activityId);
		activity = activityMapper.selectOne(activity);
		notBlank(activity, Message.ACTIVITY_NOT_EXIST);
		ActivityQrcode record = new ActivityQrcode();
		record.setWechatId(wechatId);
		record.setChannel(channel);
		record.setActivityId(activityId);
		int count = activityQrcodeMapper.selectCount(record);
		if (count > 0) {
			throw new WechatException(Message.ACTIVITY_CHANNEL_EXIST);
		}
		return createActivityQrcode(wechatId, user, activity, channel);
	}
}
