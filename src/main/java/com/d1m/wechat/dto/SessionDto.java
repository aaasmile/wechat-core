package com.d1m.wechat.dto;

public class SessionDto {
	
	private Integer id;
	private Integer offlineActivityId;
	private String createdAt;
	private String session;
	private Integer memberLimit;
	private Integer status;
	private Integer apply;
	
	public Integer getId() {
		return id;
	}
	public Integer getOfflineActivityId() {
		return offlineActivityId;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public String getSession() {
		return session;
	}
	public Integer getMemberLimit() {
		return memberLimit;
	}
	public Integer getStatus() {
		return status;
	}
	public Integer getApply() {
		return apply;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public void setOfflineActivityId(Integer offlineActivityId) {
		this.offlineActivityId = offlineActivityId;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public void setSession(String session) {
		this.session = session;
	}
	public void setMemberLimit(Integer memberLimit) {
		this.memberLimit = memberLimit;
	}
	public void setStatus(Integer status) {
		this.status = status;
	}
	public void setApply(Integer apply) {
		this.apply = apply;
	}
	
	
}
