package com.d1m.wechat.service;

import java.util.Date;

import com.d1m.wechat.dto.ReportArticleDto;
import com.d1m.wechat.model.ReportArticleSource;

public interface ReportArticleSourceService extends IService<ReportArticleSource>{

	ReportArticleDto articleReport(Integer wechatId, Date startDate,
			Date endDate);

	ReportArticleDto hourArticleReport(Integer wechatId, Date startDate,
			Date endDate);
}
