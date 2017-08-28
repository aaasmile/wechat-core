package com.d1m.wechat.model.enums;

public enum ActivityType {

	BUSINESS((byte) 1, "门店活动", "10"),

	H5((byte) 2, "H5活动", "10"),

	WEIXIN((byte) 3, "微信活动", "10"),

	CRM((byte) 4, "CRM活动", "20"),

	;

	public static ActivityType getValueByValue(Byte value) {
		if (value == null) {
			return null;
		}
		for (ActivityType activityType : ActivityType.values()) {
			if (value.equals(activityType.getValue())) {
				return activityType;
			}
		}
		return null;
	}

	private Byte value;

	private String name;

	private String channel;

	/**
	 * @param name
	 */
	private ActivityType(Byte value, String name, String channel) {
		this.value = value;
		this.name = name;
		this.channel = channel;
	}

	public String getChannel() {
		return channel;
	}

	public String getName() {
		return name;
	}

	public Byte getValue() {
		return value;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setValue(Byte value) {
		this.value = value;
	}

}
