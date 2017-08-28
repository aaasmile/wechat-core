package com.d1m.wechat.pamametermodel;

import java.util.Date;

public class QrcodeModel extends BaseModel {

	private Integer id;

	private Date createdAt;

	private String name;

	private String summary;

	private Integer qrcodeTypeId;

	private String qrcodeTypeName;

	private String qrcodeUrl;

	private String scene;

	private Integer qrcodeId;

	private ActionEngineModel rules;

	private String query;

	private Byte sopportSubscribeReply;
	
	private String start;
	
	private String end;
	
	private Integer status;

	public Date getCreatedAt() {
		return createdAt;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public Integer getQrcodeId() {
		return qrcodeId;
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

	public String getQuery() {
		return query;
	}

	public ActionEngineModel getRules() {
		return rules;
	}

	public String getScene() {
		return scene;
	}

	public Byte getSopportSubscribeReply() {
		return sopportSubscribeReply;
	}

	public String getSummary() {
		return summary;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setQrcodeId(Integer qrcodeId) {
		this.qrcodeId = qrcodeId;
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

	public void setQuery(String query) {
		this.query = query;
	}

	public void setRules(ActionEngineModel rules) {
		this.rules = rules;
	}

	public void setScene(String scene) {
		this.scene = scene;
	}

	public void setSopportSubscribeReply(Byte sopportSubscribeReply) {
		this.sopportSubscribeReply = sopportSubscribeReply;
	}

	public void setSummary(String summary) {
		this.summary = summary;
	}

	public String getStart() {
		return start;
	}

	public void setStart(String start) {
		this.start = start;
	}

	public String getEnd() {
		return end;
	}

	public void setEnd(String end) {
		this.end = end;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}
