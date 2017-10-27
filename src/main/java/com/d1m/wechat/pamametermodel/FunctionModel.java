package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("功能参数")
public class FunctionModel extends BaseModel{
	
	@ApiModelProperty("主键ID")
	private Integer id;
	
	@ApiModelProperty("父级ID")
	private Integer parentId;
	
	@ApiModelProperty("功能代码")
	private String code;
	
	public Integer getId() {
		return id;
	}

	public Integer getParentId() {
		return parentId;
	}

	public String getCode() {
		return code;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setId(Integer id) {
		this.id = id;
	}
	
	

}
