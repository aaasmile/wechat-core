package com.d1m.wechat.dto;

public class ReportActivityItemDto {
	
	private String date;
	private int memberCount;
	private int couponReceiveCount;
	private int couponValidityCount;
	private int feedCount;
	private int friendsCount;
	private int qqCount;
	private int weiboCount;
	private int qzoneCount;
	
	public String getDate() {
		return date;
	}
	public int getMemberCount() {
		return memberCount;
	}
	public int getCouponReceiveCount() {
		return couponReceiveCount;
	}
	public int getCouponValidityCount() {
		return couponValidityCount;
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
	public void setMemberCount(int memberCount) {
		this.memberCount = memberCount;
	}
	public void setCouponReceiveCount(int couponReceiveCount) {
		this.couponReceiveCount = couponReceiveCount;
	}
	public void setCouponValidityCount(int couponValidityCount) {
		this.couponValidityCount = couponValidityCount;
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
