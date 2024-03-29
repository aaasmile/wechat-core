package com.d1m.wechat.pamametermodel;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("会员标签参数")
public class MemberTagModel extends BaseModel{
	
	@ApiModelProperty("会员标签ID")
	private Integer id;
	
	@ApiModelProperty("会员标签名称")
	private String name;
	
	@ApiModelProperty("会员标签类型")
	private Integer memberTagTypeId;
	
	private List<Integer> ids;

	public Integer getId() {
		return id;
	}

	public Integer getMemberTagTypeId() {
		return memberTagTypeId;
	}

	public String getName() {
		return name;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMemberTagTypeId(Integer memberTagTypeId) {
		this.memberTagTypeId = memberTagTypeId;
	}

	public void setName(String name) {
		this.name = name;
	}

	public List<Integer> getIds() {
		return ids;
	}

	public void setIds(List<Integer> ids) {
		this.ids = ids;
	}

}
