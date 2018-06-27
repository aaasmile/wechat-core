package com.d1m.wechat.model;

import java.util.Date;

public class Tag {

	/**
     * 主键ID
     */
    private Integer id;

    /**
     * 会员ID
     */
    private Integer memberId;

    /**
     * 会员分组ID
     */
    private Integer memberTagId;

    /**
     * 公众号ID
     */
    private Integer wechatId;

    /**
     * 创建时间
     */
    private Date createdAt;
    
    private String openId;
    /**
     * 会员标签名称
     */
    private String memberTagName;
    
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getMemberId() {
		return memberId;
	}
	public void setMemberId(Integer memberId) {
		this.memberId = memberId;
	}
	public Integer getMemberTagId() {
		return memberTagId;
	}
	public void setMemberTagId(Integer memberTagId) {
		this.memberTagId = memberTagId;
	}
	public Integer getWechatId() {
		return wechatId;
	}
	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}
	public Date getCreatedAt() {
		return createdAt;
	}
	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getMemberTagName() {
		return memberTagName;
	}
	public void setMemberTagName(String memberTagName) {
		this.memberTagName = memberTagName;
	}
	@Override
	public String toString() {
		return "Tag [id=" + id + ", memberId=" + memberId + ", memberTagId=" + memberTagId + ", wechatId=" + wechatId
				+ ", createdAt=" + createdAt + ", openId=" + openId + ", memberTagName=" + memberTagName + "]";
	}
}
