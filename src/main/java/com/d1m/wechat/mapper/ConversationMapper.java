package com.d1m.wechat.mapper;

import java.util.Date;
import java.util.List;

import com.d1m.wechat.pamametermodel.ConversationModel;
import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.ConversationDto;
import com.d1m.wechat.dto.ReportMessageDto;
import com.d1m.wechat.dto.ReportMessageItemDto;
import com.d1m.wechat.model.Conversation;
import com.d1m.wechat.model.UserBehavior;
import com.d1m.wechat.model.UserLocation;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface ConversationMapper extends MyMapper<Conversation> {

	Page<ConversationDto> search(@Param("wechatId") Integer wechatId,
			@Param("memberId") Integer memberId, @Param("type") Byte type,
			@Param("dir") Integer dir, @Param("status") Byte status,
			@Param("startAt") Date startAt, @Param("endAt") Date endAt,
			@Param("lastConversationAt") Date lastConversationAt,
			@Param("msgType") Byte msgType, @Param("keyWords") String keyWords,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

	Page<ConversationDto> searchUp(@Param("wechatId") Integer wechatId,
			@Param("memberId") Integer memberId, @Param("type") Byte type,
			@Param("msgTypes") List<Byte> msgTypes, @Param("dir") Integer dir,
			@Param("status") Byte status, @Param("startAt") Date startAt,
			@Param("endAt") Date endAt,
			@Param("lastConversationAt") Date lastConversationAt,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

	Page<ConversationDto> searchMass(@Param("wechatId") Integer wechatId,
			@Param("startAt") Date startAt, @Param("endAt") Date endAt,
			@Param("status") Byte status, @Param("sortName") String sortName,
			@Param("sortDir") String sortDir);

	Integer countMass(@Param("wechatId") Integer wechatId,
			@Param("startAt") Date startAt, @Param("endAt") Date endAt,
			@Param("status") Byte status);

	List<ReportMessageItemDto> messageReport(
			@Param("wechatId") Integer wechatId, @Param("start") Date start,
			@Param("end") Date end);

	ReportMessageDto messageCount(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end);

	List<ReportMessageItemDto> messageItemReport(
			@Param("wechatId") Integer wechatId, @Param("start") Date start,
			@Param("end") Date end);

	void updateMemberPhoto(@Param("memberId") Integer memberId,
						   @Param("memberPhoto") String memberPhoto);


    List<ConversationDto> searchCustomerServiceConversation(@Param("wechatId") Integer wechatId, @Param("startDate") Date startDate,
                                                            @Param("endDate") Date endDate);

    List<Integer> getLiveChatStatistic(@Param("wechatId") Integer wechatId);
    
    public Page<UserBehavior> selectUserBehavior(@Param("wechatId") Integer wechatId, @Param("memberId") Integer memberId);
	public List<UserLocation> selectUserLocation(@Param("wechatId") Integer wechatId, @Param("memberId") Integer memberId);

	List<ConversationDto> searchComment(@Param("wechatId")Integer wechatId,@Param("memberId") Integer memberId,@Param("event") Byte event,@Param("description") String description,@Param("msgId") String msgId);
}
