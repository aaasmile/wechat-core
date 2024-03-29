package com.d1m.wechat.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "member_member_tag")
public class MemberMemberTag {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    private Integer memberId;

    /**
     * 会员分组ID
     */
    @Column(name = "member_tag_id")
    private Integer memberTagId;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;
    
    @Column(name = "open_id")
    private String openId;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取会员ID
     *
     * @return member_id - 会员ID
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * 设置会员ID
     *
     * @param memberId 会员ID
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取会员分组ID
     *
     * @return member_tag_id - 会员分组ID
     */
    public Integer getMemberTagId() {
        return memberTagId;
    }

    /**
     * 设置会员分组ID
     *
     * @param memberTagId 会员分组ID
     */
    public void setMemberTagId(Integer memberTagId) {
        this.memberTagId = memberTagId;
    }

    /**
     * 获取公众号ID
     *
     * @return wechat_id - 公众号ID
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * 设置公众号ID
     *
     * @param wechatId 公众号ID
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

	public String getOpenId() {
		return openId;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public MemberMemberTag() {}

	public MemberMemberTag(Integer memberId, Integer memberTagId, Integer wechatId, String openId, Date createdAt) {
        this.memberId = memberId;
        this.memberTagId = memberTagId;
        this.wechatId = wechatId;
        this.openId = openId;
        this.createdAt = createdAt;
    }
}