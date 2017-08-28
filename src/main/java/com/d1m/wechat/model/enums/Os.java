package com.d1m.wechat.model.enums;

public enum Os {

	IOS((byte) 1),

	Android((byte) 2),

	Others((byte) 3),

	;

	private byte value;

	public byte getValue() {
		return value;
	}

	public void setValue(byte value) {
		this.value = value;
	}

	/**
	 * @param value
	 */
	private Os(byte value) {
		this.value = value;
	}

	public static Os getByValue(Byte value) {
		if (value == null) {
			return null;
		}
		for (Os os : Os.values()) {
			if (value.equals(os.getValue())) {
				return os;
			}
		}
		return null;
	}

}
