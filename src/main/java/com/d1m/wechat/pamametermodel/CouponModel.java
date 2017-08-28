package com.d1m.wechat.pamametermodel;

import java.util.List;

public class CouponModel extends BaseModel {

	private Integer couponSettingId;

	private Integer activityId;

	private Byte status;

	private Integer businessId;

	private Integer memberId;

	private String giftType;

	private Integer wechatId;

	private List<String> grnos;

	private Byte except;

	public Integer getActivityId() {
		return activityId;
	}

	public Integer getBusinessId() {
		return businessId;
	}

	public Integer getCouponSettingId() {
		return couponSettingId;
	}

	public Byte getExcept() {
		return except;
	}

	public String getGiftType() {
		return giftType;
	}

	public List<String> getGrnos() {
		return grnos;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public Byte getStatus() {
		return status;
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public void setActivityId(Integer activityId) {
		this.activityId = activityId;
	}

	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}

	public void setCouponSettingId(Integer couponSettingId) {
		this.couponSettingId = couponSettingId;
	}

	public void setExcept(Byte except) {
		this.except = except;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public void setGrnos(List<String> grnos) {
		this.grnos = grnos;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

}
