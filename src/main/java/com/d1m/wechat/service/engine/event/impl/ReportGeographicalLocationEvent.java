package com.d1m.wechat.service.engine.event.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.d1m.wechat.model.OriginalConversation;
import org.apache.commons.lang.StringUtils;
import org.dom4j.Document;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.anno.EventCode;
import com.d1m.wechat.model.Conversation;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.enums.Effect;
import com.d1m.wechat.model.enums.Event;
import com.d1m.wechat.service.ConfigService;
import com.d1m.wechat.service.engine.EngineContext;
import com.d1m.wechat.service.engine.effect.IEffect;
import com.d1m.wechat.service.engine.event.IEvent;

/**
 * 上报地理位置
 */
@EventCode(value = Event.LOCATION)
@Service
public class ReportGeographicalLocationEvent implements IEvent {
	@Autowired
	private ConfigService configService;
	
	@Override
	public Map<String, Object> handle(Conversation conversation, Member member) {
		Integer wechatId = conversation.getWechatId();
		// 获取接口处理类，多个以逗号分隔
		String apiClasses = configService.getConfigValue(wechatId, "LBS", "LOCATION_API_CLASS");
		if(StringUtils.isNotBlank(apiClasses)){
			String[] strs = StringUtils.split(apiClasses,",");
			IEffect iEffect = EngineContext.getEffectMaps(Effect.API);
			if (iEffect != null) {
				Map<String, Object> map = new HashMap<String, Object>();
				map.put("conversation", conversation);
				map.put("wechatId", wechatId);
				List<Member> members = new ArrayList<Member>();
				members.add(member);
				map.put("members", members);
				JSONObject json = new JSONObject();
				JSONArray array = new JSONArray();
				for(String clazz:strs){
					array.add(clazz);
				}
				json.put("value", array);
				map.put("effectJson", json);
				iEffect.handle(map);
			}
		}
		return null;
	}

	@Override
	public Map<String, Object> handleSystem(OriginalConversation originalConversation, Document document) {
		return null;
	}

}
