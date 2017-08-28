package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.ReplyDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.Reply;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.ReplyModel;
import com.github.pagehelper.Page;

public interface ReplyService extends IService<Reply> {

	Reply create(Integer wechatId, User user, ReplyModel replyModel)
			throws WechatException;

	void update(Integer wechatId, User user, ReplyModel replyModel)
			throws WechatException;

	ReplyDto get(Integer wechatId, Integer id) throws WechatException;

	void delete(Integer wechatId, Integer id) throws WechatException;

	Page<ReplyDto> search(Integer wechatId, ReplyModel replyModel,
			boolean queryCount);

	ReplyDto getSubscribeReply(Integer wechatId);

	List<ReplyDto> searchMatchReply(Integer wechatId, String content);

	ReplyDto getDefaultReply(Integer wechatId);

}
