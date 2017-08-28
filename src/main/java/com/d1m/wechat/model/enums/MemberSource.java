package com.d1m.wechat.model.enums;

public enum MemberSource {

	WECHAT_SEARCH((byte) 0, "公众号搜索"),

	BUSINESS_CARD((byte) 1, "名片分享"),

	QRCODE((byte) 2, "二维码扫描"),

	TOP_RIGHT_MENU((byte) 3, "图文页右上角菜单"),

	WECHAT_IN_IMAGE_TEXT((byte) 4, "图文页内公众号名称"),

	WECHAT_IN_ARTICLE_AD((byte) 5, "公众号文章广告"),

	MOMENTS_AD((byte) 6, "朋友圈广告"),

	PAY_ATTENTION_AFTER((byte) 7, "支付后关注"),

	OTHER((byte) 8, "其他合计"), ;

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
	private MemberSource(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

}
