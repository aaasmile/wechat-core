package com.d1m.wechat.dto;

import lombok.Data;

/**
 * Auth: Jo.Ho
 * Date: 2018/12/9
 */
@Data
public class InterfaceConfigBrandDto {
	private Long id;
	private String name;
	private String key;
	private String secret;
	private String  createAt;
	private boolean deleted;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getKey() {
		return key;
	}

	public void setKey(String key) {
		this.key = key;
	}

	public String getSecret() {
		return secret;
	}

	public void setSecret(String secret) {
		this.secret = secret;
	}

	public boolean isDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}
}
