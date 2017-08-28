package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.ReportUserSourceMapper;
import com.d1m.wechat.model.ReportUserSource;
import com.d1m.wechat.service.ReportUserSourceService;

@Service
public class ReportUserSourceServiceImpl extends BaseService<ReportUserSource> implements ReportUserSourceService {

	@Autowired
	private ReportUserSourceMapper reportUserSourceMapper;
	
	@Override
	public Mapper<ReportUserSource> getGenericMapper() {
		return reportUserSourceMapper;
	}

	public ReportUserSource getRecordByDate(Integer wechatId, String date){
		return reportUserSourceMapper.getRecordByDate(wechatId, date);
	}
}
