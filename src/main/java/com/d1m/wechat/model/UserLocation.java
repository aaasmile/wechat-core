package com.d1m.wechat.model;

public class UserLocation {

	private String eventName;
	private Double locationX;
	private Double locationY;
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
	public UserLocation(String eventName, Double locationX, Double locationY) {
		super();
		this.eventName = eventName;
		this.locationX = locationX;
		this.locationY = locationY;
	}
	public UserLocation() {
		super();
	}
}
