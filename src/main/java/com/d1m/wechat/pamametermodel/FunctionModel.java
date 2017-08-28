package com.d1m.wechat.pamametermodel;

public class FunctionModel extends BaseModel{
	
	private Integer id;
	
	private Integer parentId;
	
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
