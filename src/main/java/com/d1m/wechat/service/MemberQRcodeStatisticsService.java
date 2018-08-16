package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.QRcodeStatisticsDto;

public interface MemberQRcodeStatisticsService {
	public List<QRcodeStatisticsDto> getListMemberQrcodeStatistics();
}
