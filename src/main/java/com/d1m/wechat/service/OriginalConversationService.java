package com.d1m.wechat.service;

import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.OriginalConversation;
import org.dom4j.Document;
import org.dom4j.Element;

public interface OriginalConversationService extends
		IService<OriginalConversation> {

	OriginalConversation get(Integer wechatId, Integer id);

    /**
     * 异步处理原始会话
     *
     * @param originalConversation 原始会话
     */
    void execute(Member member, Document document, OriginalConversation originalConversation);
}
