package com.d1m.wechat.service;

import com.d1m.wechat.model.MassConversationResult;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MassConversationResultService extends
        IService<MassConversationResult> {

	MassConversationResult getByMsgId(Integer wechatId, String msgId);

	MassConversationResult getByConversationId(Integer wechatId, Integer conversationId);

	List<String> selectMsgDataId(Integer wechatId);

	List<Integer> getConversationId(Integer wechatId, String msgDataId);
}
