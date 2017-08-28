package com.d1m.wechat.service.engine.effect.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.anno.EffectCode;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.MemberMemberTag;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.enums.Effect;
import com.d1m.wechat.service.MemberMemberTagService;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.service.engine.effect.IEffect;

@EffectCode(value = Effect.ADD_MEMBER_TAG)
@Service
public class AddMemberTagEffect implements IEffect {

	private static Logger log = LoggerFactory
			.getLogger(AddMemberTagEffect.class);

	@Autowired
	private MemberMemberTagService memberMemberTagService;

	@Autowired
	private MemberTagService memberTagService;

	@SuppressWarnings("unchecked")
	@Override
	public void handle(Map<String, Object> map) {
		Integer wechatId = (Integer) map.get("wechatId");
		List<Member> members = (List<Member>) map.get("members");
		JSONObject effectJson = (JSONObject) map.get("effectJson");
		JSONArray value = effectJson.getJSONArray("value");
		MemberTag memberTag = null;
		MemberMemberTag mmt = null;
		Integer memberTagId = null;
		Date current = new Date();
		for (int i = 0; i < value.size(); i++) {
			memberTagId = value.getInteger(i);
			log.info("memberTagId : {}", memberTagId);
			for (Member member : members) {
				log.info("member id : {}", member.getId());
				memberTag = memberTagService.get(wechatId, memberTagId);
				if (memberTag == null) {
					log.error("wechatId : {}, memberTag id : {} not found.",
							wechatId, memberTagId);
					continue;
				}
				mmt = memberMemberTagService.get(wechatId, member.getId(),
						memberTagId);
				log.info("member_member_tag get : {}", mmt);
				if (mmt != null) {
					log.info(
							"wechat id : {}, member id : {}, memberTag id : {} already exist.",
							wechatId, member.getId(), memberTagId);
					continue;
				}
				mmt = new MemberMemberTag();
				mmt.setMemberId(member.getId());
				mmt.setMemberTagId(memberTagId);
				mmt.setWechatId(wechatId);
				mmt.setCreatedAt(current);
				memberMemberTagService.save(mmt);
			}
		}
	}
}
