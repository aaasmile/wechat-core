package com.d1m.wechat.service.engine.event.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.d1m.wechat.model.*;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.anno.EventCode;
import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.dto.QrcodeActionEngineDto;
import com.d1m.wechat.model.enums.Effect;
import com.d1m.wechat.model.enums.Event;
import com.d1m.wechat.model.enums.Language;
import com.d1m.wechat.pamametermodel.ActionEngineCondition;
import com.d1m.wechat.pamametermodel.ActionEngineModel;
import com.d1m.wechat.service.ActionEngineService;
import com.d1m.wechat.service.MemberScanQrcodeService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.QrcodeActionEngineService;
import com.d1m.wechat.service.QrcodeService;
import com.d1m.wechat.service.engine.EngineContext;
import com.d1m.wechat.service.engine.effect.IEffect;
import com.d1m.wechat.service.engine.event.IEvent;
import com.d1m.wechat.util.AppContextUtils;
import com.github.pagehelper.Page;

/**
 * 扫描带参数二维码
 */
@EventCode(value = Event.SCAN)
@Service
public class ScanQrcodeEvent implements IEvent {

	private static Logger log = LoggerFactory.getLogger(ScanQrcodeEvent.class);

	private static Boolean IS_SUBSCRIBE = null;

	@Autowired
	private MemberService memberService;

	@Autowired
	private QrcodeService qrcodeService;

	@Autowired
	private QrcodeActionEngineService qrcodeActionEngineService;

	@Autowired
	private ActionEngineService actionEngineService;

	@Autowired
	private MemberScanQrcodeService memberScanQrcodeService;

