package com.d1m.wechat.service.impl;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.ReplyDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.ActionEngineMapper;
import com.d1m.wechat.mapper.ReplyActionEngineMapper;
import com.d1m.wechat.mapper.ReplyMapper;
import com.d1m.wechat.mapper.ReplyWordsMapper;
import com.d1m.wechat.model.Reply;
import com.d1m.wechat.model.ReplyWords;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.ReplyMatchMode;
import com.d1m.wechat.model.enums.ReplyStatus;
import com.d1m.wechat.model.enums.ReplyType;
import com.d1m.wechat.pamametermodel.ReplyModel;
import com.d1m.wechat.service.ReplyService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class ReplyServiceImpl extends BaseService<Reply> implements
		ReplyService {

	@Autowired
	private ReplyMapper replyMapper;

	@Autowired
	private ActionEngineMapper actionEngineMapper;

	@Autowired
	private ReplyActionEngineMapper replyActionEngineMapper;
	
	@Autowired
	private ReplyWordsMapper replyWordsMapper;

	public Mapper<Reply> getGenericMapper() {
		return replyMapper;
	}

	@Override
	public Reply create(Integer wechatId, User user, ReplyModel replyModel)
			throws WechatException {
		// List<ActionEngineModel> rules = replyModel.getRules();
		// notBlank(rules, Message.QRCODE_ACTION_ENGINE_NOT_BLANK);

		ReplyMatchMode replyMatchMode = null;
		// ReplyType replyType = null;
		// if (StringUtils.isBlank(replyModel.getName())) {
		// replyType = ReplyType.SUBSCRIBE_REPLY;
		// } else {
		// replyType = ReplyType.KEY_REPLY;
		// }
		// if (replyType == ReplyType.SUBSCRIBE_REPLY) {
		// Reply record = new Reply();
		// record.setReplyType(ReplyType.SUBSCRIBE_REPLY.getValue());
		// record.setStatus(ReplyStatus.INUSED.getValue());
		// record.setWechatId(wechatId);
		// int subscribeAutoReplyCount = replyMapper.selectCount(record);
		// if (subscribeAutoReplyCount > 0) {
		// throw new WechatException(Message.REPLY_SUBSCRIBE_AUTO_EXIST);
		// }
		// } else {
		notBlank(replyModel.getName(), Message.REPLY_NAME_NOT_BLANK);
		notBlank(replyModel.getMatchMode(), Message.REPLY_MATCH_MODE_NOT_BLANK);
		if(replyModel.getKeyWords() == null || replyModel.getKeyWords().isEmpty()){
			throw new WechatException(Message.REPLY_KEYWORDS_NOT_BLANK);
		}
		replyMatchMode = ReplyMatchMode.getByValue(replyModel.getMatchMode());
		if (replyMatchMode == null) {
			throw new WechatException(Message.REPLY_MATCH_MODE_INVALID);
		}
		// }

		Date current = new Date();

		Reply reply = new Reply();
		reply.setCreatedAt(current);
		reply.setCreatorId(user.getId());
		if (replyMatchMode != null) {
			reply.setMatchMode(replyMatchMode.getValue());
		}
		reply.setReplyKey(replyModel.getName());
		reply.setReplyType(ReplyType.KEY_REPLY.getValue());
		reply.setStatus(ReplyStatus.INUSED.getValue());
		reply.setWechatId(wechatId);
		reply.setWeight(replyModel.getWeight());
		replyMapper.insert(reply);
		
		List<String> keyWords = replyModel.getKeyWords();
		List<ReplyWords> replyWordsList = new ArrayList<ReplyWords>();
		for(String keyWord : keyWords){
			ReplyWords replyWords = new ReplyWords();
			replyWords.setReplyId(reply.getId());
			replyWords.setWechatId(wechatId);
			replyWords.setReplyWord(keyWord);
			replyWordsList.add(replyWords);
		}
		replyWordsMapper.insertList(replyWordsList);
		// List<ActionEngine> actionEngines = new ArrayList<ActionEngine>();
		// ActionEngine actionEngine = null;
		// for (ActionEngineModel actionEngineModel : rules) {
		// actionEngine = QrcodeActionEngineServiceImpl.getRule(wechatId,
		// user, actionEngineModel, current);
		// actionEngines.add(actionEngine);
		// }
		// actionEngineMapper.insertList(actionEngines);
		//
		// List<ReplyActionEngine> raes = new ArrayList<ReplyActionEngine>();
		// ReplyActionEngine rae = null;
		// for (ActionEngine ae : actionEngines) {
		// rae = new ReplyActionEngine();
		// rae.setActionEngineId(ae.getId());
		// rae.setCreatedAt(current);
		// rae.setCreatorId(user.getId());
		// rae.setReplyId(reply.getId());
		// rae.setWechatId(wechatId);
		// raes.add(rae);
		// }
		// replyActionEngineMapper.insertList(raes);

		return reply;
	}

	@Override
	public void update(Integer wechatId, User user, ReplyModel replyModel)
			throws WechatException {
		Integer id = replyModel.getId();
		notBlank(id, Message.REPLY_ID_NOT_BLANK);
		notBlank(replyModel.getName(), Message.REPLY_NAME_NOT_BLANK);
		notBlank(replyModel.getMatchMode(), Message.REPLY_MATCH_MODE_NOT_BLANK);
		if(replyModel.getKeyWords() == null || replyModel.getKeyWords().isEmpty()){
			throw new WechatException(Message.REPLY_KEYWORDS_NOT_BLANK);
		}
		ReplyMatchMode replyMatchMode = ReplyMatchMode.getByValue(replyModel
				.getMatchMode());
		notBlank(replyMatchMode, Message.REPLY_MATCH_MODE_INVALID);
		// List<ActionEngineModel> actionEngineModels = replyModel.getRules();
		// notBlank(actionEngineModels, null);
		Reply existReply = getReply(wechatId, id);
		notBlank(existReply, Message.REPLY_NOT_EXIST);
		// List<ActionEngine> actionEngines = new ArrayList<ActionEngine>();
		// ActionEngine actionEngine = null;
		// for (ActionEngineModel actionEngineModel : actionEngineModels) {
		// actionEngine = QrcodeActionEngineServiceImpl.getRule(wechatId,
		// user, actionEngineModel, new Date());
		// actionEngines.add(actionEngine);
		// }

		// ReplyActionEngine record = new ReplyActionEngine();
		// record.setWechatId(wechatId);
		// record.setReplyId(id);
		// List<ReplyActionEngine> existRaes = replyActionEngineMapper
		// .select(record);
		// List<ActionEngine> addActionEngines = new ArrayList<ActionEngine>();
		// List<ActionEngine> updateActionEngines = new
		// ArrayList<ActionEngine>();
		// for (ActionEngine ae : actionEngines) {
		// if (!contains(existRaes, ae)) {
		// addActionEngines.add(ae);
		// } else {
		// updateActionEngines.add(ae);
		// }
		// }
		//
		// List<ReplyActionEngine> delReplyActionEngines = new
		// ArrayList<ReplyActionEngine>();
		// for (ReplyActionEngine existRae : existRaes) {
		// if (!contains(actionEngines, existRae)) {
		// delReplyActionEngines.add(existRae);
		// }
		// }
		//
		// ReplyType replyType = null;
		// ReplyMatchMode replyMatchMode = null;
		// if (StringUtils.isBlank(replyModel.getName())
		// || replyModel.getMatchMode() == null) {
		// replyType = ReplyType.SUBSCRIBE_REPLY;
		// } else {
		// replyType = ReplyType.KEY_REPLY;
		// replyMatchMode = ReplyMatchMode.getByValue(replyModel
		// .getMatchMode());
		// if (replyMatchMode == null) {
		// throw new WechatException(Message.REPLY_MATCH_MODE_INVALID);
		// }
		// }

		existReply.setMatchMode(replyMatchMode.getValue());
		existReply.setReplyKey(replyModel.getName());
		existReply.setWeight(replyModel.getWeight());
		replyMapper.updateByPrimaryKey(existReply);
		
		ReplyWords record = new ReplyWords();
		record.setReplyId(id);
		replyWordsMapper.delete(record);
		
		List<String> keyWords = replyModel.getKeyWords();
		List<ReplyWords> replyWordsList = new ArrayList<ReplyWords>();
		for(String keyWord : keyWords){
			ReplyWords replyWords = new ReplyWords();
			replyWords.setReplyId(id);
			replyWords.setWechatId(wechatId);
			replyWords.setReplyWord(keyWord);
			replyWordsList.add(replyWords);
		}
		replyWordsMapper.insertList(replyWordsList);

		// if (!addActionEngines.isEmpty()) {
		// actionEngineMapper.insertList(addActionEngines);
		// }
		// ReplyActionEngine rae = null;
		// Date current = new Date();
		// List<ReplyActionEngine> raes = new ArrayList<ReplyActionEngine>();
		// for (ActionEngine ae : addActionEngines) {
		// rae = new ReplyActionEngine();
		// rae.setActionEngineId(ae.getId());
		// rae.setCreatedAt(current);
		// rae.setCreatorId(user.getId());
		// rae.setReplyId(existReply.getId());
		// rae.setWechatId(wechatId);
		// raes.add(rae);
		// }
		// if (!raes.isEmpty()) {
		// replyActionEngineMapper.insertList(raes);
		// }
		// for (ActionEngine ae : updateActionEngines) {
		// actionEngineMapper.updateByPrimaryKey(ae);
		// }
		// for (ReplyActionEngine delRae : delReplyActionEngines) {
		// replyActionEngineMapper.delete(delRae);
		// }
	}

	// private boolean contains(List<ReplyActionEngine> list,
	// ActionEngine actionEngine) {
	// if (actionEngine == null) {
	// return false;
	// }
	// for (ReplyActionEngine rae : list) {
	// if (actionEngine.getId() != null
	// && rae.getId().equals(actionEngine.getId())) {
	// return true;
	// }
	// }
	// return false;
	// }
	//
	// private boolean contains(List<ActionEngine> actionEngines,
	// ReplyActionEngine replyActionEngine) {
	// if (actionEngines.isEmpty() || replyActionEngine == null) {
	// return false;
	// }
	// for (ActionEngine ae : actionEngines) {
	// if (ae.getId() != null
	// && ae.getId().equals(replyActionEngine.getId())) {
	// return true;
	// }
	// }
	// return false;
	// }

	private Reply getReply(Integer wechatId, Integer replyId) {
		Reply record = new Reply();
		record.setWechatId(wechatId);
		record.setId(replyId);
		record.setStatus(ReplyStatus.INUSED.getValue());
		return replyMapper.selectOne(record);
	}

	@Override
	public ReplyDto get(Integer wechatId, Integer id) throws WechatException {
		return replyMapper.get(wechatId, id);
	}

	@Override
	public void delete(Integer wechatId, Integer id) throws WechatException {
		notBlank(id, Message.REPLY_ID_NOT_BLANK);
		Reply exist = getReply(wechatId, id);
		notBlank(exist, Message.REPLY_NOT_EXIST);
		exist.setStatus(ReplyStatus.DELETED.getValue());
		replyMapper.updateByPrimaryKey(exist);
		
		ReplyWords replyWords = new ReplyWords();
		replyWords.setReplyId(id);	
		replyWordsMapper.delete(replyWords);
	}

	@Override
	public Page<ReplyDto> search(Integer wechatId, ReplyModel replyModel,
			boolean queryCount) {
		if (replyModel == null) {
			replyModel = new ReplyModel();
		}
		if (replyModel.pagable()) {
			PageHelper.startPage(replyModel.getPageNum(),
					replyModel.getPageSize(), queryCount);
		}
		return replyMapper.search(wechatId, replyModel.getQuery(), 
				replyModel.getSortName(), replyModel.getSortDir());
	}

	@Override
	public ReplyDto getSubscribeReply(Integer wechatId) {
		return replyMapper.getSubscribeReply(wechatId);
	}

	@Override
	public List<ReplyDto> searchMatchReply(Integer wechatId, String content) {
		return replyMapper.searchMatchReply(wechatId, content);
	}

	@Override
	public ReplyDto getDefaultReply(Integer wechatId) {
		return replyMapper.getDefaultReply(wechatId);
	}

}
