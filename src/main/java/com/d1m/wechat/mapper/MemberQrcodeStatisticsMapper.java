package com.d1m.wechat.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.d1m.wechat.dto.QRcodeStatisticsDto;
@Repository("memberQrcodeInvitedMapper")
public interface MemberQrcodeStatisticsMapper {
	public List<QRcodeStatisticsDto> getListMemberQrcodeStatistics();
}
