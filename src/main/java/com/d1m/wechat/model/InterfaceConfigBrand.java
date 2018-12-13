package com.d1m.wechat.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

/**
 * Auth: Jo.Ho
 * Date: 2018/12/9
 */
@ApiModel("第三方接口类别参数")
@Table(name = "interface_brand")
public class InterfaceConfigBrand {

	@ApiModelProperty("ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Long id;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("接口KEY")
	@Column(name = "app_key")
	private String key;

	@ApiModelProperty("接口密码")
	@Column(name = "app_secret")
	private String secret;

	@Column(name = "is_deleted")
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
