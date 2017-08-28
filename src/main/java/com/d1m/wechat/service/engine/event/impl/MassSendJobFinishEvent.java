package com.d1m.wechat.service.engine.event.impl;

import com.d1m.wechat.anno.EventCode;
import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.Event;
import com.d1m.wechat.model.enums.MassConversationResultStatus;
import com.d1m.wechat.model.enums.MsgType;
import com.d1m.wechat.pamametermodel.MassConversationModel;
import com.d1m.wechat.service.MassConversationBatchResultService;
import com.d1m.wechat.service.MassConversationResultService;
import com.d1m.wechat.service.MassConversationService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.engine.event.IEvent;
import com.d1m.wechat.util.ParamUtil;
import org.dom4j.Document;
import org.dom4j.Element;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;

@EventCode(value = Event.MASSSENDJOBFINISH)
@Service
public class MassSendJobFinishEvent implements IEvent {

	private static Logger log = LoggerFactory
			.getLogger(MassSendJobFinishEvent.class);

	@Autowired
	private MassConversationResultService massConversationResultService;
	@Autowired
	private MassConversationBatchResultService massConversationBatchResultService;
	@Autowired
	private MassConversationService massConversationService;
	@Autowired
	private MemberService memberService;

	@Override
	public Map<String, Object> handle(Conversation conversation, Member member) {
		return null;
	}

	@Override
	public Map<String, Object> handleSystem(OriginalConversation originalConversation, Document document) {
		log.info("mass conversation result back.");
		Integer wechatId = originalConversation.getWechatId();
		Element root = document.getRootElement();
		Long msgId = ParamUtil.getLong(ParamUtil.getElementContent(root, "MsgId"), null);
		if (msgId == null) {
			msgId = ParamUtil.getLong(ParamUtil.getElementContent(root, "MsgID"), null);
		}
		log.info("wechat id : {}, msg id : {}.", wechatId, msgId);
		MassConversationResult massConversationResult = null;

		MassConversationBatchResult massConversationBatchResult = massConversationBatchResultService.getByMsgId(wechatId, msgId.toString());
		if (massConversationBatchResult != null) {
			if (!massConversationBatchResult.getStatus().equals(MassConversationResultStatus.SENDING.getValue())) {
				log.info("wechat id : {}, msg id : {} status : {}, already handle.",
						wechatId, msgId, massConversationBatchResult.getStatus());
				return null;
			}
			log.info("get massConversationResult by conversationId: wechat id : {}, conversationId id : {}.", wechatId, massConversationBatchResult.getConversationId());
			massConversationResult = massConversationResultService.getByConversationId(wechatId, massConversationBatchResult.getConversationId());
		} else {
			log.info("get massConversationResult by msgId: wechat id : {}, msg id : {}.", wechatId, msgId);
			massConversationResult = massConversationResultService.getByMsgId(wechatId, msgId.toString());
		}

		if (massConversationResult == null) {
			log.error("wechat id : {}, msg id : {} not found.", wechatId, msgId);
			return null;
		}
		/*if (!massConversationResult.getStatus().equals(
				MassConversationResultStatus.SENDING.getValue())) {
			log.info(
					"wechat id : {}, msg id : {} status : {}, already handle.",
					wechatId, msgId, massConversationResult.getStatus());
			return null;
		}*/
		String fromUserName = ParamUtil.getElementContent(root, "FromUserName");
		String msgType = ParamUtil.getElementContent(root, "MsgType");
		String event = ParamUtil.getElementContent(root, "Event");
		String status = ParamUtil.getElementContent(root, "Status");
		MassConversationResultStatus resultStatus = MassConversationResultStatus.getByDesc(status);
		if (resultStatus == null) {
			log.error("status : {} not defined.", status);
		}
		log.info("resultStatus : {}", resultStatus);

		Integer totalCount = ParamUtil.getInt(
				ParamUtil.getElementContent(root, "TotalCount"),
				0);
		Integer filterCount = ParamUtil.getInt(
				ParamUtil.getElementContent(root, "FilterCount"),
				0);
		Integer sentCount = ParamUtil.getInt(
				ParamUtil.getElementContent(root, "SentCount"),
				0);
		Integer errorCount = ParamUtil.getInt(
				ParamUtil.getElementContent(root, "ErrorCount"),
				0);
		boolean noOneReceive = (totalCount == 0 || filterCount == 0 || sentCount == 0);
		log.info("noOneReceive : {}", noOneReceive);

		if (massConversationBatchResult != null) {
			updateMassBatchResult(massConversationBatchResult, resultStatus, totalCount, filterCount, sentCount, errorCount);
		}

		massConversationResult.setErrorCount(getIntValue(massConversationResult.getErrorCount()) + errorCount);
		Event et = Event.getValueByName(event);
		massConversationResult.setEvent(et != null ? et.getValue() : null);
		massConversationResult.setFilterCount(getIntValue(massConversationResult.getFilterCount()) + filterCount);
		massConversationResult.setMpHelper(fromUserName);
		MsgType mt = MsgType.getByName(msgType);
		massConversationResult.setMsgType(mt != null ? mt.getValue() : null);
		massConversationResult.setSendCount(getIntValue(massConversationResult.getSendCount()) + sentCount);
		if (resultStatus != null) {
			changeMassResultStatus(wechatId, massConversationResult, massConversationBatchResult, resultStatus);
		}
		massConversationResult.setTotalCount(getIntValue(massConversationResult.getTotalCount()) + totalCount);
		massConversationResult.setWxSendAt(new Date());
		massConversationResultService.updateAll(massConversationResult);

		// 非分组发送时才在此处更新本月群发数
		if (massConversationResult.getTotalBatch()==null) {
			Integer conversationId = massConversationResult.getConversationId();
			MassConversationModel massConversationModel = new MassConversationModel();
			massConversationModel.setWechatId(wechatId);
			massConversationModel.setConversationId(conversationId);
			massConversationModel.disablePage();
			List<MassConversation> massConversations = massConversationService.search(wechatId, massConversationModel, false);
			Member massMember = null;
			for (MassConversation massConversation : massConversations) {
				massMember = memberService.getMember(wechatId,massConversation.getMemberId());
				if (massMember != null) {
					if (massMember.getBatchsendMonth() == null) {
						massMember.setBatchsendMonth(0);
					}
					Member update = new Member();
					update.setId(massMember.getId());
					update.setBatchsendMonth(massMember.getBatchsendMonth() + 1);
					memberService.updateNotNull(update);
				}
			}
		}
		return null;
	}

