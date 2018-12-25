package com.d1m.wechat.dto;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("会话DTO")
public class ConversationDto {
	
	/** conversation id */
	@ApiModelProperty("群发会话ID")
	private Integer cid;

	/** mass conversation result id */
	@ApiModelProperty("群发会话结果ID")
	private Integer id;

	@ApiModelProperty("消息类型")
	private Byte msgType;
	
	@ApiModelProperty("会员头像")
	private String memberPhoto;
	
	@ApiModelProperty("会员ID")
	private String memberId;
	
	@ApiModelProperty("昵称")
	private String memberNickname;
	
	@ApiModelProperty("openId")
	private String memberOpenId;
	
	@ApiModelProperty("客服头像")
	private String kfPhoto;
	
	@ApiModelProperty("文本")
	private String content;

	@ApiModelProperty("会话时间")
	private String createdAt;
	
	@ApiModelProperty("当前时间")
	private String current;

	private List<ImageTextDto> items;
	
	@ApiModelProperty("会话方向(0:进,1:出)")
	private Integer dir;
	
	@ApiModelProperty("是否是群发会话")
	private Integer isMass;

	private MassConversationResultDto result;
	
	@ApiModelProperty("状态(0:未回复,1:已回复)")
	private Byte status;
	
	@ApiModelProperty("素材ID")
	private Integer materialId;
	
	@ApiModelProperty("事件类型")
	private Byte event;
	
	@ApiModelProperty("发送时间")
	private String sendAt;
	
	@ApiModelProperty("微信发送回调时间")
	private String wxSendAt;
	
	@ApiModelProperty("审核时间")
	private String auditAt;
	
	@ApiModelProperty("审核人")
	private String auditBy;

	@ApiModelProperty("审核备注")
	private String reason;
	
	@ApiModelProperty("执行时间")
	private String runAt;
	
	@ApiModelProperty("语音url")
	private String voiceUrl;
	
	@ApiModelProperty("视频url")
	private String videoUrl;
	
	@ApiModelProperty("群发会话结果ID")
	private Integer csrId;
	
	@ApiModelProperty("图文内容json串")
	private String conditions;
	
	private String newtype;

	public Integer getCid() {
		return cid;
	}

	public void setCid(Integer cid) {
		this.cid = cid;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Byte getMsgType() {
		return msgType;
	}

	public void setMsgType(Byte msgType) {
		this.msgType = msgType;
	}

	public String getMemberPhoto() {
		return memberPhoto;
	}

	public void setMemberPhoto(String memberPhoto) {
		this.memberPhoto = memberPhoto;
	}

	public String getMemberId() {
		return memberId;
	}

	public void setMemberId(String memberId) {
		this.memberId = memberId;
	}

	public String getMemberNickname() {
		return memberNickname;
	}

	public void setMemberNickname(String memberNickname) {
		this.memberNickname = memberNickname;
	}

	public String getMemberOpenId() {
		return memberOpenId;
	}

	public void setMemberOpenId(String memberOpenId) {
		this.memberOpenId = memberOpenId;
	}

	public String getKfPhoto() {
		return kfPhoto;
	}

	public void setKfPhoto(String kfPhoto) {
		this.kfPhoto = kfPhoto;
	}

	public String getContent() {
		return content;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public String getCurrent() {
		return current;
	}

	public void setCurrent(String current) {
		this.current = current;
	}

	public List<ImageTextDto> getItems() {
		return items;
	}

	public void setItems(List<ImageTextDto> items) {
		this.items = items;
	}

	public Integer getDir() {
		return dir;
	}

	public void setDir(Integer dir) {
		this.dir = dir;
	}

	public Integer getIsMass() {
		return isMass;
	}

	public void setIsMass(Integer isMass) {
		this.isMass = isMass;
	}

	public MassConversationResultDto getResult() {
		return result;
	}

	public void setResult(MassConversationResultDto result) {
		this.result = result;
	}

	public Byte getStatus() {
		return status;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public Byte getEvent() {
		return event;
	}

	public void setEvent(Byte event) {
		this.event = event;
	}

	public String getSendAt() {
		return sendAt;
	}

	public void setSendAt(String sendAt) {
		this.sendAt = sendAt;
	}

	public String getWxSendAt() {
		return wxSendAt;
	}

	public void setWxSendAt(String wxSendAt) {
		this.wxSendAt = wxSendAt;
	}

	public String getAuditAt() {
		return auditAt;
	}

	public void setAuditAt(String auditAt) {
		this.auditAt = auditAt;
	}

	public String getAuditBy() {
		return auditBy;
	}

	public void setAuditBy(String auditBy) {
		this.auditBy = auditBy;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	public String getRunAt() {
		return runAt;
	}

	public void setRunAt(String runAt) {
		this.runAt = runAt;
	}

	public String getVoiceUrl() {
		return voiceUrl;
	}

	public void setVoiceUrl(String voiceUrl) {
		this.voiceUrl = voiceUrl;
	}

	public String getVideoUrl() {
		return videoUrl;
	}

	public void setVideoUrl(String videoUrl) {
		this.videoUrl = videoUrl;
	}

	public Integer getCsrId() {
		return csrId;
	}

	public void setCsrId(Integer csrId) {
		this.csrId = csrId;
	}

	public String getConditions() {
		return conditions;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public String getNewtype() {
		return newtype;
	}

	public void setNewtype(String newtype) {
		this.newtype = newtype;
	}
}
