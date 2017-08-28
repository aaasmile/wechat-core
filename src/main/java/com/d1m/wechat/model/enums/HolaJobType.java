package com.d1m.wechat.model.enums;

public enum HolaJobType {

	BIND_CARD(1),

	EDIT_PROFILE(2), ;

	private Integer value;

	public Integer getValue() {
		return value;
	}

	public void setValue(Integer value) {
		this.value = value;
	}

	/**
	 * @param value
	 */
	private HolaJobType(Integer value) {
		this.value = value;
	}

}