	@Override
	public Map<String, Object> handle(Conversation conversation, Member member) {

		log.info("conversation wechatId : {}, eventkey : {}",
				conversation.getWechatId(), conversation.getEventKey());
		Qrcode qrcode = qrcodeService.getBySceneId(conversation.getWechatId(),
				conversation.getEventKey());
		if (qrcode == null) {
			log.error("wechatId : {},qrcode eventkey : {} not found.",
					conversation.getWechatId(), conversation.getEventKey());
			return null;
		}
		log.info("qrcode id : {}, member id : {}", qrcode.getId(),
				conversation.getMemberId());


		MemberScanQrcode memberScanQrcode = new MemberScanQrcode();
		memberScanQrcode.setCreatedAt(new Date());
		//当ticket 为SUBSCRIBE时表示模拟数据
		memberScanQrcode.setIsSubscribe(true);
		IS_SUBSCRIBE = true;
		if("SUBSCRIBE".equals(conversation.getTicket())){
			memberScanQrcode.setIsSubscribe(false);
			IS_SUBSCRIBE = false;
		}
		memberScanQrcode.setMemberId(member.getId());
		memberScanQrcode.setOpenId(member.getOpenId());
		memberScanQrcode.setQrcodeId(qrcode.getId());
		memberScanQrcode.setUnionId(member.getUnionId());
		memberScanQrcode.setWechatId(member.getWechatId());
		memberScanQrcodeService.save(memberScanQrcode);

		ActionEngineModel qae = new ActionEngineModel();
		Integer wechatId = qrcode.getWechatId();
		qae.setQrcodeId(qrcode.getId());
		Page<QrcodeActionEngineDto> page = qrcodeActionEngineService.search(
				wechatId, qae, false);
		if (page.isEmpty()) {
			log.warn("wechatId : {},qrcode id {} no action engine.",
					conversation.getWechatId(), qrcode.getId());
			return null;
		}
		ActionEngine actionEngine = null;
		List<Member> members = new ArrayList<Member>();
		String effect = null;
		Date current = new Date();
		List<QrcodeActionEngineDto> qrcodeActionEngineDtos = page.getResult();
		Integer actionEngineId = null;
		for (QrcodeActionEngineDto qrcodeActionEngineDto : qrcodeActionEngineDtos) {
			log.info("qrcodeActionEngine id : {}",
					qrcodeActionEngineDto.getId());
			actionEngineId = qrcodeActionEngineDto.getActionEngineId();
			actionEngine = actionEngineService.get(wechatId, actionEngineId);
			if (actionEngine == null) {
				log.error("wechatId : {}, actionEngine id : {} not found.",
						conversation.getWechatId(), actionEngineId);
				continue;
			}
			effect = actionEngine.getEffect();
			log.info("effect : {}", effect);
			if (StringUtils.isBlank(effect)) {
				log.info("wechatId : {}, actionEngine id : {} no effect.",
						conversation.getWechatId(), actionEngineId);
				continue;
			}
			if (actionEngine.getStartAt() != null
					&& actionEngine.getEndAt() != null) {
				if (current.compareTo(actionEngine.getStartAt()) < 0
						|| current.compareTo(actionEngine.getEndAt()) > 0) {
					log.info(
							"start : {}, end : {}, current : {}, time not match.",
							actionEngine.getStartAt(), actionEngine.getEndAt(),
							current);
					continue;
				}
			} else if (actionEngine.getStartAt() != null) {
				if (current.compareTo(actionEngine.getStartAt()) < 0) {
					log.info("start : {}, current : {}, time not match.",
							actionEngine.getStartAt(), current);
					continue;
				}
			} else if (actionEngine.getEndAt() != null) {
				if (current.compareTo(actionEngine.getEndAt()) > 0) {
					log.info("end : {}, current : {}, time not match.",
							actionEngine.getEndAt(), current);
					continue;
				}
			}
			ActionEngineCondition condition = null;
			log.info("conditions : {}", actionEngine.getConditions());
			if (StringUtils.isNotBlank(actionEngine.getConditions())) {
				condition = JSONObject.parseObject(
						actionEngine.getConditions(),
						ActionEngineCondition.class);
			}
			if (!matchCondition(member, condition)) {
				log.info(
						"wechatId : {}, actionEngine id : {}, memberId : {} not match condition.",
						conversation.getWechatId(), actionEngineId,
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

	public static boolean matchCondition(Member member,
			ActionEngineCondition condition) {
		if (condition == null) {
			return true;
		}
		if (condition.getDateOfBirthStart() != null) {
			// TODO
		}
		if (condition.getDateOfBirthEnd() != null) {
			// TODO
		}
		if (condition.getGender() != null && condition.getGender().length > 0) {
			if (member.getSex() == null) {
				log.info("member sex is null, condition gender is not null.");
				return false;
			}
			if (!Arrays.asList(condition.getGender()).contains(
					member.getSex().intValue())) {
				log.info("member sex is not in condition gender.");
				return false;
			}
		}
		if (condition.getHasMobile() != null) {
			if (StringUtils.isBlank(member.getMobile())) {
				log.info("member mobile is null, condition hasMobile is not null.");
				return false;
			}
		}
		if (condition.getIsFollowed() != null) {
			if (!condition.getIsFollowed().equals(IS_SUBSCRIBE)) {
				log.info("member subscribe is null, condition subscribe is not equal.");
				return false;
			}
		}
		if (condition.getLanguage() != null
				&& condition.getLanguage().length > 0) {
			if (member.getLanguage() == null) {
				log.info("member language is null, condition language is not null.");
				return false;
			}
			Language language = Language.getByValue(member.getLanguage());
			if (language == null) {
				return false;
			}
			if (!Arrays.asList(condition.getLanguage()).contains(
					language.getValue())) {
				log.info("member language is not in condition languages.");
				return false;
			}
		}
		if (condition.getMobileStatus() != null) {
			// TODO
		}
		Integer[] memberTags = condition.getMemberTagIds();
		if (memberTags != null && memberTags.length > 0) {
			MemberService memberService = AppContextUtils
					.getBean(MemberService.class);
			List<MemberTagDto> memberMemberTags = memberService
					.getMemberMemberTag(member.getWechatId(), member.getId());
			boolean containAtLeastOne = false;
			if (memberMemberTags != null && memberMemberTags.size() > 0) {
				for (Integer memberTagId : memberTags) {
					if (contains(memberMemberTags, memberTagId)) {
						containAtLeastOne = true;
						break;
					}
				}
			}
			if (!containAtLeastOne) {
				log.info("member tags don't contains at least one condition membertag.");
				return false;
			}
		}
		return true;
	}

	private static boolean contains(List<MemberTagDto> memberMemberTags,
			Integer memberTagId) {
		for (MemberTagDto memberTagDto : memberMemberTags) {
			if (memberTagDto.getId().equals(memberTagId)) {
				return true;
			}
		}
		return false;
	}
}
