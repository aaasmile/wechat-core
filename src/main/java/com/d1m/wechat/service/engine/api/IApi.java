package com.d1m.wechat.service.engine.api;

import java.util.List;

import org.springframework.stereotype.Service;

import com.d1m.wechat.model.Conversation;
import com.d1m.wechat.model.Member;

@Service
public interface IApi {
	void handle(Integer wechatId,Conversation conversation,List<Member> members);
}
