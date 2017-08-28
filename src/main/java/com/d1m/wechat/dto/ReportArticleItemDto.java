package com.d1m.wechat.dto;

public class ReportArticleItemDto {
	
	private String date;
	private Integer pageUser;
	private Integer pageCount;
	private Integer oriPageUser;
	private Integer oriPageCount;
	private Integer addFavUser;
	private Integer addFavCount;
	private Integer shareUser;
	private Integer shareCount;
	private String title;
	private String msgid;
	
	public String getDate() {
		return date;
	}
	public Integer getPageUser() {
		return pageUser;
	}
	public Integer getPageCount() {
		return pageCount;
	}
	public Integer getOriPageUser() {
		return oriPageUser;
	}
	public Integer getOriPageCount() {
		return oriPageCount;
	}
	public Integer getAddFavUser() {
		return addFavUser;
	}
	public Integer getAddFavCount() {
		return addFavCount;
	}
	public Integer getShareUser() {
		return shareUser;
	}
	public Integer getShareCount() {
		return shareCount;
	}
	public String getTitle() {
		return title;
	}
	public String getMsgid() {
		return msgid;
	}
	public void setDate(String date) {
		this.date = date;
	}
	public void setPageUser(Integer pageUser) {
		this.pageUser = pageUser;
	}
	public void setPageCount(Integer pageCount) {
		this.pageCount = pageCount;
	}
	public void setOriPageUser(Integer oriPageUser) {
		this.oriPageUser = oriPageUser;
	}
	public void setOriPageCount(Integer oriPageCount) {
		this.oriPageCount = oriPageCount;
	}
	public void setAddFavUser(Integer addFavUser) {
		this.addFavUser = addFavUser;
	}
	public void setAddFavCount(Integer addFavCount) {
		this.addFavCount = addFavCount;
	}
	public void setShareUser(Integer shareUser) {
		this.shareUser = shareUser;
	}
	public void setShareCount(Integer shareCount) {
		this.shareCount = shareCount;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public void setMsgid(String msgid) {
		this.msgid = msgid;
	}
	
	

}