	private void changeMassResultStatus(Integer wechatId, MassConversationResult massConversationResult, MassConversationBatchResult massConversationBatchResult, MassConversationResultStatus resultStatus) {
		if(massConversationBatchResult == null) {
            log.info("mass send finished : {}", massConversationResult.getConversationId());
            massConversationResult.setStatus(resultStatus.getValue());
        } else {
            if(massConversationBatchResultService.isBatchMsgSent(wechatId, massConversationResult.getConversationId(), massConversationResult.getTotalBatch())) {
                log.info("mass batch send finished : {}", massConversationResult.getConversationId());
                massConversationResult.setStatus(resultStatus.getValue());
            }
        }
	}

	private void updateMassBatchResult(MassConversationBatchResult massConversationBatchResult, MassConversationResultStatus resultStatus, Integer totalCount, Integer filterCount, Integer sentCount, Integer errorCount) {
		massConversationBatchResult.setErrorCount(errorCount);
		massConversationBatchResult.setFilterCount(filterCount);
		massConversationBatchResult.setSendCount(sentCount);
		massConversationBatchResult.setTotalCount(totalCount);
		if (resultStatus != null) {
            massConversationBatchResult.setStatus(resultStatus.getValue());
        }
		massConversationBatchResultService.updateBatchResult(massConversationBatchResult);
	}

	private Integer getIntValue(Integer aInt) {
		return aInt==null ? 0 : aInt;
	}
}
