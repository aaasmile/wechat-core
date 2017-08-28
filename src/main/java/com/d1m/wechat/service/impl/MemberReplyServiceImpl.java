package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.ReportKeyMatchDto;
import com.d1m.wechat.dto.ReportKeyMatchTopDto;
import com.d1m.wechat.dto.ReportKeyMatchTrendDto;
import com.d1m.wechat.mapper.MemberReplyMapper;
import com.d1m.wechat.model.MemberReply;
import com.d1m.wechat.service.MemberReplyService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class MemberReplyServiceImpl extends BaseService<MemberReply> implements
		MemberReplyService {
	
	@Autowired
	private MemberReplyMapper memberReplyMapper;
	
	@Override
	public Mapper<MemberReply> getGenericMapper() {
		return memberReplyMapper;
	}

	private List<ReportKeyMatchTrendDto> findDates(Date begin, Date end) {
		List<ReportKeyMatchTrendDto> list = new ArrayList<ReportKeyMatchTrendDto>();
		ReportKeyMatchTrendDto dto = new ReportKeyMatchTrendDto();
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
			dto = new ReportKeyMatchTrendDto();
			dto.setDate(DateUtil.formatYYYYMMDD(calBegin.getTime()));
			list.add(dto);
		}
		return list;
	}

	@Override
	public ReportKeyMatchDto keyMatch(Integer wechatId, Date start, Date end) {
		notBlank(wechatId, Message.REPORT_ARGS_INVALID);
		notBlank(start, Message.REPORT_ARGS_INVALID);
		notBlank(end, Message.REPORT_ARGS_INVALID);
		
		List<ReportKeyMatchTrendDto> allList = findDates(start,end);
		List<ReportKeyMatchTrendDto> trendList = memberReplyMapper.matchTrend(wechatId, start, end);
		for (ReportKeyMatchTrendDto dto : allList) {
			for (ReportKeyMatchTrendDto temp : trendList) {
				if (dto.getDate().equals(temp.getDate())) {
					dto.setTotalCount(temp.getTotalCount());
					dto.setMatchCount(temp.getMatchCount());
					break;
				}
			}
		}
		ReportKeyMatchDto dto = new ReportKeyMatchDto();

		if (allList != null && allList.size() > 0) {
			dto.setTrendList(allList);
			for (ReportKeyMatchTrendDto param : allList) {
				dto.setMatchCount(dto.getMatchCount()
						+ param.getMatchCount());
				dto.setTotalCount(dto.getTotalCount()
						+ param.getTotalCount());
			}
		}
		if(dto.getTotalCount()>0){
			BigDecimal matchRate = new BigDecimal((double)dto.getMatchCount()*100/dto.getTotalCount());  
			dto.setMatchRate(matchRate.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
		}else{
			dto.setMatchRate(0);
		}
		return dto;
	}

	@Override
	public Page<ReportKeyMatchTopDto> matchTop(Integer wechatId, Date start,
			Date end, Integer top) {
		notBlank(wechatId, null);
		notBlank(start, null);
		notBlank(end, null);
		PageHelper.startPage(1, top, true);
		return memberReplyMapper.matchTop(wechatId, start, end, top);
	}

	@Override
	public Page<ReportKeyMatchTopDto> unMatchTop(Integer wechatId, Date start,
			Date end, Integer top) {
		notBlank(wechatId, null);
		notBlank(start, null);
		notBlank(end, null);
		PageHelper.startPage(1, top, true);
		return memberReplyMapper.unMatchTop(wechatId, start, end, top);
	}

	@Override
	public Page<ReportKeyMatchTopDto> match(Integer wechatId, Date start,
			Date end) {
		return memberReplyMapper.matchTop(wechatId, start, end, null);
	}
}
