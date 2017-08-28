package com.d1m.wechat.dto;

import java.util.List;

public class FunctionDto {
	
	private Integer id;
	
	private Integer parentId;
	
	private String code;
	
	private String nameCn;
	
	private String nameEn;
	
	private List<FunctionDto> children;

	public Integer getId() {
		return id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public String getCode() {
		return code;
	}

	public String getNameCn() {
		return nameCn;
	}

	public String getNameEn() {
		return nameEn;
	}

	public List<FunctionDto> getChildren() {
		return children;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setNameCn(String nameCn) {
		this.nameCn = nameCn;
	}

	public void setNameEn(String nameEn) {
		this.nameEn = nameEn;
	}

	public void setChildren(List<FunctionDto> children) {
		this.children = children;
	}

	
}
