package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("会员标签类型参数")
public class MemberTagTypeModel extends BaseModel{
	
	@ApiModelProperty("类型名")
	private String name;
	
	@ApiModelProperty("父ID")
	private Integer parentId;
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}
	
}
