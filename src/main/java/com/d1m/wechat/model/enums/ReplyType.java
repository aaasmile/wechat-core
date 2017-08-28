package com.d1m.wechat.model.enums;

public enum ReplyType {

	SUBSCRIBE_REPLY((byte) 1, "关注回复"),

	KEY_REPLY((byte) 2, "关键字回复"),
	
	DEFAULT_REPLY((byte)0, "默认回复"),

	;

	private byte value;

	private String name;

	public byte getValue() {
		return value;
	}

	public void setCode(byte value) {
		this.value = value;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	/**
	 * @param code
	 * @param name
	 */
	private ReplyType(Byte value, String name) {
		this.value = value;
		this.name = name;
	}
}
