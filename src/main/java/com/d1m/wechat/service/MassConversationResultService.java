package com.d1m.wechat.service;

import com.d1m.wechat.model.MassConversationResult;

public interface MassConversationResultService extends
        IService<MassConversationResult> {

	MassConversationResult getByMsgId(Integer wechatId, String msgId);

	MassConversationResult getByConversationId(Integer wechatId, Integer conversationId);
}
