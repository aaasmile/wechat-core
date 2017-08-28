package com.d1m.wechat.pamametermodel;

import java.util.Date;
import java.util.List;

public class RoleModel extends BaseModel{
	
	private Integer id;
	
	private String name;
	
	private String description;
	
	private Byte status;
	
	private Date createdAt;
	
	private Integer creatorId;
	
	private List<Integer> functionIds;
	
	private Integer companyId;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getDescription() {
		return description;
	}

	public Byte getStatus() {
		return status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Integer getCreatorId() {
		return creatorId;
	}

	public List<Integer> getFunctionIds() {
		return functionIds;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setCreatorId(Integer creatorId) {
		this.creatorId = creatorId;
	}

	public void setFunctionIds(List<Integer> functionIds) {
		this.functionIds = functionIds;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


}
