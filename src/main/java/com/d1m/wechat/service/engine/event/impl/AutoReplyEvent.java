package com.d1m.wechat.service.engine.event.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.d1m.wechat.model.OriginalConversation;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.anno.EventCode;
import com.d1m.wechat.dto.ReplyActionEngineDto;
import com.d1m.wechat.dto.ReplyDto;
import com.d1m.wechat.model.Conversation;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.MemberReply;
import com.d1m.wechat.model.enums.Effect;
import com.d1m.wechat.model.enums.Event;
import com.d1m.wechat.model.enums.ReplyType;
import com.d1m.wechat.pamametermodel.ActionEngineCondition;
import com.d1m.wechat.service.MemberReplyService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.QrcodeService;
import com.d1m.wechat.service.ReplyService;
import com.d1m.wechat.service.engine.EngineContext;
import com.d1m.wechat.service.engine.effect.IEffect;
import com.d1m.wechat.service.engine.event.IEvent;

/**
 * 关键字自动回复
 */
@EventCode(value = Event.AUTO_REPLY)
@Service
public class AutoReplyEvent implements IEvent {

	private static Logger log = LoggerFactory.getLogger(AutoReplyEvent.class);

	@Autowired
	private QrcodeService qrcodeService;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private MemberService memberService;
	
	@Autowired
	private MemberReplyService memberReplyService;

	@Override
	public Map<String, Object> handle(Conversation conversation, Member member) {
		Integer wechatId = conversation.getWechatId();
		log.info("conversation wechatId : {}, content : {}", wechatId,
				conversation.getContent());
		if (StringUtils.isBlank(conversation.getContent())) {
			log.info("content is empty.");
			return null;
		}

		List<ReplyDto> replys = replyService.searchMatchReply(wechatId,
				conversation.getContent());
		log.info("replys match size : {}  wechatId : {}", replys.size(),wechatId);
		ReplyDto replyDto = null;
		if (replys == null || replys.isEmpty()) {
			replyDto = replyService.getDefaultReply(wechatId);
		}else{
			replyDto = replys.get(0);
		}
		if(null==replyDto){
			log.info("reply no match. wechatId : {}",wechatId);
			return null;
		}
		if(replyDto.getReplyType() == ReplyType.DEFAULT_REPLY.getValue() 
				&& replyDto.getRules().isEmpty()){
			log.info("default reply has no rules");
			return null;
		}
		
		log.info("reply id {} match. wechatId : {}", replyDto.getId(),wechatId);

		// 记录用户自动回复匹配结果
		MemberReply entity = new MemberReply();
		entity.setMemberId(member.getId());
		entity.setReplyId(replyDto.getReplyType() == ReplyType.DEFAULT_REPLY.getValue()?null:replyDto.getId());
		entity.setConversationId(conversation.getId());
		entity.setWechatId(wechatId);
		entity.setCreatedAt(new Date());
		memberReplyService.save(entity);
		
		List<Member> members = new ArrayList<Member>();

		String effect = null;
		Date current = new Date();
		List<ReplyActionEngineDto> replyActionEngineDtos = replyDto.getRules();
		for (ReplyActionEngineDto replyActionEngineDto : replyActionEngineDtos) {
			log.info("replyActionEngine id : {}", replyActionEngineDto.getId());
			effect = replyActionEngineDto.getEffect();
			log.info("effect : {}", effect);
			if (StringUtils.isBlank(effect)) {
				log.info("wechatId : {}, actionEngine id : {} no effect.",
						wechatId, replyActionEngineDto.getActionEngineId());
				continue;
			}
			if (replyActionEngineDto.getStart_at() != null
					&& replyActionEngineDto.getEnd_at() != null) {
				if (current.compareTo(replyActionEngineDto.getStart_at()) < 0
						|| current.compareTo(replyActionEngineDto.getEnd_at()) > 0) {
					log.info(
							"start : {}, end : {}, current : {}, time not match.",
							replyActionEngineDto.getStart_at(),
							replyActionEngineDto.getEnd_at(), current);
					continue;
				}
			} else if (replyActionEngineDto.getStart_at() != null) {
				if (current.compareTo(replyActionEngineDto.getStart_at()) < 0) {
					log.info("start : {}, current : {}, time not match.",
							replyActionEngineDto.getStart_at(), current);
					continue;
				}
			} else if (replyActionEngineDto.getEnd_at() != null) {
				if (current.compareTo(replyActionEngineDto.getEnd_at()) > 0) {
					log.info("end : {}, current : {}, time not match.",
							replyActionEngineDto.getEnd_at(), current);
					continue;
				}
			}
			ActionEngineCondition condition = null;
			log.info("conditions : {}", replyActionEngineDto.getConditions());
			if (StringUtils.isNotBlank(replyActionEngineDto.getConditions())) {
				condition = JSONObject.parseObject(
						replyActionEngineDto.getConditions(),
						ActionEngineCondition.class);
			}
			if (!ScanQrcodeEvent.matchCondition(member, condition)) {
				log.info(
						"wechatId : {}, actionEngine id : {}, memberId : {} not match condition.",
						wechatId, replyActionEngineDto.getActionEngineId(),
						member.getId());
				continue;
			}
			if (members.isEmpty()) {
				members.add(member);
			}

			JSONArray effectArray = JSONArray.parseArray(effect);
			JSONObject effectJson = null;
			Integer effectCode = null;
			Effect et = null;
			for (int i = 0; i < effectArray.size(); i++) {
				effectJson = effectArray.getJSONObject(i);
				effectCode = effectJson.getInteger("code");
				et = Effect.getValueByValue(effectCode.byteValue());
				IEffect iEffect = EngineContext.getEffectMaps(et);
				log.info("effect : {}, effectEngine : {}", et, iEffect);
				if (iEffect != null) {
					Map<String, Object> map = new HashMap<String, Object>();
					map.put("wechatId", wechatId);
					map.put("members", members);
					map.put("effectJson", effectJson);
					iEffect.handle(map);
				}
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> handleSystem(OriginalConversation originalConversation, Document document) {
		return null;
	}

}
