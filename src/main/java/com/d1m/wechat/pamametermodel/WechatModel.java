package com.d1m.wechat.pamametermodel;

public class WechatModel extends BaseModel {

	private String name;

	private Byte status;
	
	private Integer companyId;
	
	private Integer isSystemRole;

	public String getName() {
		return name;
	}

	public Byte getStatus() {
		return status;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public Integer getIsSystemRole() {
		return isSystemRole;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public void setIsSystemRole(Integer isSystemRole) {
		this.isSystemRole = isSystemRole;
	}


}
