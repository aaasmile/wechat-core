package com.d1m.wechat.dto;

public class SessionMemberDto {
	
	private String memberName;
	private String phone;
	private String businessName;
	private String memberSession;
	private String province;
	private String city;
	
	public String getMemberName() {
		return memberName;
	}
	public String getPhone() {
		return phone;
	}
	public String getBusinessName() {
		return businessName;
	}
	public String getMemberSession() {
		return memberSession;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}
	public void setMemberSession(String memberSession) {
		this.memberSession = memberSession;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
