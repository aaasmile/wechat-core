package com.d1m.wechat.model.enums;

import org.apache.commons.lang.StringUtils;

public enum Language {

	zh_CN((byte) 1, "简体中文"),

	zh_TW((byte) 2, "繁体中文TW"),

	zh_HK((byte) 3, "繁体中文HK"),

	en((byte) 4, "英文"),

	id((byte) 5, "印尼"),

	ms((byte) 6, "马来"),

	es((byte) 7, "西班牙"),

	ko((byte) 8, "韩国"),

	it((byte) 9, "意大利"),

	ja((byte) 10, "日本"),

	pl((byte) 11, "波兰"),

	pt((byte) 12, "葡萄牙"),

	ru((byte) 13, "俄国"),

	th((byte) 14, "泰文"),

	vi((byte) 15, "越南"),

	ar((byte) 16, "阿拉伯语"),

	hi((byte) 17, "北印度"),

	he((byte) 18, "希伯来"),

	tr((byte) 19, "土耳其"),

	de((byte) 20, "德语"),

	FR((byte) 21, "法语"), ;

	private byte value;

	private String name;

	/**
	 * @param value
	 * @param name
	 */
	private Language(byte value, String name) {
		this.value = value;
		this.name = name;
	}

	public byte getValue() {
		return value;
	}

	public String getName() {
		return name;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public static Language getValueByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (Language language : Language.values()) {
			if (language.name().equalsIgnoreCase(name)) {
				return language;
			}
		}
		return null;
	}

	public static Language getByValue(Byte value) {
		if (value == null) {
			return null;
		}
		for (Language language : Language.values()) {
			if (language.getValue() == value) {
				return language;
			}
		}
		return null;
	}

}
