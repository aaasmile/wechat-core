package com.d1m.wechat.dto;

public class MemberProfileDto {

	private String mobile;

	private String name;

	private String level;

	private Integer credits;

	private String birthDate;

	private Byte sex;

	private String email;

	private Integer province;

	private Integer city;

	private String provinceStr;

	private String cityStr;

	private String address;

	private Boolean acceptPromotion;

	private String cardId;

	public Boolean getAcceptPromotion() {
		return acceptPromotion;
	}

	public String getAddress() {
		return address;
	}

	public String getBirthDate() {
		return birthDate;
	}

	public String getCardId() {
		return cardId;
	}

	public Integer getCity() {
		return city;
	}

	public Integer getCredits() {
		return credits;
	}

	public String getEmail() {
		return email;
	}

	public String getLevel() {
		return level;
	}

	public String getMobile() {
		return mobile;
	}

	public String getName() {
		return name;
	}

	public Integer getProvince() {
		return province;
	}

	public Byte getSex() {
		return sex;
	}

	public void setAcceptPromotion(Boolean acceptPromotion) {
		this.acceptPromotion = acceptPromotion;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setBirthDate(String birthDate) {
		this.birthDate = birthDate;
	}

	public void setCardId(String cardId) {
		this.cardId = cardId;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public void setCredits(Integer credits) {
		this.credits = credits;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setLevel(String level) {
		this.level = level;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public void setSex(Byte sex) {
		this.sex = sex;
	}

	public String getProvinceStr() {
		return provinceStr;
	}

	public void setProvinceStr(String provinceStr) {
		this.provinceStr = provinceStr;
	}

	public String getCityStr() {
		return cityStr;
	}

	public void setCityStr(String cityStr) {
		this.cityStr = cityStr;
	}
}
