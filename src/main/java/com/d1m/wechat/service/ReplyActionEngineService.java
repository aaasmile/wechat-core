package com.d1m.wechat.service;

import com.d1m.wechat.dto.ReplyActionEngineDto;
import com.d1m.wechat.model.ReplyActionEngine;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.ActionEngineModel;
import com.d1m.wechat.pamametermodel.ReplyModel;
import com.github.pagehelper.Page;

public interface ReplyActionEngineService extends IService<ReplyActionEngine> {

	ReplyActionEngine create(Integer wechatId, User user, ReplyModel replyModel);

	void update(Integer wechatId, User user, ReplyModel replyModel);

	void delete(Integer wechatId, User user, Integer replyActionEngineId);

	Page<ReplyActionEngineDto> search(Integer wechatId,
			ActionEngineModel actionEngineModel, boolean queryCount);

}
