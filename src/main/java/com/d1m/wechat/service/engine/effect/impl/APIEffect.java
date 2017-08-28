package com.d1m.wechat.service.engine.effect.impl;

import java.util.List;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.anno.EffectCode;
import com.d1m.wechat.model.Conversation;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.enums.Effect;
import com.d1m.wechat.service.engine.api.IApi;
import com.d1m.wechat.service.engine.effect.IEffect;
import com.d1m.wechat.util.AppContextUtils;

@EffectCode(value = Effect.API)
@Service
public class APIEffect implements IEffect {

	private static Logger log = LoggerFactory.getLogger(APIEffect.class);

	@SuppressWarnings("unchecked")
	@Override
	public void handle(Map<String, Object> map) {
		Conversation conversation = (Conversation) map.get("conversation");
		Integer wechatId = (Integer) map.get("wechatId");
		List<Member> members = (List<Member>) map.get("members");
		JSONObject effectJson = (JSONObject) map.get("effectJson");
		JSONArray value = effectJson.getJSONArray("value");
		if(value!=null&&value.size()>0){
			for (int i = 0; i < value.size(); i++) {
				String apiClass = value.getString(i);
				log.info("api class : {}", apiClass);
				try {
					Class<?> clazz = Class.forName(apiClass);
					Object object = AppContextUtils.getBean(clazz);
					if(object == null){
						object = clazz.newInstance();
					}
					if(object instanceof IApi){
						((IApi) object).handle(wechatId, conversation, members);
					}else{
						log.error("ApiBean must implements IApi!");
					}
				} catch (Exception e) {
					log.error("ApiBean must be set!");
				}
			}
		}
	}
}
