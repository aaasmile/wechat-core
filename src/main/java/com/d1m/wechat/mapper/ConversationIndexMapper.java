package com.d1m.wechat.mapper;

import com.d1m.wechat.model.ConversationIndex;
import com.d1m.wechat.util.MyMapper;

public interface ConversationIndexMapper extends MyMapper<ConversationIndex> {

	void weekDelete();
}