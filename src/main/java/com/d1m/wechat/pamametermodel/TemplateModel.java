package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("模板参数")
public class TemplateModel {
	
	@ApiModelProperty("颜色")
	private String color;
	
	@ApiModelProperty("ID")
	private Integer id;

	@ApiModelProperty("值")
	private String value;

	public String getColor() {
		return color;
	}
	public Integer getId() {
		return id;
	}

	public String getValue() {
		return value;
	}

	public void setColor(String color) {
		this.color = color;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setValue(String value) {
		this.value = value;
	}

}
