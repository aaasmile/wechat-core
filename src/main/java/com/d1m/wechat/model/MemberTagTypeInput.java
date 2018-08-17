package com.d1m.wechat.model;

import java.io.Serializable;

import javax.validation.constraints.Size;

import org.hibernate.validator.constraints.NotBlank;

public class MemberTagTypeInput implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -8731989242556714670L;
	private String parentMemberTgTypeName;

	@NotBlank
	private String username;
	@NotBlank
	private String password;
	@NotBlank
	@Size(max=255)
	private String memberTgName;
	@NotBlank
	@Size(max=255)
	private String memberTgTypeName;
	@NotBlank
	@Size(max=11)
	private String wechatId;
	public String getMemberTgName() {
		return memberTgName;
	}
	public void setMemberTgName(String memberTgName) {
		this.memberTgName = memberTgName;
	}
	public String getMemberTgTypeName() {
		return memberTgTypeName;
	}
	public void setMemberTgTypeName(String memberTgTypeName) {
		this.memberTgTypeName = memberTgTypeName;
	}
	public String getWechatId() {
		return wechatId;
	}
	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {
		this.password = password;
	}
	public String getParentMemberTgTypeName() {
		return parentMemberTgTypeName;
	}
	public void setParentMemberTgTypeName(String parentMemberTgTypeName) {
		this.parentMemberTgTypeName = parentMemberTgTypeName;
	}

	@Override
	public String toString() {
		return "UserInfo [memberTgName=" + memberTgName + ", username=" + username + ", password=" + password + ", wechatId="
				+ wechatId + ", memberTgTypeName=" + memberTgTypeName + ", parentMemberTgTypeName" + parentMemberTgTypeName + "]";
	}
}
