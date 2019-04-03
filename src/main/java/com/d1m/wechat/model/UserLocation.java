package com.d1m.wechat.model;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.util.Date;

public class UserLocation {

	private String eventName;
	private Double locationX;
	private Double locationY;
	@JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss",timezone = "GMT+8")
	private Date createdAt;
	
	public String getEventName() {
		return eventName;
	}
	public void setEventName(String eventName) {
		this.eventName = eventName;
	}
	public Double getLocationX() {
		return locationX;
	}
	public void setLocationX(Double locationX) {
		this.locationX = locationX;
	}
	public Double getLocationY() {
		return locationY;
	}
	public void setLocationY(Double locationY) {
		this.locationY = locationY;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public UserLocation() {
		super();
	}
	public UserLocation(String eventName, Double locationX, Double locationY, Date createdAt) {
		super();
		this.eventName = eventName;
		this.locationX = locationX;
		this.locationY = locationY;
		this.createdAt = createdAt;
	}
}
