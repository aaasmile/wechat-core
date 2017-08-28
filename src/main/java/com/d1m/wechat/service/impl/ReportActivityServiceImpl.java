package com.d1m.wechat.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.ActivityListDto;
import com.d1m.wechat.dto.PieBaseDto;
import com.d1m.wechat.dto.ReportActivityDto;
import com.d1m.wechat.dto.ReportActivityItemDto;
import com.d1m.wechat.mapper.ActivityCouponSettingMapper;
import com.d1m.wechat.mapper.ActivityMapper;
import com.d1m.wechat.mapper.ActivityShareMapper;
import com.d1m.wechat.mapper.CouponMemberMapper;
import com.d1m.wechat.mapper.CouponSettingMapper;
import com.d1m.wechat.model.Activity;
import com.d1m.wechat.service.ReportActivityService;
import com.d1m.wechat.util.DateUtil;

@Service
public class ReportActivityServiceImpl extends BaseService<Activity> implements ReportActivityService{
	
	private static Logger log = LoggerFactory.getLogger(ReportActivityServiceImpl.class);
	
	@Autowired
	ActivityMapper activityMapper;
	
	@Autowired
	CouponSettingMapper couponSettingMapper;
	
	@Autowired
	CouponMemberMapper couponMemberMapper;
	
	@Autowired
	ActivityCouponSettingMapper activityCouponSettingMapper;
	
	@Autowired
	ActivityShareMapper activityShareMapper;
	
	@Override
	public Mapper<Activity> getGenericMapper() {
		// TODO Auto-generated method stub
		return activityMapper;
	}
	
	private List<ReportActivityItemDto> findDates4Message(Date begin, Date end) {
		List<ReportActivityItemDto> list = new ArrayList<ReportActivityItemDto>();
		ReportActivityItemDto dto = new ReportActivityItemDto();
		dto.setDate(DateUtil.formatYYYYMMDD(begin));
		list.add(dto);

		Calendar calBegin = Calendar.getInstance();
		calBegin.setTime(begin);
		calBegin.set(Calendar.HOUR_OF_DAY, 0);
		calBegin.set(Calendar.MINUTE, 0);
		calBegin.set(Calendar.SECOND, 0);
		calBegin.set(Calendar.MILLISECOND, 0);
		Calendar calEnd = Calendar.getInstance();
		calEnd.setTime(end);
		calEnd.set(Calendar.HOUR_OF_DAY, 0);
		calEnd.set(Calendar.MINUTE, 0);
		calEnd.set(Calendar.SECOND, 0);
		calEnd.set(Calendar.MILLISECOND, 0);
		while (calEnd.after(calBegin)) {
			calBegin.add(Calendar.DAY_OF_MONTH, 1);
			dto = new ReportActivityItemDto();
			dto.setDate(DateUtil.formatYYYYMMDD(calBegin.getTime()));
			list.add(dto);
		}
		return list;
	}

	@Override
	public ReportActivityDto activityReport(Integer wechatId, Date startDate,
			Date endDate, Integer activityId, Integer couponSettingId) {
		ReportActivityDto dto = new ReportActivityDto();
		int activityTotal = 0;
		if (activityId != null || couponSettingId != null){
			activityTotal = 1;
		}else{
			activityTotal = activityMapper.countActivity(wechatId, startDate, endDate);
		}
		List<Integer> csis = new ArrayList<Integer>(); 
		if(couponSettingId != null){
			csis.add(couponSettingId);
		}else if (activityId != null ){
			csis = activityCouponSettingMapper.getCouponSettingList(wechatId, activityId);
			if(csis.size() == 0){
				csis = null;
			}
		}else{
			csis = null;
		}
		int couponTotal = couponSettingMapper.countCoupon(wechatId, startDate, endDate, csis);
		int memberTotal = couponMemberMapper.countMember(startDate, endDate, csis);
		int couponReceiveTotal = couponMemberMapper.countReceive(startDate, endDate, csis);
		int couponValidityTotal = couponMemberMapper.countValidity(startDate, endDate, csis);
		List<ReportActivityItemDto> list = findDates4Message(startDate, endDate);
		List<ReportActivityItemDto> shareList = activityShareMapper.activityShare(wechatId, startDate, endDate, activityId);
		outter: for(ReportActivityItemDto ddto : list){
			Date dateBegin = DateUtil.getDateBegin(DateUtil.parse(ddto.getDate()));
			Date dateEnd = DateUtil.getDateEnd(DateUtil.parse(ddto.getDate()));
			ddto.setMemberCount(couponMemberMapper.countMember(dateBegin, dateEnd, csis));
			ddto.setCouponReceiveCount(couponMemberMapper.countReceive(dateBegin, dateEnd, csis));
			ddto.setCouponValidityCount(couponMemberMapper.countValidity(dateBegin, dateEnd, csis));
			for(ReportActivityItemDto temp : shareList){
				if(ddto.getDate().equals(temp.getDate())){
					ddto.setFeedCount(temp.getFeedCount());
					ddto.setFriendsCount(temp.getFriendsCount());
					ddto.setQqCount(temp.getQqCount());
					ddto.setWeiboCount(temp.getWeiboCount());
					ddto.setQzoneCount(temp.getQzoneCount());
					continue outter;
				}
			}
			ddto.setFeedCount(0);
			ddto.setFriendsCount(0);
			ddto.setQqCount(0);
			ddto.setWeiboCount(0);
			ddto.setQzoneCount(0);
		}
		List<PieBaseDto> pieList = activityShareMapper.pieActivityShareReport(wechatId, startDate, endDate, activityId);
		dto.setActivityTotal(activityTotal);
		dto.setCouponTotal(couponTotal);
		dto.setMemberTotal(memberTotal);
		dto.setCouponReceiveTotal(couponReceiveTotal);
		dto.setCouponValidityTotal(couponValidityTotal);
		dto.setList(list);
		if (pieList != null && pieList.size() > 0) {
			int total = 0;
			for (PieBaseDto data : pieList) {
				total += data.getCount();
			}
			for (PieBaseDto data : pieList) {
				if (total != 0){
					data.setRate((double) data.getCount() / total);
				}else{
					data.setRate((double)0);
				}
			}
		}
		List<PieBaseDto> baseList = buildActivityShareList();
		outter: for (PieBaseDto baseDto : baseList){
			for (PieBaseDto pieDto:pieList){
				if(pieDto.getName().equals(baseDto.getName())){
					baseDto.setCount(pieDto.getCount());
					baseDto.setRate((double)pieDto.getRate()/100);
					continue outter;
				}
			}
		}
		dto.setPieList(baseList);
		return dto;
	}

	private List<PieBaseDto> buildActivityShareList() {
		List<PieBaseDto> dtos = new ArrayList<PieBaseDto>(); 
		for(int i=1;i<6;i++){
			PieBaseDto dto = new PieBaseDto();
			dto.setName(Integer.toString(i));
			dto.setCount(0);
			dto.setRate((double)0);
			dtos.add(dto);
		}
		return dtos;
	}

	@Override
	public List<ActivityListDto> activityList(Integer wechatId) {
		List<ActivityListDto> dtos = activityMapper.getActivityList(wechatId);
		return dtos;
	}

}
