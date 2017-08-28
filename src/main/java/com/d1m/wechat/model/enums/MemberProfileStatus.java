package com.d1m.wechat.model.enums;

public enum MemberProfileStatus {

	UN_BUND((byte) 0, "已解绑"),

	BIND((byte) 1, "已绑定"),

	WAIT_BIND((byte) 2, "待绑卡"),

	MULTI_CARD((byte) 3, "多张卡"),

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
	private MemberProfileStatus(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

}
