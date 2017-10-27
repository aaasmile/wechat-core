package com.d1m.wechat.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("客服配置")
public class CustomerServiceConfigDto {
	
	@ApiModelProperty("组名")
	private String group;
	
	@ApiModelProperty("组名标签")
	private String groupLabel;
	
	@ApiModelProperty("主键ID")
	private Integer id;
	
	@ApiModelProperty("key")
	private String key;
	
	@ApiModelProperty("值")
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
