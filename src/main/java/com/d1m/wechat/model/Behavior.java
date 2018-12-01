package com.d1m.wechat.model;

public class Behavior {

	private String eventName;
	private String title;
	private Double locationX;
	private Double locationY;
	
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
	public static class UserBehavior {
		
		private String eventName;
		private String title;
		
		public UserBehavior eventName(String eventName) {
			this.eventName = eventName;
			return this;
		}
		public UserBehavior title(String title) {
			this.title = title;
			return this;
		}
	}
	public static class UserLocation {
		private String eventName;
		private Double locationX;
		private Double locationY;
		
		public UserLocation eventName(String eventName) {
			this.eventName = eventName;
			return this;
		}
		public UserLocation locationX(Double locationX) {
			this.locationX = locationX;
			return this;
		}
		public UserLocation locationY(Double locationY) {
			this.locationY = locationY;
			return this;
		}
	}
}
