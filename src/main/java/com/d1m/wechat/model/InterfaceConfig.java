package com.d1m.wechat.model;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.persistence.*;

@ApiModel("第三方接口参数")
@Table(name = "interface_config")
public class InterfaceConfig {

	/**
	 * 主键
	 */
	@ApiModelProperty("ID")
	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private String id;

	@ApiModelProperty("第三方ID")
	private String brand;

	@ApiModelProperty("名称")
	private String name;

	@ApiModelProperty("方法类型 POST/GET")
	@Column(name = "method_type")
	private String methodType;

	@ApiModelProperty("接口类型")
	private String type;

	@ApiModelProperty("事件")
	private String event;

	@ApiModelProperty("参数")
	private String parameter;

	@ApiModelProperty("描述")
	private String description;

	@ApiModelProperty("URL")
	private String url;

	private int sequence;

	@Column(name = "is_deleted")
	private boolean deleted;

	@Column(name = "created_at")
	private long createdAt;

	@Column(name = "created_by")
	private String createdBy;

	@Column(name = "updated_at")
	private long updatedAt;

	@Column(name = "updated_by")
	private String updatedBy;

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getBrand() {
		return brand;
	}

	public void setBrand(String brand) {
		this.brand = brand;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getMethodType() {
		return methodType;
	}

	public void setMethodType(String methodType) {
		this.methodType = methodType;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getParameter() {
		return parameter;
	}

	public void setParameter(String parameter) {
		this.parameter = parameter;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public int getSequence() {
		return sequence;
	}

	public void setSequence(int sequence) {
		this.sequence = sequence;
	}

	public boolean getDeleted() {
		return deleted;
	}

	public void setDeleted(boolean deleted) {
		this.deleted = deleted;
	}

	public long getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(long createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public long getUpdatedAt() {
		return updatedAt;
	}

	public void setUpdatedAt(long updatedAt) {
		this.updatedAt = updatedAt;
	}

	public String getUpdatedBy() {
		return updatedBy;
	}

	public void setUpdatedBy(String updatedBy) {
		this.updatedBy = updatedBy;
	}
}
