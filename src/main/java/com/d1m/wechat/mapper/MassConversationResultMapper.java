package com.d1m.wechat.mapper;

import com.d1m.wechat.model.MassConversationResult;
import com.d1m.wechat.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface MassConversationResultMapper extends MyMapper<MassConversationResult> {
    List<String> selectMsgDataId(@Param("wechatId")Integer wechatId);

    List<Integer> getConversationId(@Param("wechatId")Integer wechatId,@Param("msgDataId")String msgDataId);
}