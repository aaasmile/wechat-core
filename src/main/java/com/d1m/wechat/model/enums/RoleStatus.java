package com.d1m.wechat.model.enums;

public enum RoleStatus {
	
	DELETED((byte) 0, "删除"),

	INUSED((byte) 1, "使用"),

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
	private RoleStatus(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

}
