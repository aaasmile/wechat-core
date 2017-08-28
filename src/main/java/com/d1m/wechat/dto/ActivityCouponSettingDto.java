package com.d1m.wechat.dto;

public class ActivityCouponSettingDto {

	/** 优惠券id */
	private Integer id;

	/** 关系id */
	private Integer activityCouponSettingId;

	private String name;

	private String grno;

	private String channel;

	private String type;

	public Integer getActivityCouponSettingId() {
		return activityCouponSettingId;
	}

	public String getChannel() {
		return channel;
	}

	public String getGrno() {
		return grno;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getType() {
		return type;
	}

	public void setActivityCouponSettingId(Integer activityCouponSettingId) {
		this.activityCouponSettingId = activityCouponSettingId;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setGrno(String grno) {
		this.grno = grno;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setType(String type) {
		this.type = type;
	}

}
