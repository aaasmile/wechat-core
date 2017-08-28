package com.d1m.wechat.model.enums;

public enum OriginalConversationStatus {

	WAIT((byte) 1, "等待处理"),

	SUCCESS((byte) 2, "成功"),

	FAIL((byte) 3, "失败"),

	REPEAT((byte) 4, "重复"),

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
	private OriginalConversationStatus(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

}
