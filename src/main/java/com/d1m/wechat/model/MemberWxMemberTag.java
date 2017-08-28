package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "member_wx_member_tag")
public class MemberWxMemberTag {
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
     * 微信会员标签ID
     */
    @Column(name = "wx_member_tag_id")
    private Integer wxMemberTagId;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

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
     * 获取微信会员标签ID
     *
     * @return wx_member_tag_id - 微信会员标签ID
     */
    public Integer getWxMemberTagId() {
        return wxMemberTagId;
    }

    /**
     * 设置微信会员标签ID
     *
     * @param wxMemberTagId 微信会员标签ID
     */
    public void setWxMemberTagId(Integer wxMemberTagId) {
        this.wxMemberTagId = wxMemberTagId;
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
}