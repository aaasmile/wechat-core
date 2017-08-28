package com.d1m.wechat.dto;

import java.util.List;

public class ActivityListDto {
	
	private Integer id;
	private String name;
	private List<CouponSettingDto> list;
	
	public Integer getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public List<CouponSettingDto> getList() {
		return list;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setName(String name) {
		this.name = name;
	}
	public void setList(List<CouponSettingDto> list) {
		this.list = list;
	}
	
}
