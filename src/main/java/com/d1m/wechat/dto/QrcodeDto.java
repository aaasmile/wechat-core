package com.d1m.wechat.dto;

public class QrcodeDto {

	private Integer id;

	private String qrcodeTypeName;

	private Integer qrcodeTypeId;

	private String name;

	private String qrcodeUrl;

	private String createdAt;

	private String summary;

	private String scene;

	private Byte status;

	private Byte sopportSubscribeReply;

	public String getCreatedAt() {
		return createdAt;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getQrcodeTypeId() {
		return qrcodeTypeId;
	}

	public String getQrcodeTypeName() {
		return qrcodeTypeName;
	}

	public String getQrcodeUrl() {
		return qrcodeUrl;
	}

	public String getScene() {
		return scene;
	}

	public Byte getSopportSubscribeReply() {
		return sopportSubscribeReply;
	}

	public Byte getStatus() {
		return status;
	}

	public String getSummary() {
		return summary;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQrcodeTypeId(Integer qrcodeTypeId) {
		this.qrcodeTypeId = qrcodeTypeId;
	}

	public void setQrcodeTypeName(String qrcodeTypeName) {
		this.qrcodeTypeName = qrcodeTypeName;
	}

	public void setQrcodeUrl(String qrcodeUrl) {
		this.qrcodeUrl = qrcodeUrl;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public void setSopportSubscribeReply(Byte sopportSubscribeReply) {
		this.sopportSubscribeReply = sopportSubscribeReply;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

}
