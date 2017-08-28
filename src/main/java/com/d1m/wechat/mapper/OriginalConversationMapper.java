package com.d1m.wechat.mapper;

import com.d1m.wechat.model.OriginalConversation;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface OriginalConversationMapper extends
		MyMapper<OriginalConversation> {

	Page<OriginalConversation> searchActiveOriginalConversations();

}