package com.d1m.wechat.pamametermodel;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("素材参数")
public class MassConversationModel extends AddMemberTagModel {
	
	@ApiModelProperty("素材ID")
	private Integer materialId;
	
	@ApiModelProperty("内容")
	private String content;
	
	@ApiModelProperty("是否发送给所有人")
	private Boolean sendToAll;
	
	@ApiModelProperty("状态(0:删除,1:使用)")
	private String status;
	
	@ApiModelProperty("是否发送给所有人")
	private String reason;
	
	@ApiModelProperty("群发会话主键ID")
	private Integer id;

	@ApiModelProperty("执行时间")
	private String runAt;
	
	@ApiModelProperty("会话ID")
	private Integer conversationId;
	
	@ApiModelProperty("公众号ID")
	private Integer wechatId;
	
	@ApiModelProperty("是否强制发送")
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
