package com.d1m.wechat.dto;

import java.util.List;

public class BusinessCategoryDto {
	
	private Integer id;

	private String name;

	private List<BusinessCategoryDto> children;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public List<BusinessCategoryDto> getChildren() {
		return children;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setChildren(List<BusinessCategoryDto> children) {
		this.children = children;
	}
	
}
