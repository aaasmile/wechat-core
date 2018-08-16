package com.d1m.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d1m.wechat.dto.QRcodeStatisticsDto;
import com.d1m.wechat.mapper.MemberQrcodeStatisticsMapper;
import com.d1m.wechat.service.MemberQRcodeStatisticsService;

@Service
public class MemberQRcodeStatisticsServiceImpl implements MemberQRcodeStatisticsService {

	@Autowired
	private MemberQrcodeStatisticsMapper MemberQrcodeStatisticsMapper;
	@Override
	public List<QRcodeStatisticsDto> getListMemberQrcodeStatistics() {
		// TODO Auto-generated method stub
		return MemberQrcodeStatisticsMapper.getListMemberQrcodeStatistics();
	}

}
