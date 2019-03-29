package com.d1m.wechat.mapper;

import com.d1m.wechat.model.MassConversationResult;
import com.d1m.wechat.util.MyMapper;

import java.util.List;

public interface MassConversationResultMapper extends MyMapper<MassConversationResult> {
    List<String> selectMsgDataId(Integer wechatId);
}