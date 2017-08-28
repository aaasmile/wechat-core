package com.d1m.wechat.pamametermodel;

import java.util.Date;

public class SessionModel {
	
	private Integer offlineActivityId;
	
	private String session;
	
	private Date createdAt;
	
	private Integer memberLimit;

	public Integer getOfflineActivityId() {
		return offlineActivityId;
	}

	public String getSession() {
		return session;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public Integer getMemberLimit() {
		return memberLimit;
	}

	public void setOfflineActivityId(Integer offlineActivityId) {
		this.offlineActivityId = offlineActivityId;
	}

	public void setSession(String session) {
		this.session = session;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setMemberLimit(Integer memberLimit) {
		this.memberLimit = memberLimit;
	}

	
}
