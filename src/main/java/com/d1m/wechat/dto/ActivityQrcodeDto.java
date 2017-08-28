package com.d1m.wechat.dto;

public class ActivityQrcodeDto {

	private Integer id;

	private String channel;

	private String qrcodeImgUrl;

	private String actyUrl;

	public String getActyUrl() {
		return actyUrl;
	}

	public String getChannel() {
		return channel;
	}

	public Integer getId() {
		return id;
	}

	public String getQrcodeImgUrl() {
		return qrcodeImgUrl;
	}

	public void setActyUrl(String actyUrl) {
		this.actyUrl = actyUrl;
	}

	public void setChannel(String channel) {
		this.channel = channel;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setQrcodeImgUrl(String qrcodeImgUrl) {
		this.qrcodeImgUrl = qrcodeImgUrl;
	}

}
