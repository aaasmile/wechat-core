package com.d1m.wechat.model.enums;

import org.apache.commons.lang.StringUtils;

public enum Effect {

	ADD_MEMBER_TAG((byte) 200, "会员加标签"),

	SEND_IMAGE_TEXT((byte) 201, "发送图文"),

	SEND_IMAGE((byte) 202, "发送图片"),

	SEND_TEXT((byte) 203, "发送文本"),

	API((byte) 204, "调用API"),

	SEND_MINI_PROGRAM((byte) 205, "发送小程序"),

	SEND_DCRM_IMAGE_TEXT((byte) 301, "发送图文"),
	;

	private byte value;

	private String name;

	/**
	 * @param value
	 * @param name
	 */
	private Effect(byte value, String name) {
		this.value = value;
		this.name = name;
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

	public static Effect getValueByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (Effect effect : Effect.values()) {
			if (effect.name().equalsIgnoreCase(name)) {
				return effect;
			}
		}
		return null;
	}

	public static Effect getValueByValue(Byte value) {
		if (value == null) {
			return null;
		}
		for (Effect effect : Effect.values()) {
			if (value.equals(effect.getValue())) {
				return effect;
			}
		}
		return null;
	}
}
