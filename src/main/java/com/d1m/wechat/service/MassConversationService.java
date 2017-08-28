package com.d1m.wechat.service;

import com.d1m.wechat.model.MassConversation;
import com.d1m.wechat.pamametermodel.MassConversationModel;
import com.github.pagehelper.Page;

public interface MassConversationService extends IService<MassConversation> {

	Page<MassConversation> search(Integer wechatId,
			MassConversationModel massConversationModel, boolean queryCount);

}