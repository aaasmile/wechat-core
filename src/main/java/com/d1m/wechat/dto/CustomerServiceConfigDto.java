package com.d1m.wechat.dto;

public class CustomerServiceConfigDto {

	private String group;

	private String groupLabel;

	private Integer id;

	private String key;

	private String value;

	public String getGroup() {
		return group;
	}

	public String getGroupLabel() {
		return groupLabel;
	}

	public Integer getId() {
		return id;
	}

	public String getKey() {
		return key;
	}

	public String getValue() {
		return value;
	}

	public void setGroup(String group) {
		this.group = group;
	}

	public void setGroupLabel(String groupLabel) {
		this.groupLabel = groupLabel;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public void setValue(String value) {
		this.value = value;
	}
}
