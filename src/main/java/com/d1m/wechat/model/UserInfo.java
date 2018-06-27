package com.d1m.wechat.model;

import java.util.List;

public class UserInfo implements java.io.Serializable {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 4150992752925409331L;
	private String unionid, username, password;
	private String wechatId;
	private List<Tag> tags;

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

	public String getWechatId() {
		return wechatId;
	}

	public void setWechatId(String wechatId) {
		this.wechatId = wechatId;
	}

	public List<Tag> getTags() {
		return tags;
	}

	public void setTags(List<Tag> tags) {
		this.tags = tags;
	}

	@Override
	public String toString() {
		return "UserInfo [unionid=" + unionid + ", username=" + username + ", password=" + password + ", wechatId="
				+ wechatId + ", tags=" + tags + "]";
	}
}
