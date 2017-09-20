package com.d1m.wechat.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.benefit.MemberBenefitDto;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

/**
 * 
 * @author D1M
 *
 */
public interface MemberBenefitMapper extends MyMapper<MemberBenefitDto> {
	
	Page<MemberBenefitDto> search(
			@Param("wechatId") Integer wechatId,
			@Param("nickname") String nickname,
			@Param("sex") Byte sex, 
			@Param("country") Integer country, 
			@Param("province") Integer province, 
			@Param("city") Integer city, 
			@Param("subscribe") Boolean subscribe, 
			@Param("activityStartAt") Integer activityStartAt, 
			@Param("activityEndAt") Integer activityEndAt, 
			@Param("batchSendOfMonthStartAt") Integer batchSendOfMonthStartAt, 
			@Param("batchSendOfMonthEndAt") Integer batchSendOfMonthEndAt, 
			@Param("attentionStartAt") Date attentionStartAt, 
			@Param("attentionEndAt") Date attentionEndAt, 
			@Param("cancelSubscribeStartAt") Date cancelSubscribeStartAt, 
			@Param("cancelSubscribeEndAt") Date cancelSubscribeEndAt, 
			@Param("mobile") String mobile, 
			@Param("isOnline") Boolean isOnline,
			@Param("daytime") Date daytime,
			@Param("sortName") String sortName, 
			@Param("sortDir") String sortDir, 
			@Param("offset") Integer offset,
			@Param("pageSize") Integer pageSize);

	long count(@Param("wechatId") Integer wechatId,
			@Param("nickname") String nickname,
			@Param("sex") Byte sex, 
			@Param("country") Integer country, 
			@Param("province") Integer province, 
			@Param("city") Integer city, 
			@Param("subscribe") Boolean subscribe, 
			@Param("activityStartAt") Integer activityStartAt, 
			@Param("activityEndAt") Integer activityEndAt, 
			@Param("batchSendOfMonthStartAt") Integer batchSendOfMonthStartAt, 
			@Param("batchSendOfMonthEndAt") Integer batchSendOfMonthEndAt, 
			@Param("attentionStartAt") Date attentionStartAt, 
			@Param("attentionEndAt") Date attentionEndAt, 
			@Param("cancelSubscribeStartAt") Date cancelSubscribeStartAt, 
			@Param("cancelSubscribeEndAt") Date cancelSubscribeEndAt, 
			@Param("mobile") String mobile, 
			@Param("isOnline") Boolean isOnline,
			@Param("daytime") Date daytime,
			@Param("sortName") String sortName, 
			@Param("sortDir") String sortDir);
	
	MemberBenefitDto getByMemberId(@Param("wechatId")Integer wechatId, @Param("id") Integer id);

}
