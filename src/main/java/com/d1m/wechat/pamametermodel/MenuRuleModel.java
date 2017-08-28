package com.d1m.wechat.pamametermodel;

public class MenuRuleModel {

	private Byte gender;

	private Byte language;

	private Byte clientPlatformType;

	private Integer country;

	private Integer province;

	private Integer city;

	private Integer tagId;

	public boolean empty() {
		return gender == null && language == null && clientPlatformType == null
				&& country == null && province == null && city == null && tagId == null;
	}

	public Integer getCity() {
		return city;
	}

	public Byte getClientPlatformType() {
		return clientPlatformType;
	}

	public Integer getCountry() {
		return country;
	}

	public Byte getGender() {
		return gender;
	}

	public Byte getLanguage() {
		return language;
	}

	public Integer getProvince() {
		return province;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public void setClientPlatformType(Byte clientPlatformType) {
		this.clientPlatformType = clientPlatformType;
	}

	public void setCountry(Integer country) {
		this.country = country;
	}

	public void setGender(Byte gender) {
		this.gender = gender;
	}

	public void setLanguage(Byte language) {
		this.language = language;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}
}
