package com.d1m.wechat.pamametermodel;

public class SessionMemberModel extends BaseModel{
	
	private Integer offlineActivityId;
	private String phone;
	private String date;
	private String session;
	
	public Integer getOfflineActivityId() {
		return offlineActivityId;
	}
	public String getPhone() {
		return phone;
	}
	public String getDate() {
		return date;
	}
	public String getSession() {
		return session;
	}
	public void setOfflineActivityId(Integer offlineActivityId) {
		this.offlineActivityId = offlineActivityId;
	}
	public void setPhone(String phone) {
		this.phone = phone;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setSession(String session) {
		this.session = session;
	}
	
}
