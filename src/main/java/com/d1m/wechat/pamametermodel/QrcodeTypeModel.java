package com.d1m.wechat.pamametermodel;

public class QrcodeTypeModel extends BaseModel {

	private Integer id;

	private String name;

	private Integer parentId;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getParentId() {
		return parentId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setParentId(Integer parentId) {
		this.parentId = parentId;
	}

}
