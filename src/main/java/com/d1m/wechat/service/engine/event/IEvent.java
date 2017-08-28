package com.d1m.wechat.service.engine.event;

import java.util.Map;

import com.d1m.wechat.model.OriginalConversation;
import org.dom4j.Document;
import org.springframework.stereotype.Service;

import com.d1m.wechat.model.Conversation;
import com.d1m.wechat.model.Member;

@Service
public interface IEvent {

	Map<String, Object> handle(Conversation conversation, Member member);

	Map<String, Object> handleSystem(OriginalConversation originalConversation, Document document);

}
