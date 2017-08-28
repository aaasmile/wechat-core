package com.d1m.wechat.dto;

public class MenuRuleDto {

	private Integer groupId;

	private Byte gender;

	private Byte clientPlatformType;

	private Integer country;

	private Integer province;

	private Integer city;

	private String language;

	private Integer tagId;

	public Integer getCity() {
		return city;
	}

	public Byte getClientPlatformType() {
		return clientPlatformType;
	}

	public Integer getCountry() {
		return country;
	}

	public Integer getGroupId() {
		return groupId;
	}

	public String getLanguage() {
		return language;
	}

	public Integer getProvince() {
		return province;
	}

	public Byte getGender() {
		return gender;
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

	public void setGroupId(Integer groupId) {
		this.groupId = groupId;
	}

	public void setLanguage(String language) {
		this.language = language;
	}

	public Integer getTagId() {
		return tagId;
	}

	public void setTagId(Integer tagId) {
		this.tagId = tagId;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public void setGender(Byte gender) {
		this.gender = gender;
	}

}
