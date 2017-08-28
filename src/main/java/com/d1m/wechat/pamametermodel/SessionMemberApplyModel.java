package com.d1m.wechat.pamametermodel;

public class SessionMemberApplyModel {
	
	private String openId;
	private String name;
	private String phone;
	private String province;
	private String city;
	private Integer offlineActivityId;
	private Integer businessId;
	private Integer sessionId;
	
	
	public String getName() {
		return name;
	}
	public String getPhone() {
		return phone;
	}
	public Integer getOfflineActivityId() {
		return offlineActivityId;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public Integer getBusinessId() {
		return businessId;
	}
	public Integer getSessionId() {
		return sessionId;
	}
	public String getOpenId() {
		return openId;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setOfflineActivityId(Integer offlineActivityId) {
		this.offlineActivityId = offlineActivityId;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public void setBusinessId(Integer businessId) {
		this.businessId = businessId;
	}
	public void setSessionId(Integer sessionId) {
		this.sessionId = sessionId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	
}
