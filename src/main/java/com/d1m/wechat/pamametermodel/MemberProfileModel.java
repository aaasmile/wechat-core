package com.d1m.wechat.pamametermodel;

public class MemberProfileModel {

	private Integer memberId;

	private Integer wechatId;

	private String openId;

	private String name;

	private String birthDate;

	private Byte sex;

	private String email;

	private Integer province;

	private Integer city;

	private String address;

	private boolean acceptPromotion;

	public String getAddress() {
		return address;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public Integer getCity() {
		return city;
	}

	public String getEmail() {
		return email;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public String getName() {
		return name;
	}

	public String getOpenId() {
		return openId;
	}

	public Integer getProvince() {
		return province;
	}

	public Byte getSex() {
		return sex;
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public boolean isAcceptPromotion() {
		return acceptPromotion;
	}

	public void setAcceptPromotion(boolean acceptPromotion) {
		this.acceptPromotion = acceptPromotion;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	@Override
	public String toString() {
		return "MemberProfileModel [memberId=" + memberId + ", wechatId="
				+ wechatId + ", openId=" + openId + ", name=" + name
				+ ", birthDate=" + birthDate + ", sex=" + sex + ", email="
				+ email + ", province=" + province + ", city=" + city
				+ ", address=" + address + ", acceptPromotion="
				+ acceptPromotion + "]";
	}

}
