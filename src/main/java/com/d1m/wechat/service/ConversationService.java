package com.d1m.wechat.service;

import java.util.Date;
import java.util.List;

import com.d1m.wechat.model.*;
import org.dom4j.Document;

import com.d1m.wechat.dto.ConversationDto;
import com.d1m.wechat.dto.ReportMessageDto;
import com.d1m.wechat.pamametermodel.ConversationModel;
import com.d1m.wechat.pamametermodel.MassConversationModel;
import com.github.pagehelper.Page;
import org.dom4j.Element;

public interface ConversationService extends IService<Conversation> {

	/*
	OriginalConversation preHandleMemberToWechat(Wechat wechat, Document document);

	boolean memberToWechat(Member member, Document document, OriginalConversation originalConversation);
    */
	Conversation wechatToMember(Integer wechatId, User user,
			ConversationModel conversationModel);

	Page<ConversationDto> search(Integer wechatId,
			ConversationModel conversationModel, boolean queryCount);

	Page<ConversationDto> searchUnread(Integer wechatId,
			ConversationModel conversationModel, boolean queryCount);

	Page<ConversationDto> searchMass(Integer wechatId,
			ConversationModel conversationModel, boolean queryCount);

	Integer countMass(Integer wechatId, ConversationModel conversationModel);

	Integer countMassAvalible(Integer wechatId);

	void preMassConversation(Integer wechatId, User user,
			MassConversationModel massConversationModel);

	void auditMassConversation(Integer wechatId, User user,
			MassConversationModel massConversationModel);

	void sendMassConversation(Integer wechatId, User user,
			MassConversationModel massConversationModel);

	ReportMessageDto messageReport(Integer wechatId, Date start, Date end);

	ReportMessageDto hourMessageReport(Integer wechatId, Date startdate, Date endDate);

	List<ConversationDto> searchCustomerServiceConversation(Integer wechatId, Date startDate, Date endDate);
}
