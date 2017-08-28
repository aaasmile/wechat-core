package com.d1m.wechat.service.impl;

import cn.d1m.wechat.client.model.WxMessage;
import com.alibaba.fastjson.JSONObject;
import com.d1m.common.ds.TenantContext;
import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.mapper.MassConversationBatchMemberMapper;
import com.d1m.wechat.mapper.MassConversationBatchResultMapper;
import com.d1m.wechat.model.MassConversationBatchMember;
import com.d1m.wechat.model.MassConversationBatchResult;
import com.d1m.wechat.model.enums.MassConversationResultStatus;
import com.d1m.wechat.model.enums.MsgType;
import com.d1m.wechat.service.MassConversationBatchResultService;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import java.util.Date;
import java.util.List;

/**
 * Created by D1M on 2017/6/2.
 */
@Service
@Slf4j
public class MassConversationBatchResultServiceImpl extends
        BaseService<MassConversationBatchResult> implements
        MassConversationBatchResultService {

    @Autowired
    private MassConversationBatchResultMapper massConversationBatchResultMapper;

    @Autowired
    private MassConversationBatchMemberMapper massConversationBatchMemberMapper;

    @Override
    public MassConversationBatchResult getByMsgId(Integer wechatId, String msgId) {
        MassConversationBatchResult massConversationBatchResult = new MassConversationBatchResult();
        massConversationBatchResult.setWechatId(wechatId);
        massConversationBatchResult.setMsgId(msgId.toString());
        return massConversationBatchResultMapper.selectOne(massConversationBatchResult);
    }

    @Override
    public void updateBatchResult(MassConversationBatchResult result) {
        massConversationBatchResultMapper.updateByPrimaryKeySelective(result);
    }

    @Override
    public boolean isBatchMsgSent(Integer wechatId, Integer conversationId, Integer totalBatch) {
        MassConversationBatchResult sendingBatchResult = new MassConversationBatchResult();
        sendingBatchResult.setWechatId(wechatId);
        sendingBatchResult.setConversationId(conversationId);
        sendingBatchResult.setStatus(MassConversationResultStatus.SENDING.getValue());
        MassConversationBatchResult theBatchSize = new MassConversationBatchResult();
        theBatchSize.setWechatId(wechatId);
        theBatchSize.setConversationId(conversationId);
        return massConversationBatchResultMapper.selectCount(sendingBatchResult) == 0 &&
                massConversationBatchResultMapper.selectCount(theBatchSize) == totalBatch;
    }

    private Integer insertBatchMassMessageResult(Integer wechatId, int i, Integer conversationId, boolean success, String msgId, Byte msgType, String msgContent) {
        MassConversationBatchResult massConversationBatchResult = new MassConversationBatchResult();
        massConversationBatchResult.setCreatedAt(new Date());
        massConversationBatchResult.setPiCi(i);
        massConversationBatchResult.setWechatId(wechatId);
        massConversationBatchResult.setConversationId(conversationId);
        massConversationBatchResult.setMsgType(msgType);
        massConversationBatchResult.setMsgContent(msgContent);
        massConversationBatchResult.setStatus(MassConversationResultStatus.WAIT_SEND.getValue());
        massConversationBatchResult.setMsgId(msgId);
        massConversationBatchResultMapper.insert(massConversationBatchResult);
        return massConversationBatchResult.getId();
    }

    private void insertBatchMassMessageMember(Integer wechatId, Integer batchId, Page<MemberDto> list) {
        for (MemberDto member:list){
            MassConversationBatchMember massConversationBatchMember = new MassConversationBatchMember();
            massConversationBatchMember.setBatchId(batchId);
            massConversationBatchMember.setMemberId(member.getId());
            massConversationBatchMember.setOpenId(member.getOpenId());
            massConversationBatchMember.setWechatId(wechatId);
            massConversationBatchMemberMapper.insert(massConversationBatchMember);
        }
    }

    @Override
    public void batchSendMassMsg(int batchIndex, Integer wechatId, Page<MemberDto> list, Integer conversationId, MsgType msgType, String wxMassMessage ) {
        Integer batchId = insertBatchMassMessageResult(wechatId, batchIndex, conversationId, false, null, msgType.getValue(),wxMassMessage);
        insertBatchMassMessageMember(wechatId,batchId,list);
    }

    @Override
    public Mapper<MassConversationBatchResult> getGenericMapper() {
        return massConversationBatchResultMapper;
    }
}
