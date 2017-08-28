package com.d1m.wechat.model.enums;

public enum ReplyMatchMode {

	FUZZY_MATCH((byte) 1, "模糊匹配"),

	PERFECT_MATCH((byte) 2, "完全匹配"),

	PREFIX_MATCH((byte) 3, "前缀匹配"),

	SUFFIXMATCH((byte) 4, "后缀匹配"),

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
	private ReplyMatchMode(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

	public static ReplyMatchMode getByValue(Byte value) {
		if (value == null) {
			return null;
		}
		for (ReplyMatchMode matchMode : ReplyMatchMode.values()) {
			if (matchMode.getValue() == value) {
				return matchMode;
			}
		}
		return null;
	}

}
