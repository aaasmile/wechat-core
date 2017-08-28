package com.d1m.wechat.dto;

import java.util.Date;
import java.util.List;

public class RoleDto {
	
	private Integer id;
	
	private String name;
	
	private String description;
	
	private Byte status;
	
	private Date createdAt;
	
	private Integer creatorId;
	
	private List<FunctionDto> functions;
	
	private List<Integer> functionIds;
	
	private List<String> functionCodes;
	
	private Integer isSystemRole;

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

	public List<FunctionDto> getFunctions() {
		return functions;
	}

	public List<Integer> getFunctionIds() {
		return functionIds;
	}

	public List<String> getFunctionCodes() {
		return functionCodes;
	}

	public Integer getIsSystemRole() {
		return isSystemRole;
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

	public void setFunctions(List<FunctionDto> functions) {
		this.functions = functions;
	}

	public void setFunctionIds(List<Integer> functionIds) {
		this.functionIds = functionIds;
	}

	public void setFunctionCodes(List<String> functionCodes) {
		this.functionCodes = functionCodes;
	}

	public void setIsSystemRole(Integer isSystemRole) {
		this.isSystemRole = isSystemRole;
	}


}
