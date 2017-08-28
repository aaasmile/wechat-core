package com.d1m.wechat.model.enums;

public enum MemberStatus {

	UN_BUND((byte) 0, "已解绑"),

	BIND((byte) 1, "已绑定"),

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
	private MemberStatus(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

}
