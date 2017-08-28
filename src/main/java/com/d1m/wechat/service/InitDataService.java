package com.d1m.wechat.service;

import java.util.Date;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;

import com.d1m.wechat.dto.ReplyDto;
import com.d1m.wechat.model.Reply;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.model.enums.ReplyStatus;
import com.d1m.wechat.model.enums.ReplyType;
import com.d1m.wechat.model.enums.WechatStatus;
import com.d1m.wechat.pamametermodel.WechatModel;
import com.d1m.wechat.util.AppContextUtils;
import com.github.pagehelper.Page;

public class InitDataService implements InitializingBean {

	private ReplyService replyService = AppContextUtils
			.getBean(ReplyService.class);

	private WechatService wechatService = AppContextUtils
			.getBean(WechatService.class);

	private static Logger log = LoggerFactory.getLogger(InitDataService.class);

	public void initSubscribeAutoReply() {
		WechatModel wechatModel = new WechatModel();
		wechatModel.setStatus(WechatStatus.INUSED.getValue());
		Page<Wechat> wechats = wechatService.search(wechatModel, false);
		log.info("wechat size : {}", wechats.size());
		ReplyDto subscribeReply = null;
		Date current = new Date();
		for (Wechat wechat : wechats) {
			subscribeReply = replyService.getSubscribeReply(wechat.getId());
			if (subscribeReply != null) {
				log.info("wechat : {}, subscribe reply already exist, id {}",
						wechat.getId(), subscribeReply.getId());
				continue;
			}
			Reply reply = new Reply();
			reply.setReplyType(ReplyType.SUBSCRIBE_REPLY.getValue());
			reply.setStatus(ReplyStatus.INUSED.getValue());
			reply.setWechatId(wechat.getId());
			reply.setCreatedAt(current);
			reply.setReplyKey("关注回复");
			replyService.save(reply);
			log.info("wechat : {}, subscribe reply created, id {}",
					wechat.getId(), reply.getId());
		}

	}
	
	public void initDefaultAutoReply() {
		WechatModel wechatModel = new WechatModel();
		wechatModel.setStatus(WechatStatus.INUSED.getValue());
		Page<Wechat> wechats = wechatService.search(wechatModel, false);
		log.info("wechat size : {}", wechats.size());
		ReplyDto defaultReply = null;
		Date current = new Date();
		for (Wechat wechat : wechats) {
			defaultReply = replyService.getDefaultReply(wechat.getId());
			if (defaultReply != null) {
				log.info("wechat : {}, default reply already exist, id {}",
						wechat.getId(), defaultReply.getId());
				continue;
			}
			Reply reply = new Reply();
			reply.setReplyType(ReplyType.DEFAULT_REPLY.getValue());
			reply.setStatus(ReplyStatus.INUSED.getValue());
			reply.setWechatId(wechat.getId());
			reply.setCreatedAt(current);
			reply.setReplyKey("默认回复");
			replyService.save(reply);
			log.info("wechat : {}, default reply created, id {}",
					wechat.getId(), reply.getId());
		}

	}

	@Override
	public void afterPropertiesSet() throws Exception {
		initSubscribeAutoReply();
		initDefaultAutoReply();
	}

}
