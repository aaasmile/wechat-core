package com.d1m.wechat.model.enums;

public enum QrcodeAction {

	QR_SCENE((byte) 1, "临时"),

	QR_LIMIT_SCENE((byte) 2, "永久数值"),

	QR_LIMIT_STR_SCENE((byte) 3, "永久字符串"),

	;

	private byte value;

	private String name;

	/**
	 * @param value
	 * @param name
	 */
	private QrcodeAction(byte value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public byte getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(byte value) {
		this.value = value;
	}

}
