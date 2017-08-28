package com.d1m.wechat.dto;

public class BusinessItemDto {
	
	private Integer id;
	private String name;
	private String province;
	private String city;
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public String getProvince() {
		return province;
	}
	public String getCity() {
		return city;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setProvince(String province) {
		this.province = province;
	}
	public void setCity(String city) {
		this.city = city;
	}
	
}
