package com.d1m.wechat.model.enums;

public enum ConversationStatus {

	UNREAD((byte) 0, "未回复"),

	READ((byte) 1, "已回复"),

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
	private ConversationStatus(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

}
