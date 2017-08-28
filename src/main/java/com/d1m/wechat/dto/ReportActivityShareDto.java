package com.d1m.wechat.dto;

public class ReportActivityShareDto {
	
	private String date;
	private int feedCount;
	private int friendsCount;
	private int qqCount;
	private int weiboCount;
	private int qzoneCount;
	
	public String getDate() {
		return date;
	}
	public int getFeedCount() {
		return feedCount;
	}
	public int getFriendsCount() {
		return friendsCount;
	}
	public int getQqCount() {
		return qqCount;
	}
	public int getWeiboCount() {
		return weiboCount;
	}
	public int getQzoneCount() {
		return qzoneCount;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setFeedCount(int feedCount) {
		this.feedCount = feedCount;
	}
	public void setFriendsCount(int friendsCount) {
		this.friendsCount = friendsCount;
	}
	public void setQqCount(int qqCount) {
		this.qqCount = qqCount;
	}
	public void setWeiboCount(int weiboCount) {
		this.weiboCount = weiboCount;
	}
	public void setQzoneCount(int qzoneCount) {
		this.qzoneCount = qzoneCount;
	}

}
