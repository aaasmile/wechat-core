package com.d1m.wechat.service.engine.event.impl;

import java.util.*;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d1m.wechat.anno.EventCode;
import com.d1m.wechat.dto.ReplyActionEngineDto;
import com.d1m.wechat.dto.ReplyDto;
import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.*;
import com.d1m.wechat.pamametermodel.ActionEngineCondition;
import com.d1m.wechat.service.*;
import com.d1m.wechat.service.engine.EngineContext;
import com.d1m.wechat.service.engine.effect.IEffect;
import com.d1m.wechat.service.engine.event.IEvent;

/**
 * 关注
 */
@EventCode(value = Event.SUBSCRIBE)
@Service
public class SubscribeEvent implements IEvent {

	private static Logger log = LoggerFactory.getLogger(SubscribeEvent.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberSubscribeService memberSubscribeService;

	@Autowired
	private ReplyService replyService;

	@Autowired
	private QrcodeService qrcodeService;

	@Autowired
	private OriginalConversationService originalConversationService;

	@Autowired
	private WechatService wechatService;

	@Autowired
	private MemberScanQrcodeService memberScanQrcodeService;

	public static String QRCODE_SUBSCRIBE_XML = "<xml><ToUserName><![CDATA[%s]]></ToUserName><FromUserName><![CDATA[%s]]></FromUserName><CreateTime>%s</CreateTime><MsgType><![CDATA[event]]></MsgType><Event><![CDATA[SCAN]]></Event><EventKey><![CDATA[%s]]></EventKey><Ticket><![CDATA[%s]]></Ticket></xml>";

	@Override
	public Map<String, Object> handle(Conversation conversation, Member member) {
		log.info("SubscribeEvent handle.");
		Integer wechatId = conversation.getWechatId();
		MemberSource memberSource = null;
		Qrcode qrcode = null;
		if (StringUtils.isBlank(conversation.getEventKey())) {
			memberSource = MemberSource.WECHAT_SEARCH;
		} else {
			memberSource = MemberSource.QRCODE;
			String eventKey = conversation.getEventKey();
			if(StringUtils.isNotBlank(eventKey)){
				eventKey = eventKey.replaceAll("qrscene_","");
				qrcode = qrcodeService.getBySceneId(wechatId,eventKey);
			}
		}

        // 异步更新微信用户信息
        memberService.updateWxuser(member,qrcode,memberSource);

		// 增加订阅记录
		Date current = new Date();
		log.info("member : {}", (member != null ? member.getId() : null));
		MemberSubscribe memberSubscribe = new MemberSubscribe();
		memberSubscribe.setCreatedAt(member.getCreatedAt());
		memberSubscribe.setMemberId(member.getId());
		memberSubscribe.setSubscribeBy(memberSource.getValue());
		memberSubscribe.setSubscribe(Subscribe.SUBSCRIBE.getValue());
		memberSubscribe.setSubscribeAt(current);
		memberSubscribe.setWechatId(wechatId);
		if (qrcode != null) {
			memberSubscribe.setQrcodeId(qrcode.getId());
		}
		memberSubscribeService.save(memberSubscribe);
		log.info("memberSubscribe create id : {}", memberSubscribe.getId());

		Map<String, Object> map = new HashMap<String, Object>();
		map.put("member", member);

		/**
		 * 当用户关注时模拟一条扫码交互数据,此时的扫码模拟消息 设置ticket为SUBSCRIBE,以区分正常微信交互.
		 * 当二维码不支持扫描关注时,直接生成一条会员扫描记录,此时设置扫描时用户关注状态为false.
		 */
		if (qrcode != null){
			if(qrcode.getSopportSubscribeReply() != null && qrcode.getSopportSubscribeReply() == 1) {
				Wechat wechat = wechatService.getById(wechatId);
				String qrcodeSubscribeXml = String.format(QRCODE_SUBSCRIBE_XML,
						wechat.getOpenId(), conversation.getOpenId(), conversation
								.getCreatedAt().getTime() / 1000 + 1,
						qrcode.getScene(), "SUBSCRIBE");
				log.info("create QrcodeSubscribeXml : {}", qrcodeSubscribeXml);
				try {
					Document document = DocumentHelper
							.parseText(qrcodeSubscribeXml);
					OriginalConversation originalConversation = new OriginalConversation();
					originalConversation.setContent(document.asXML());
					originalConversation.setCreatedAt(new Date());
					originalConversation.setStatus(OriginalConversationStatus.WAIT
							.getValue());
					originalConversation.setWechatId(wechatId);
					originalConversationService.save(originalConversation);
                    originalConversationService.execute(member,document,originalConversation);
					log.info("QrcodeSubscribeXml add to originalConversation.");
				} catch (DocumentException e) {
					log.error(e.getLocalizedMessage());
				}
			}else{
				MemberScanQrcode memberScanQrcode = new MemberScanQrcode();
				memberScanQrcode.setCreatedAt(new Date());
				memberScanQrcode.setIsSubscribe(false);
				memberScanQrcode.setMemberId(member.getId());
				memberScanQrcode.setOpenId(member.getOpenId());
				memberScanQrcode.setQrcodeId(qrcode.getId());
				memberScanQrcode.setUnionId(member.getUnionId());
				memberScanQrcode.setWechatId(member.getWechatId());
				memberScanQrcodeService.save(memberScanQrcode);
			}
		}

		// 非二维码关注或关闭二维码关注回复时启用默认关注回复
		if(qrcode==null||(qrcode!=null&&(qrcode.getSopportSubscribeReply() == null || qrcode.getSopportSubscribeReply() == 0))){
			ReplyDto subscribeReply = replyService.getSubscribeReply(wechatId);
			if (subscribeReply == null) {
				log.warn("wechatId : {}, no subscribe reply action engine.",
						conversation.getWechatId());
				return map;
			}
			List<Member> members = new ArrayList<Member>();
			members.add(member);
			Integer actionEngineId = null;
			List<ReplyActionEngineDto> rules = subscribeReply.getRules();
			if (rules == null || rules.isEmpty()) {
				log.error(
						"wechatId : {}, subscribeReply id : {} has no rules.",
						wechatId, subscribeReply.getId());
				return map;
			}
			String effect = null;
			for (ReplyActionEngineDto replyActionEngineDto : rules) {
				effect = replyActionEngineDto.getEffect();
				log.info("effect : {}", effect);
				if (StringUtils.isBlank(effect)) {
					log.info("wechatId : {}, actionEngine id : {} no effect.",
							conversation.getWechatId(),
							replyActionEngineDto.getActionEngineId());
					continue;
				}
				if (replyActionEngineDto.getStart_at() != null
						&& replyActionEngineDto.getEnd_at() != null) {
					if (current.compareTo(replyActionEngineDto.getStart_at()) < 0
							|| current.compareTo(replyActionEngineDto
									.getEnd_at()) > 0) {
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
				log.info("conditions : {}",
						replyActionEngineDto.getConditions());
				if (StringUtils
						.isNotBlank(replyActionEngineDto.getConditions())) {
					condition = JSONObject.parseObject(
							replyActionEngineDto.getConditions(),
							ActionEngineCondition.class);
				}
				if (!ScanQrcodeEvent.matchCondition(member, condition)) {
					log.info(
							"wechatId : {}, actionEngine id : {}, memberId : {} not match condition.",
							conversation.getWechatId(), actionEngineId,
							member.getId());
					continue;
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
						Map<String, Object> effectMap = new HashMap<String, Object>();
						effectMap.put("wechatId", wechatId);
						effectMap.put("members", members);
						effectMap.put("effectJson", effectJson);
						iEffect.handle(effectMap);
					}
				}
			}
		}


		log.info("SubscribeEvent handle end.");
		return map;
	}

	@Override
	public Map<String, Object> handleSystem(OriginalConversation originalConversation, Document document) {
		return null;
	}

}
