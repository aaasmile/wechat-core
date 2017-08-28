package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.ReplyActionEngineDto;
import com.d1m.wechat.mapper.ActionEngineMapper;
import com.d1m.wechat.mapper.ReplyActionEngineMapper;
import com.d1m.wechat.mapper.ReplyMapper;
import com.d1m.wechat.model.ActionEngine;
import com.d1m.wechat.model.Reply;
import com.d1m.wechat.model.ReplyActionEngine;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.ActionEngineModel;
import com.d1m.wechat.pamametermodel.ReplyModel;
import com.d1m.wechat.service.ReplyActionEngineService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ReplyActionEngineServiceImpl extends
		BaseService<ReplyActionEngine> implements ReplyActionEngineService {

	@Autowired
	private ReplyActionEngineMapper replyActionEngineMapper;

	@Autowired
	private ActionEngineMapper actionEngineMapper;

	@Autowired
	private ReplyMapper replyMapper;

	@Override
	public Mapper<ReplyActionEngine> getGenericMapper() {
		return replyActionEngineMapper;
	}

	private Reply getReply(Integer wechatId, Integer replyId) {
		Reply reply = new Reply();
		reply.setWechatId(wechatId);
		reply.setId(replyId);
		reply = replyMapper.selectOne(reply);
		return reply;
	}

	@Override
	public ReplyActionEngine create(Integer wechatId, User user,
			ReplyModel replyModel) {
		notBlank(replyModel.getId(), Message.REPLY_ID_NOT_BLANK);
		notBlank(replyModel.getRules(), Message.REPLY_ACTION_ENGINE_NOT_BLANK);
		Reply reply = getReply(wechatId, replyModel.getId());
		notBlank(reply, Message.REPLY_NOT_EXIST);

		Date current = new Date();
		ActionEngine actionEngine = QrcodeActionEngineServiceImpl.getRule(
				wechatId, user, replyModel.getRules(), current);
		actionEngineMapper.insert(actionEngine);
		ReplyActionEngine qae = new ReplyActionEngine();
		qae.setActionEngineId(actionEngine.getId());
		qae.setCreatedAt(current);
		qae.setCreatorId(user.getId());
		qae.setReplyId(replyModel.getId());
		qae.setWechatId(wechatId);
		replyActionEngineMapper.insert(qae);
		return qae;
	}

	private ReplyActionEngine getReplyActionEngine(Integer wechatId,
			Integer replyActionEngineId) {
		ReplyActionEngine record = new ReplyActionEngine();
		record.setWechatId(wechatId);
		record.setId(replyActionEngineId);
		return replyActionEngineMapper.selectOne(record);
	}

	@Override
	public void delete(Integer wechatId, User user, Integer replyActionEngineId) {
		ReplyActionEngine exist = getReplyActionEngine(wechatId,
				replyActionEngineId);
		notBlank(exist, Message.REPLY_ACTION_ENGINE_NOT_EXIST);
		replyActionEngineMapper.delete(exist);
	}

	@Override
	public Page<ReplyActionEngineDto> search(Integer wechatId,
			ActionEngineModel actionEngineModel, boolean queryCount) {
		if (actionEngineModel.pagable()) {
			PageHelper.startPage(actionEngineModel.getPageNum(),
					actionEngineModel.getPageSize(), queryCount);
		}
		return replyActionEngineMapper
				.search(wechatId, actionEngineModel.getReplyId(),
						actionEngineModel.getSortName(),
						actionEngineModel.getSortDir());
	}

	@Override
	public void update(Integer wechatId, User user, ReplyModel replyModel) {
		ActionEngineModel actionEngineModel = replyModel.getRules();
		notBlank(actionEngineModel.getId(),
				Message.REPLY_ACTION_ENGINE_ID_NOT_BLANK);
		ActionEngine actionEngine = QrcodeActionEngineServiceImpl.getRule(
				wechatId, user, actionEngineModel, new Date());
		ReplyActionEngine replyActionEngine = getReplyActionEngine(wechatId,
				actionEngineModel.getId());
		notBlank(replyActionEngine, Message.REPLY_ACTION_ENGINE_NOT_EXIST);
		ActionEngine exist = actionEngineMapper
				.selectByPrimaryKey(replyActionEngine.getActionEngineId());
		exist.setConditions(actionEngine.getConditions());
		exist.setConditionSql(actionEngine.getConditionSql());
		exist.setEffect(actionEngine.getEffect());
		exist.setEndAt(actionEngine.getEndAt());
		exist.setName(actionEngine.getName());
		exist.setRunType(actionEngine.getRunType());
		exist.setStartAt(actionEngine.getStartAt());
		actionEngineMapper.updateByPrimaryKey(exist);

	}

}
