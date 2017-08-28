package com.d1m.wechat.dto;

public class WechatDto {

	private Integer id;

	private String name;
	
	private String headImg;
	
	private Integer isUse;

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getHeadImg() {
		return headImg;
	}

	public Integer getIsUse() {
		return isUse;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setHeadImg(String headImg) {
		this.headImg = headImg;
	}

	public void setIsUse(Integer isUse) {
		this.isUse = isUse;
	}


}
