package com.d1m.wechat.pamametermodel;

public class MemberTagTypeModel extends BaseModel{
	
	private String name;
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
