package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d1m.wechat.mapper.ReportArticleHourSourceMapper;
import com.d1m.wechat.model.ReportArticleHourSource;
import com.d1m.wechat.service.ReportArticleHourSourceService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class ReportArticleHourSourceImpl extends BaseService<ReportArticleHourSource> implements ReportArticleHourSourceService{

	@Autowired
	private ReportArticleHourSourceMapper reportArticleHourSourceMapper;
	
	@Override
	public Mapper<ReportArticleHourSource> getGenericMapper() {
		// TODO Auto-generated method stub
		return reportArticleHourSourceMapper;
	}

}
