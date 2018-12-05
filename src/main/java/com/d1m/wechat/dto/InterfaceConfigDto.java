package com.d1m.wechat.dto;

public class InterfaceConfigDto {

	private String id;
	private String brand;
	private String name;
	private String methodType;
	private String event;
	private String interfaceName;
	private String parameter;
	private String description;
	private String url;
	private String key;
	private String secret;
	private String sequence;
	private String isDeleted;
	private String createdAt;
	private String createdBy;
	private String lasteUpdatedAt;
	private String lasteUpdatedBy;

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

	public String getEvent() {
		return event;
	}

	public void setEvent(String event) {
		this.event = event;
	}

	public String getInterfaceName() {
		return interfaceName;
	}

	public void setInterfaceName(String interfaceName) {
		this.interfaceName = interfaceName;
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

	public String getSequence() {
		return sequence;
	}

	public void setSequence(String sequence) {
		this.sequence = sequence;
	}

	public String getIsDeleted() {
		return isDeleted;
	}

	public void setIsDeleted(String isDeleted) {
		this.isDeleted = isDeleted;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCreatedBy() {
		return createdBy;
	}

	public void setCreatedBy(String createdBy) {
		this.createdBy = createdBy;
	}

	public String getLasteUpdatedAt() {
		return lasteUpdatedAt;
	}

	public void setLasteUpdatedAt(String lasteUpdatedAt) {
		this.lasteUpdatedAt = lasteUpdatedAt;
	}

	public String getLasteUpdatedBy() {
		return lasteUpdatedBy;
	}

	public void setLasteUpdatedBy(String lasteUpdatedBy) {
		this.lasteUpdatedBy = lasteUpdatedBy;
	}

	public InterfaceConfigDto(String id, String brand, String name, String methodType, String event, String interfaceName, String parameter, String description, String url, String key, String secret, String sequence, String isDeleted, String createdAt, String createdBy, String lasteUpdatedAt, String lasteUpdatedBy) {
		super();
		this.id = id;
		this.brand = brand;
		this.name = name;
		this.methodType = methodType;
		this.event = event;
		this.interfaceName = interfaceName;
		this.parameter = parameter;
		this.description = description;
		this.url = url;
		this.key = key;
		this.secret = secret;
		this.sequence = sequence;
		this.isDeleted = isDeleted;
		this.createdAt = createdAt;
		this.createdBy = createdBy;
		this.lasteUpdatedAt = lasteUpdatedAt;
		this.lasteUpdatedBy = lasteUpdatedBy;
	}

	public InterfaceConfigDto() {
		super();
	}

	public static class Builder {
		private String id;
		private String brand;
		private String name;
		private String methodType;
		private String event;
		private String interfaceName;
		private String parameter;
		private String description;
		private String url;
		private String key;
		private String secret;
		private String sequence;
		private String isDeleted;
		private String createdAt;
		private String createdBy;
		private String lasteUpdatedAt;
		private String lasteUpdatedBy;

		public Builder id(String id) {
			this.id = id;
			return this;
		}

		public Builder brand(String brand) {
			this.brand = brand;
			return this;
		}

		public Builder name(String name) {
			this.name = name;
			return this;
		}

		public Builder methodType(String methodType) {
			this.methodType = methodType;
			return this;
		}

		public Builder event(String event) {
			this.event = event;
			return this;
		}

		public Builder interfaceName(String interfaceName) {
			this.interfaceName = interfaceName;
			return this;
		}

		public Builder parameter(String parameter) {
			this.parameter = parameter;
			return this;
		}

		public Builder description(String description) {
			this.description = description;
			return this;
		}

		public Builder url(String url) {
			this.url = url;
			return this;
		}

		public Builder key(String key) {
			this.key = key;
			return this;
		}

		public Builder secret(String secret) {
			this.secret = secret;
			return this;
		}

		public Builder sequence(String sequence) {
			this.sequence = sequence;
			return this;
		}

		public Builder isDeleted(String isDeleted) {
			this.isDeleted = isDeleted;
			return this;
		}

		public Builder createdAt(String createdAt) {
			this.createdAt = createdAt;
			return this;
		}

		public Builder createdBy(String createdBy) {
			this.createdBy = createdBy;
			return this;
		}

		public Builder lasteUpdatedAt(String lasteUpdatedAt) {
			this.lasteUpdatedAt = lasteUpdatedAt;
			return this;
		}

		public Builder lasteUpdatedBy(String lasteUpdatedBy) {
			this.lasteUpdatedBy = lasteUpdatedBy;
			return this;
		}

		public InterfaceConfigDto build() {
			return new InterfaceConfigDto(this);
		}
	}

	private InterfaceConfigDto(Builder builder) {
		this.id = builder.id;
		this.brand = builder.brand;
		this.name = builder.name;
		this.methodType = builder.methodType;
		this.event = builder.event;
		this.interfaceName = builder.interfaceName;
		this.parameter = builder.parameter;
		this.description = builder.description;
		this.url = builder.url;
		this.key = builder.key;
		this.secret = builder.secret;
		this.sequence = builder.sequence;
		this.isDeleted = builder.isDeleted;
		this.createdAt = builder.createdAt;
		this.createdBy = builder.createdBy;
		this.lasteUpdatedAt = builder.lasteUpdatedAt;
		this.lasteUpdatedBy = builder.lasteUpdatedBy;
	}
}
