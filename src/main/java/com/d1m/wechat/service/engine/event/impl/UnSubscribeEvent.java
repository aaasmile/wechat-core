package com.d1m.wechat.service.engine.event.impl;

import java.util.Map;

import com.d1m.wechat.model.OriginalConversation;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d1m.wechat.anno.EventCode;
import com.d1m.wechat.model.Conversation;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.MemberSubscribe;
import com.d1m.wechat.model.enums.Event;
import com.d1m.wechat.model.enums.Subscribe;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.MemberSubscribeService;
import com.d1m.wechat.service.engine.event.IEvent;

/**
 * 取消关注
 */
@EventCode(value = Event.UNSUBSCRIBE)
@Service
public class UnSubscribeEvent implements IEvent {

	private static Logger log = LoggerFactory.getLogger(UnSubscribeEvent.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberSubscribeService memberSubscribeService;

	@Override
	public Map<String, Object> handle(Conversation conversation, Member member) {
		log.info("UnSubscribeEvent handle.");
		Integer wechatId = conversation.getWechatId();
		Member update = new Member();
		update.setId(member.getId());
		update.setUnsubscribeAt(conversation.getCreatedAt());
		update.setIsSubscribe(false);
		memberService.updateNotNull(update);
		log.info("UnsubscribeAt {}", update.getUnsubscribeAt());
		MemberSubscribe memberSubscribe = new MemberSubscribe();
		memberSubscribe.setCreatedAt(conversation.getCreatedAt());
		memberSubscribe.setMemberId(conversation.getMemberId());
		memberSubscribe.setSubscribe(Subscribe.UNSUBSCRIBE.getValue());
		memberSubscribe.setSubscribeAt(conversation.getCreatedAt());
		memberSubscribe.setWechatId(wechatId);
		memberSubscribeService.save(memberSubscribe);

		return null;
	}

	@Override
	public Map<String, Object> handleSystem(OriginalConversation originalConversation, Document document) {
		return null;
	}

}
