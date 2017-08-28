package com.d1m.wechat.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.ReportArticleDto;
import com.d1m.wechat.dto.ReportArticleItemDto;
import com.d1m.wechat.mapper.ReportArticleSourceMapper;
import com.d1m.wechat.model.ReportArticleSource;
import com.d1m.wechat.service.ReportArticleSourceService;
import com.d1m.wechat.util.DateUtil;

@Service
public class ReportArticleSourceServiceImpl extends BaseService<ReportArticleSource> implements ReportArticleSourceService{
	
	@Autowired
	private ReportArticleSourceMapper reportArticleSourceMapper;

	@Override
	public Mapper<ReportArticleSource> getGenericMapper() {
		// TODO Auto-generated method stub
		return reportArticleSourceMapper;
	}

	private List<ReportArticleItemDto> findDates4Message(Date begin, Date end) {
		List<ReportArticleItemDto> list = new ArrayList<ReportArticleItemDto>();
		ReportArticleItemDto dto = new ReportArticleItemDto();
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
			dto = new ReportArticleItemDto();
			dto.setDate(DateUtil.formatYYYYMMDD(calBegin.getTime()));
			list.add(dto);
		}
		return list;
	}
	
	@Override
	public ReportArticleDto articleReport(Integer wechatId, Date startDate,
			Date endDate) {
		
		ReportArticleDto dto = reportArticleSourceMapper.countArticle(wechatId, startDate, endDate);
		if (dto == null){
			dto = new ReportArticleDto();
		}
		List<ReportArticleItemDto> allList = findDates4Message(startDate, endDate);
		List<ReportArticleItemDto> list = reportArticleSourceMapper.articleReport(wechatId, startDate, endDate);
		outter: for (ReportArticleItemDto ddto : allList) {
			for (ReportArticleItemDto temp : list) {
				if(ddto.getDate().equals(temp.getDate())){
					ddto.setPageUser(temp.getPageUser());
					ddto.setPageCount(temp.getPageCount());
					ddto.setOriPageUser(temp.getOriPageUser());
					ddto.setOriPageCount(temp.getOriPageCount());
					ddto.setAddFavUser(temp.getAddFavUser());
					ddto.setAddFavCount(temp.getAddFavCount());
					ddto.setShareUser(temp.getShareUser());
					ddto.setShareCount(temp.getShareCount());
					continue outter;
				}
			}
			ddto.setPageUser(0);
			ddto.setPageCount(0);
			ddto.setOriPageUser(0);
			ddto.setOriPageCount(0);
			ddto.setAddFavUser(0);
			ddto.setAddFavCount(0);
			ddto.setShareUser(0);
			ddto.setShareCount(0);
		}
		if (allList != null && allList.size() > 0) {
			dto.setList(allList);
		}
		
		List<ReportArticleItemDto> articleList = reportArticleSourceMapper.articleListReport(wechatId, startDate, endDate);
		if (articleList != null && articleList.size() > 0) {
			dto.setArticleList(articleList);
		}
		return dto;
	}

	@Override
	public ReportArticleDto hourArticleReport(Integer wechatId, Date startDate,
			Date endDate) {
		// TODO Auto-generated method stub
		return null;
	}

}
