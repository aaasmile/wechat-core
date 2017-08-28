package com.d1m.wechat.model.enums;

public enum ActivityShareType {

	FRIEND_CIRCLE((byte) 1, "朋友圈"),

	FRIEND((byte) 2, "朋友"),

	QQ((byte) 3, "QQ"),

	BLOG_TENCENT((byte) 4, "腾讯博客"),

	QQ_ZONE((byte) 5, "QQ空间"),

	;

	private Byte value;

	private String name;

	/**
	 * @param value
	 * @param name
	 */
	private ActivityShareType(Byte value, String name) {
		this.value = value;
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public Byte getValue() {
		return value;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

	public static ActivityShareType getValueByValue(Byte value) {
		if (value == null) {
			return null;
		}
		for (ActivityShareType activityShareType : ActivityShareType.values()) {
			if (value.equals(activityShareType.getValue())) {
				return activityShareType;
			}
		}
		return null;
	}

}
