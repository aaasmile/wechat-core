package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("会员标签类型参数")
public class MemberTagTypeInfoModel extends MemberTagTypeModel{
	
	@ApiModelProperty("主键")
	private Integer id;
	@ApiModelProperty("公众号ID")
	private String wechat_id;
	@ApiModelProperty("父标签名称")
	private String parentName;

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getWechat_id() {
		return wechat_id;
	}

	public void setWechat_id(String wechat_id) {
		this.wechat_id = wechat_id;
	}

	public String getParentName() {
		return parentName;
	}

	public void setParentName(String parentName) {
		this.parentName = parentName;
	}

}
