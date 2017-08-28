package com.d1m.wechat.dto;

import java.util.Date;

public class MemberStatusDto {
	private Integer memberId;
	private Boolean isSubscribe;
	private Date menuClickLast;
	private Date conversationLast;
	
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Boolean getIsSubscribe() {
		return isSubscribe;
	}
	public void setIsSubscribe(Boolean isSubscribe) {
		this.isSubscribe = isSubscribe;
	}
	public Date getMenuClickLast() {
		return menuClickLast;
	}
	public void setMenuClickLast(Date menuClickLast) {
		this.menuClickLast = menuClickLast;
	}
	public Date getConversationLast() {
		return conversationLast;
	}
	public void setConversationLast(Date conversationLast) {
		this.conversationLast = conversationLast;
	}
}
