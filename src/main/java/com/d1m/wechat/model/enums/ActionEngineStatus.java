package com.d1m.wechat.model.enums;

public enum ActionEngineStatus {

	OFF((byte) 1, "关闭"),

	ON((byte) 1, "使用"),

	DELETE((byte) 1, "删除"),

	;

	private byte value;

	private String name;

	/**
	 * @param value
	 * @param name
	 */
	private ActionEngineStatus(byte value, String name) {
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
