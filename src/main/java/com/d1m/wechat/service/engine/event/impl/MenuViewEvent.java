package com.d1m.wechat.service.engine.event.impl;

import java.util.Date;
import java.util.Map;

import com.d1m.wechat.model.*;
import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d1m.wechat.anno.EventCode;
import com.d1m.wechat.model.enums.Event;
import com.d1m.wechat.service.MemberClickMenuService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.MenuService;
import com.d1m.wechat.service.engine.event.IEvent;

/**
 * 点击菜单跳转链接
 */
@EventCode(value = Event.VIEW)
@Service
public class MenuViewEvent implements IEvent {

	private static Logger log = LoggerFactory.getLogger(MenuViewEvent.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberClickMenuService memberClickMenuService;

	@Autowired
	private MenuService menuService;

	@Override
	public Map<String, Object> handle(Conversation conversation, Member member) {
		Integer wechatId = conversation.getWechatId();
		String eventKey = conversation.getEventKey();
		Integer menuId = conversation.getMenuId();
		
		log.info("get conversation menuId: {}", menuId);
		Menu menu = menuService.getByUrl(wechatId, eventKey, menuId);
		
		if (menu == null) {
			log.warn("wechatId : {},menu id {} not exist.",
					conversation.getWechatId(), eventKey);
			return null;
		}
		log.info("get menu id : {}", menu.getId());
		MemberClickMenu memberClickMenu = new MemberClickMenu();
		memberClickMenu.setCreatedAt(new Date());
		memberClickMenu.setMemberId(member.getId());
		memberClickMenu.setMenuGroupId(menu.getMenuGroupId());
		memberClickMenu.setMenuId(menu.getId());
		memberClickMenu.setOpenId(member.getOpenId());
		memberClickMenu.setUnionId(member.getUnionId());
		memberClickMenu.setWechatId(wechatId);
		memberClickMenuService.save(memberClickMenu);

		return null;
	}

	@Override
	public Map<String, Object> handleSystem(OriginalConversation originalConversation, Document document) {
		return null;
	}

}
