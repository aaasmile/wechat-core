package com.d1m.wechat.pamametermodel;

import java.util.Date;
import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("会话参数")
public class ConversationModel extends BaseModel {
	
	@ApiModelProperty("")
	private Long time;
	
	@ApiModelProperty("会员ID")
	private Integer memberId;
	
	@ApiModelProperty("素材ID")
	private Integer materialId;
	
	@ApiModelProperty("内容")
	private String content;

	/**
	 * 方向
	 */
	@ApiModelProperty("方向")
	private Integer dir;

	/**
	 * 0:进,1:出
	 */
	@ApiModelProperty("0:进,1:出")
	private Byte type;
	
	@ApiModelProperty("起始时间")
	private Date startAt;
	
	@ApiModelProperty("结束时间")
	private Date endAt;

	@ApiModelProperty("状态(0:未回复,1:已回复)")
	private Byte status;
	
	@ApiModelProperty("结束时间")
	private boolean updateRead;
	
	@ApiModelProperty("消息类型")
	private Byte msgType;
	
	@ApiModelProperty("消息类型")
	private String keyWords;
	
	@ApiModelProperty("消息类型")
	private String start;

	private List<Byte> msgTypes;
	
	private Integer newid;
	private String newtype;

	public String getContent() {
		return content;
	}

	public Integer getDir() {
		return dir;
	}

	public Date getEndAt() {
		return endAt;
	}

	public String getKeyWords() {
		return keyWords;
	}

	public Integer getMaterialId() {
		return materialId;
	}

	public Integer getMemberId() {
		return memberId;
	}

	public Byte getMsgType() {
		return msgType;
	}

	public List<Byte> getMsgTypes() {
		return msgTypes;
	}

	public String getStart() {
		return start;
	}

	public Date getStartAt() {
		return startAt;
	}

	public Byte getStatus() {
		return status;
	}

	public Long getTime() {
		return time;
	}

	public Byte getType() {
		return type;
	}

	public boolean isUpdateRead() {
		return updateRead;
	}

	public void setContent(String content) {
		this.content = content;
	}

	public void setDir(Integer dir) {
		this.dir = dir;
	}

	public void setEndAt(Date endAt) {
		this.endAt = endAt;
	}

	public void setKeyWords(String keyWords) {
		this.keyWords = keyWords;
	}

	public void setMaterialId(Integer materialId) {
		this.materialId = materialId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public void setMsgType(Byte msgType) {
		this.msgType = msgType;
	}

	public void setMsgTypes(List<Byte> msgTypes) {
		this.msgTypes = msgTypes;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setTime(Long time) {
		this.time = time;
	}

	public void setType(Byte type) {
		this.type = type;
	}

	public void setUpdateRead(boolean updateRead) {
		this.updateRead = updateRead;
	}

	public Integer getNewid() {
		return newid;
	}

	public void setNewid(Integer newid) {
		this.newid = newid;
	}

	public String getNewtype() {
		return newtype;
	}

	public void setNewtype(String newtype) {
		this.newtype = newtype;
	}

}
