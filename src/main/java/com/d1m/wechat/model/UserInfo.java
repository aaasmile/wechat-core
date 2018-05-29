package com.d1m.wechat.model;

public class UserInfo implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4150992752925409331L;
	private String unionid, username, password;

	public String getUnionid() {
		return unionid;
	}

	public void setUnionid(String unionid) {
		this.unionid = unionid;
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
}
