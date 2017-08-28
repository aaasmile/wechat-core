package com.d1m.wechat.service.engine.event.impl;

import com.d1m.wechat.anno.EventCode;
import com.d1m.wechat.mapper.TemplateResultMapper;
import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.Event;
import com.d1m.wechat.model.enums.MassConversationResultStatus;
import com.d1m.wechat.model.enums.MsgType;
import com.d1m.wechat.pamametermodel.MassConversationModel;
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

@EventCode(value = Event.TEMPLATESENDJOBFINISH)
@Service
public class TemplateSendJobFinishEvent implements IEvent {

	private static Logger log = LoggerFactory
			.getLogger(TemplateSendJobFinishEvent.class);

	@Autowired
	private TemplateResultMapper templateResultMapper;

	@Override
	public Map<String, Object> handle(Conversation conversation, Member member) {
		return null;
	}

	@Override
	public Map<String, Object> handleSystem(OriginalConversation originalConversation, Document document) {
		Element root = document.getRootElement();
		String createTime = ParamUtil.getElementContent(root, "CreateTime");
		Date current = new Date(ParamUtil.getLong(createTime, null) * 1000);
		String msgID = ParamUtil.getElementContent(root, "MsgId");
		if (msgID == null) {
			msgID = ParamUtil.getElementContent(root, "MsgID");
		}
		String msg = ParamUtil.getElementContent(root, "Status");
		TemplateResult tr = new TemplateResult();
		tr.setMsgId(msgID);
		TemplateResult record = templateResultMapper.selectOne(tr);
		if (record != null) {
			record.setCreateAt(current);
			record.setMsg(msg);
			if (msg.equals("success")) {
				record.setStatus((byte) 1);
			} else {
				record.setStatus((byte) 0);
			}
			int result = templateResultMapper.updateByPrimaryKeySelective(record);
			log.info("update result: {}", result);
		}
		return null;
	}
}
