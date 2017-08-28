package com.d1m.wechat.dto;

import java.util.List;

public class UserDto {

	private Integer id;

	private String avatar;

	private String username;

	private String mobile;

	private String email;

	private String position;

	private String lastLoginAt;
	
	private Integer roleId;

	private List<WechatDto> wechats;
	
	private RoleDto role;
	
	private List<Integer> functionIds;

	private List<FunctionDto> functionDtos;
	
	private List<String> functionCodes;
	
	private Integer companyId;
	
	public String getAvatar() {
		return avatar;
	}

	public String getEmail() {
		return email;
	}

	public Integer getId() {
		return id;
	}

	public String getLastLoginAt() {
		return lastLoginAt;
	}

	public String getMobile() {
		return mobile;
	}

	public String getPosition() {
		return position;
	}

	public String getUsername() {
		return username;
	}

	public List<WechatDto> getWechats() {
		return wechats;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setLastLoginAt(String lastLoginAt) {
		this.lastLoginAt = lastLoginAt;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public void setWechats(List<WechatDto> wechats) {
		this.wechats = wechats;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public RoleDto getRole() {
		return role;
	}

	public void setRole(RoleDto role) {
		this.role = role;
	}

	public List<Integer> getFunctionIds() {
		return functionIds;
	}

	public void setFunctionIds(List<Integer> functionIds) {
		this.functionIds = functionIds;
	}

	public List<FunctionDto> getFunctionDtos() {
		return functionDtos;
	}

	public void setFunctionDtos(List<FunctionDto> functionDtos) {
		this.functionDtos = functionDtos;
	}

	public List<String> getFunctionCodes() {
		return functionCodes;
	}

	public void setFunctionCodes(List<String> functionCodes) {
		this.functionCodes = functionCodes;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}


}
