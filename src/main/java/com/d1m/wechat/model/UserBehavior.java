package com.d1m.wechat.model;

public class UserBehavior {
	
	private String eventName;
	private String title;
	
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
	public UserBehavior(String eventName, String title) {
		super();
		this.eventName = eventName;
		this.title = title;
	}
	public UserBehavior() {
		super();
	}
	
}
