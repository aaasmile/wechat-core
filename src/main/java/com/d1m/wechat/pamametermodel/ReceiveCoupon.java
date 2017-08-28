package com.d1m.wechat.pamametermodel;

public class ReceiveCoupon {

	private Integer activityId;

	private String openId;

	private Integer couponSettingId;

	private Byte type;

	public Integer getActivityId() {
		return activityId;
	}

	public Integer getCouponSettingId() {
		return couponSettingId;
	}

	public String getOpenId() {
		return openId;
	}

	public Byte getType() {
		return type;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public void setCouponSettingId(Integer couponSettingId) {
		this.couponSettingId = couponSettingId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setType(Byte type) {
		this.type = type;
	}

}
