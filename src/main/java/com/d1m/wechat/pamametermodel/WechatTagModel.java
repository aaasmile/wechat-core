package com.d1m.wechat.pamametermodel;

import java.util.Date;

public class WechatTagModel extends BaseModel{
	
	private Integer id;
	
	/**
     * 公众号名称
     */
    private String name;
	
	/**
     * App ID
     */
    private String appid;

    /**
     * App Secret
     */
    private String appscret;

    /**
     * 公众号描述
     */
    private String remark;

    /**
     * Token
     */
    private String token;

    /**
     * URL
     */
    private String url;

    /**
     * Encoding AES Key
     */
    private String encodingAesKey;

    /**
     * Open ID
     */
    private String openId;

    /**
     * 状态
     */
    private Byte status;
    
    /**
     * 创建时间
     */
    private Date createdAt;
    
    private String headImgUrl;
    

	public String getName() {
		return name;
	}

	public String getAppid() {
		return appid;
	}

	public String getAppscret() {
		return appscret;
	}

	public String getRemark() {
		return remark;
	}

	public String getToken() {
		return token;
	}

	public String getUrl() {
		return url;
	}

	public String getEncodingAesKey() {
		return encodingAesKey;
	}

	public String getOpenId() {
		return openId;
	}

	public Byte getStatus() {
		return status;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setAppid(String appid) {
		this.appid = appid;
	}

	public void setAppscret(String appscret) {
		this.appscret = appscret;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public void setToken(String token) {
		this.token = token;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public void setEncodingAesKey(String encodingAesKey) {
		this.encodingAesKey = encodingAesKey;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getHeadImgUrl() {
		return headImgUrl;
	}

	public void setHeadImgUrl(String headImgUrl) {
		this.headImgUrl = headImgUrl;
	}

}
