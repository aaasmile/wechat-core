package com.d1m.wechat.model.enums;

public enum Subscribe {

	SUBSCRIBE((byte) 0, "关注"),

	UNSUBSCRIBE((byte) 1, "取消关注"), ;

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
	private Subscribe(Byte value, String name) {
		this.value = value;
		this.name = name;
	}
}
