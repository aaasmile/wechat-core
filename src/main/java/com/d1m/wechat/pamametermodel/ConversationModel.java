package com.d1m.wechat.pamametermodel;

import java.util.Date;
import java.util.List;

public class ConversationModel extends BaseModel {

	private Long time;

	private Integer memberId;

	private Integer materialId;

	private String content;

	/**
	 * 方向
	 */
	private Integer dir;

	/**
	 * 0:进,1:出
	 */
	private Byte type;

	private Date startAt;

	private Date endAt;

	private Byte status;

	private boolean updateRead;

	private Byte msgType;

	private String keyWords;

	private String start;

	private List<Byte> msgTypes;

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

}
