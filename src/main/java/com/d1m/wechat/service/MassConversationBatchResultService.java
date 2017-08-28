package com.d1m.wechat.service;

import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.model.MassConversationBatchResult;
import com.d1m.wechat.model.enums.MsgType;
import com.github.pagehelper.Page;

/**
 * Created by D1M on 2017/6/2.
 */
public interface MassConversationBatchResultService extends
        IService<MassConversationBatchResult> {
    MassConversationBatchResult getByMsgId(Integer wechatId, String msgId);

    void updateBatchResult(MassConversationBatchResult result);

    boolean isBatchMsgSent(Integer wechatId, Integer conversationId, Integer totalBatch);

    void batchSendMassMsg(int batchIndex, Integer wechatId, Page<MemberDto> list, Integer conversationId, MsgType msgType, String wxMassMessage);
}
