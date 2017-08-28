package com.d1m.wechat.dto;

public class HolaMemberDto {

	private Integer memberId;

	private Integer wechatId;

	private String openId;

	private Byte status;

	public Integer getMemberId() {
		return memberId;
	}

	public String getOpenId() {
		return openId;
	}

	public Byte getStatus() {
		return status;
	}

	public Integer getWechatId() {
		return wechatId;
	}

	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}

}
