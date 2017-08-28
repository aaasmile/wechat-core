package com.d1m.wechat.model.enums;

public enum MaterialType {

	IMAGE_TEXT((byte) 1, "图文"),

	IMAGE((byte) 2, "图片"),

	VOICE((byte) 3, "音频"),

	VIDEO((byte) 4, "视频"),

	LITTLE_VIDEO((byte) 5, "小视频"),

	TEXT((byte) 6, "文本"),
	
	MEDIAIMAGE((byte) 7, "图文图片"),
	
	OUTLETIMAGE((byte) 8, "门店图片"),

	OFFLINEACTIVITYIMAGE((byte) 9, "线下活动图片"),

	;

	private byte value;

	private String name;

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	/**
	 * @param value
	 * @param name
	 */
	private MaterialType(byte value, String name) {
		this.value = value;
		this.name = name;
	}

}
