package com.d1m.wechat.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserBehavior {
	
	private String eventName;
	private String title;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createdAt;
	
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
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public UserBehavior() {
		super();
	}
	public UserBehavior(String eventName, String title, Date createdAt) {
		super();
		this.eventName = eventName;
		this.title = title;
		this.createdAt = createdAt;
	}
}
