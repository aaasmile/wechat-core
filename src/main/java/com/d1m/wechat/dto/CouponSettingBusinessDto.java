package com.d1m.wechat.dto;

public class CouponSettingBusinessDto {

	private Integer id;

	private String name;

	private String code;

	public String getCode() {
		return code;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

}
