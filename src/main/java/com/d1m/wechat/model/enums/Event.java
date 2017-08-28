package com.d1m.wechat.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * 事件类型
 */
public enum Event {

	SUBSCRIBE((byte) 1, "关注", false, true),

	UNSUBSCRIBE((byte) 2, "取消关注", false, false),

	SCAN((byte) 3, "扫描带参数二维码", false, true),

	LOCATION((byte) 4, "上报地理位置", false, false),

	CLICK((byte) 5, "点击菜单拉取消息", false, true),

	VIEW((byte) 6, "点击菜单跳转链接", false, true),

	POI_CHECK_NOTIFY((byte) 7, "门店审核", true, false),

	MASSSENDJOBFINISH((byte) 8, "群发消息结果", true, false),

	AUTO_REPLY((byte) 9, "关键字自动回复", false, false),
	
	SCANCODE_PUSH((byte) 10, "扫码推事件", false, true),
	
	SCANCODE_WAITMSG((byte) 11, "扫码推事件且弹出“消息接收中”提示框", false, true),
	
	TEMPLATESENDJOBFINISH((byte)12, "模板消息结果", true, false),

	CARD_PASS_CHECK((byte)13, "卡券审核通过", true, false),

	CARD_NOT_PASS_CHECK((byte)14, "卡券未审核通过", true, false),

	USER_GET_CARD((byte)15, "用户领券", false, false),

	USER_GIFTING_CARD((byte)16, "卡券转赠", false, false),

	USER_DEL_CARD((byte)17, "删除卡券", false, false),

	USER_CONSUME_CARD((byte)18, "核销卡券", false, false),

	USER_PAY_FROM_PAY_CELL((byte)19, "用户买单", false, false),

	USER_ENTER_SESSION_FROM_CARD((byte)20, "用户从卡券进入公众号", false, false),

	CARD_SKU_REMIND((byte)21, "库存警告", true, false),

	CONVERSATION_TRANSFER((byte)99, "会话转接", true, false),

	;

	private byte value;

	private String name;
	// 是否系统事件
	private boolean isSystem;
	// 是否算交互
	private boolean isInteractive;

	/**
	 * @param value
	 * @param name
	 */
	private Event(byte value, String name, boolean isSystem, boolean isInteractive) {
		this.value = value;
		this.name = name;
		this.isSystem = isSystem;
		this.isInteractive = isInteractive;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public boolean isSystem() {
		return isSystem;
	}

	public void setSystem(boolean system) {
		isSystem = system;
	}

	public boolean isInteractive() {
		return isInteractive;
	}

	public void setInteractive(boolean interactive) {
		isInteractive = interactive;
	}

	public static Event getValueByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (Event event : Event.values()) {
			if (event.name().equalsIgnoreCase(name)) {
				return event;
			}
		}
		return null;
	}
}
