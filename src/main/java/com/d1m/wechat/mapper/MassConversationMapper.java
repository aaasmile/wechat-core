package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.MassConversation;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface MassConversationMapper extends MyMapper<MassConversation> {

	Page<MassConversation> search(@Param("wechatId") Integer wechatId,
			@Param("conversationId") Integer conversationId,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

}