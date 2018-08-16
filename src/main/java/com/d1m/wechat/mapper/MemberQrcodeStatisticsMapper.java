package com.d1m.wechat.mapper;

import java.util.List;

import org.springframework.stereotype.Repository;

import com.d1m.wechat.dto.QRcodeStatisticsDto;
import com.d1m.wechat.util.MyMapper;
@Repository("memberQrcodeStatisticsMapper")
public interface MemberQrcodeStatisticsMapper extends MyMapper<QRcodeStatisticsDto>{
	public List<QRcodeStatisticsDto> getListMemberQrcodeStatistics();
}
