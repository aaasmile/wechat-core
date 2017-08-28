package com.d1m.wechat.service;

import com.d1m.wechat.model.ReportUserSource;

public interface ReportUserSourceService extends IService<ReportUserSource> {
	public ReportUserSource getRecordByDate(Integer wechatId, String date);
}
