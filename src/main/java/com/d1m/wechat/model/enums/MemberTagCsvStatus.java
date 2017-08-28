package com.d1m.wechat.model.enums;

public enum MemberTagCsvStatus {
	
	PREPARE((byte) 0, "准备"),

	PROGRESS((byte) 1, "进行"),
	
	FINISH((byte) 2, "完成"),

	;
	
	private byte value;

	private String name;

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

	private MemberTagCsvStatus(byte value, String name) {
		this.value = value;
		this.name = name;
	}
	
}
