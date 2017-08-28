package com.d1m.wechat.pamametermodel;

public class MassConversationModel extends AddMemberTagModel {

	private Integer materialId;

	private String content;

	private Boolean sendToAll;

	private String status;

	private String reason;

	private Integer id;

	private String runAt;

	private Integer conversationId;

	private Integer wechatId;
	
	private Boolean isForce;

	public String getContent() {
		return content;
	}

	public Integer getConversationId() {
		return conversationId;
	}

	public Integer getId() {
		return id;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public String getReason() {
		return reason;
	}

	public String getRunAt() {
		return runAt;
	}

	public Boolean getSendToAll() {
		return sendToAll;
	}

	public String getStatus() {
		return status;
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setConversationId(Integer conversationId) {
		this.conversationId = conversationId;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public void setRunAt(String runAt) {
		this.runAt = runAt;
	}

	public void setSendToAll(Boolean sendToAll) {
		this.sendToAll = sendToAll;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

	public Boolean getIsForce() {
		return isForce;
	}

	public void setIsForce(Boolean isForce) {
		this.isForce = isForce;
	}

}
