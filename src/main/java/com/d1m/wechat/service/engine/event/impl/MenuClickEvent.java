package com.d1m.wechat.service.engine.event.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.d1m.wechat.model.*;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.anno.EventCode;
import com.d1m.wechat.model.enums.Effect;
import com.d1m.wechat.model.enums.Event;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.service.MaterialTextDetailService;
import com.d1m.wechat.service.MemberClickMenuService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.MenuService;
import com.d1m.wechat.service.engine.EngineContext;
import com.d1m.wechat.service.engine.effect.IEffect;
import com.d1m.wechat.service.engine.event.IEvent;
import com.d1m.wechat.util.ParamUtil;

/**
 * 点击菜单拉取消息
 */
@EventCode(value = Event.CLICK)
@Service
public class MenuClickEvent implements IEvent {

	private static Logger log = LoggerFactory.getLogger(MenuClickEvent.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberClickMenuService memberClickMenuService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private MaterialService materialService;
	
	@Autowired
	private MaterialTextDetailService materialTextDetailService;

	@Override
	public Map<String, Object> handle(Conversation conversation, Member member) {
		Integer wechatId = conversation.getWechatId();
		String eventKey = conversation.getEventKey();
		Menu menu = menuService.get(wechatId, ParamUtil.getInt(eventKey, null));
		if (menu == null) {
			log.warn("wechatId : {},menu id {} not exist.",
					conversation.getWechatId(), eventKey);
			return null;
		}

		MemberClickMenu memberClickMenu = new MemberClickMenu();
		memberClickMenu.setCreatedAt(new Date());
		memberClickMenu.setMemberId(member.getId());
		memberClickMenu.setMenuGroupId(menu.getMenuGroupId());
		memberClickMenu.setMenuId(menu.getId());
		memberClickMenu.setOpenId(member.getOpenId());
		memberClickMenu.setUnionId(member.getUnionId());
		memberClickMenu.setWechatId(wechatId);
		memberClickMenuService.save(memberClickMenu);

		if (menu.getMenuKey() == null) {
			log.warn("wechatId : {}, menuId : {} menukey is empty.", wechatId,
					eventKey);
			return null;
		}
		Material material = materialService.getMaterial(wechatId,
				menu.getMenuKey());
		if (material == null) {
			log.warn("wechatId : {}, menuId : {}, materialId : {} is empty.",
					wechatId, eventKey, menu.getMenuKey());
			return null;
		}
		IEffect iEffect = EngineContext.getEffectMaps(Effect.SEND_IMAGE_TEXT);
		if (iEffect != null) {
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("wechatId", wechatId);
			List<Member> members = new ArrayList<Member>();
			members.add(member);
			map.put("members", members);
			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();
			array.add(material.getId());
			json.put("value", array);
			map.put("effectJson", json);
			iEffect.handle(map);
		}
		IEffect iEffectApi = EngineContext.getEffectMaps(Effect.API);
		if (iEffectApi != null) {
			if(material.getMaterialType()!=7){
				return null;
			}
			Map<String, Object> map = new HashMap<String, Object>();
			map.put("wechatId", wechatId);
			List<Member> members = new ArrayList<Member>();
			members.add(member);
			map.put("members", members);
			JSONObject json = new JSONObject();
			JSONArray array = new JSONArray();
			MaterialTextDetail materialTextDetail = materialTextDetailService.search(wechatId, 
					material.getId());
			if(materialTextDetail != null){
				array.add(materialTextDetail.getContent());
			}else{
				return null;
			}
			json.put("value", array);
			map.put("effectJson", json);
			iEffectApi.handle(map);
		}
		return null;
	}

	@Override
	public Map<String, Object> handleSystem(OriginalConversation originalConversation, Document document) {
		return null;
	}

}
