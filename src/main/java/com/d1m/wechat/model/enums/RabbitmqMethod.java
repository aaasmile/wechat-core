package com.d1m.wechat.model.enums;

public enum RabbitmqMethod {

	INSERT("insert"),
	M2WINSERT("m2winsert"),
	W2MINSERT("w2minsert"),
	UPDATE("update"),
	CUSTOMSERVICE("customservice"),
	SEND_DCRM_IMAGE_TEXT("send_dcrm_image_text"),
	SEND_WECHAT_IMAGE_TEXT("send_wechat_image_text");
	
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private RabbitmqMethod(String value) {
		this.value = value;
	}
}
