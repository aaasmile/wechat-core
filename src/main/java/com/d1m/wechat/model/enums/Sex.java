package com.d1m.wechat.model.enums;

import org.apache.commons.lang.StringUtils;

public enum Sex {

	UNKNOW("未知", (byte) 0),

	MALE("男", (byte) 1),

	FEMALE("女", (byte) 2),

	;

	private String name;

	private byte value;

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
	 * @param name
	 * @param value
	 */
	private Sex(String name, byte value) {
		this.name = name;
		this.value = value;
	}

	public static Sex getByName(String name) {
		if (StringUtils.isBlank(name)) {
			return null;
		}
		for (Sex sex : Sex.values()) {
			if (StringUtils.equals(sex.getName(), name)) {
				return sex;
			}
		}
		return null;
	}

	public static Sex getByValue(Byte value) {
		if (value == null) {
			return null;
		}
		for (Sex sex : Sex.values()) {
			if (sex.getValue() == value) {
				return sex;
			}
		}
		return null;
	}

}
