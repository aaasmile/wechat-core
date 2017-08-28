package com.d1m.wechat.service.engine.effect.impl;

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
import com.d1m.wechat.model.enums.Effect;
import com.d1m.wechat.pamametermodel.ConversationModel;
import com.d1m.wechat.service.ConversationService;
import com.d1m.wechat.service.engine.effect.IEffect;

@EffectCode(value = Effect.SEND_IMAGE)
@Service
public class SendImageEffect implements IEffect {

	private static Logger log = LoggerFactory.getLogger(SendImageEffect.class);

	@Autowired
	private ConversationService conversationService;

	@SuppressWarnings("unchecked")
	@Override
	public void handle(Map<String, Object> map) {
		Integer wechatId = (Integer) map.get("wechatId");
		List<Member> members = (List<Member>) map.get("members");
		JSONObject effectJson = (JSONObject) map.get("effectJson");
		JSONArray value = effectJson.getJSONArray("value");
		Integer materialId = null;
		ConversationModel conversationModel = null;
		for (int i = 0; i < value.size(); i++) {
			materialId = value.getInteger(i);
			log.info("materialId : {}", materialId);
			conversationModel = new ConversationModel();
			conversationModel.setMaterialId(materialId);
			for (Member member : members) {
				log.info("member id : {}", member.getId());
				conversationModel.setMemberId(member.getId());
				conversationService.wechatToMember(wechatId, null,
						conversationModel);
			}
		}
	}

}
