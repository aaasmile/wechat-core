package com.d1m.wechat.model;

public class UserBehavior {
	
	private String eventName;
	private String title;
	private String createdAt;
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}
	public UserBehavior() {
		super();
	}
	public UserBehavior(String eventName, String title, String createdAt) {
		super();
		this.eventName = eventName;
		this.title = title;
		this.createdAt = createdAt;
	}
}
