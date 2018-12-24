package com.d1m.wechat.service;

import java.util.Date;
import java.util.List;

import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.RabbitmqMethod;
import com.d1m.wechat.model.enums.RabbitmqTable;
import com.d1m.wechat.dto.ConversationDto;
import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.dto.ReportMessageDto;
import com.d1m.wechat.pamametermodel.ConversationModel;
import com.d1m.wechat.pamametermodel.MassConversationModel;
import com.github.pagehelper.Page;

public interface ConversationService extends IService<Conversation> {

	public void send2SocialWechatCoreApi(RabbitmqTable table, RabbitmqMethod method, Object obj);
	
	Conversation wechatToMember(Integer wechatId, User user, ConversationModel conversationModel, MemberDto member);

	Page<ConversationDto> search(Integer wechatId, ConversationModel conversationModel, boolean queryCount);

	Page<ConversationDto> searchUnread(Integer wechatId, ConversationModel conversationModel, boolean queryCount);

	Page<ConversationDto> searchMass(Integer wechatId, ConversationModel conversationModel, boolean queryCount);

	Integer countMass(Integer wechatId, ConversationModel conversationModel);

	Integer countMassAvalible(Integer wechatId);

	void preMassConversation(Integer wechatId, User user, MassConversationModel massConversationModel);

	void auditMassConversation(Integer wechatId, User user, MassConversationModel massConversationModel);

	void sendMassConversation(Integer wechatId, User user, MassConversationModel massConversationModel);

	ReportMessageDto messageReport(Integer wechatId, Date start, Date end);

	ReportMessageDto hourMessageReport(Integer wechatId, Date startdate, Date endDate);

	List<ConversationDto> searchCustomerServiceConversation(Integer wechatId, Date startDate, Date endDate);

	public Page<UserBehavior> selectUserBehavior(Integer wechatId, ConversationModel conversationModel);
	public Page<UserLocation> selectUserLocation(Integer wechatId, ConversationModel conversationModel);
}
