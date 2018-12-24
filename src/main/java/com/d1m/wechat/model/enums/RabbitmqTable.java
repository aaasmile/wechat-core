package com.d1m.wechat.model.enums;

public enum RabbitmqTable {

	ORIGINAL_CONVERSATION("original_conversation"),
	CONVERSATION("conversation"),
	CONVERSATION_IMAGE_TEXT_DETAIL("conversation_image_text_detail"),
	MENU("menu"),
	DCRM_IMAGE_TEXT("dcrm_image_text"),
	WECHAT_IMAGE_TEXT("wechat_image_text");
	
	private String value;
	
	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	private RabbitmqTable(String value) {
		this.value = value;
	}
}
