package com.d1m.wechat.pamametermodel;

import java.util.List;

import com.d1m.wechat.model.Wechat;

public class UserModel extends BaseModel {

	private String username;

	private String avatar;

	private String password;

	private String confirmPassword;

	private String email;

	private String mobile;

	private String position;

	private List<Integer> wechatIds;
	
	private List<Wechat> wechats;
	
	private Byte status;
	
	private Integer roleId;
	
	private Integer userId;
	
	private Integer defaultWechatId;
	
	private Integer companyId;
	
	private Integer id;
	
	private String start;
	
	private String end;

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getAvatar() {
		return avatar;
	}

	public void setAvatar(String avatar) {
		this.avatar = avatar;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getConfirmPassword() {
		return confirmPassword;
	}

	public void setConfirmPassword(String confirmPassword) {
		this.confirmPassword = confirmPassword;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getPosition() {
		return position;
	}

	public void setPosition(String position) {
		this.position = position;
	}

	public List<Integer> getWechatIds() {
		return wechatIds;
	}

	public void setWechatIds(List<Integer> wechatIds) {
		this.wechatIds = wechatIds;
	}

	public List<Wechat> getWechats() {
		return wechats;
	}

	public void setWechats(List<Wechat> wechats) {
		this.wechats = wechats;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Integer getRoleId() {
		return roleId;
	}

	public void setRoleId(Integer roleId) {
		this.roleId = roleId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getDefaultWechatId() {
		return defaultWechatId;
	}

	public void setDefaultWechatId(Integer defaultWechatId) {
		this.defaultWechatId = defaultWechatId;
	}

	public Integer getCompanyId() {
		return companyId;
	}

	public void setCompanyId(Integer companyId) {
		this.companyId = companyId;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	
}
