package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "member_subscribe")
public class MemberSubscribe {
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
     * 是否关注(1:已关注,0:未关注)
     */
    private Byte subscribe;

    /**
     * 关注/取消关注时间
     */
    @Column(name = "subscribe_at")
    private Date subscribeAt;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * ip
     */
    @Column(name = "subscribe_ip")
    private String subscribeIp;

    /**
     * 关注方式
     */
    @Column(name = "subscribe_by")
    private Byte subscribeBy;

    /**
     * 二维码ID
     */
    @Column(name = "qrcode_id")
    private Integer qrcodeId;

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
     * 获取是否关注(1:已关注,0:未关注)
     *
     * @return subscribe - 是否关注(1:已关注,0:未关注)
     */
    public Byte getSubscribe() {
        return subscribe;
    }

    /**
     * 设置是否关注(1:已关注,0:未关注)
     *
     * @param subscribe 是否关注(1:已关注,0:未关注)
     */
    public void setSubscribe(Byte subscribe) {
        this.subscribe = subscribe;
    }

    /**
     * 获取关注/取消关注时间
     *
     * @return subscribe_at - 关注/取消关注时间
     */
    public Date getSubscribeAt() {
        return subscribeAt;
    }

    /**
     * 设置关注/取消关注时间
     *
     * @param subscribeAt 关注/取消关注时间
     */
    public void setSubscribeAt(Date subscribeAt) {
        this.subscribeAt = subscribeAt;
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
     * 获取ip
     *
     * @return subscribe_ip - ip
     */
    public String getSubscribeIp() {
        return subscribeIp;
    }

    /**
     * 设置ip
     *
     * @param subscribeIp ip
     */
    public void setSubscribeIp(String subscribeIp) {
        this.subscribeIp = subscribeIp == null ? null : subscribeIp.trim();
    }

    /**
     * 获取关注方式
     *
     * @return subscribe_by - 关注方式
     */
    public Byte getSubscribeBy() {
        return subscribeBy;
    }

    /**
     * 设置关注方式
     *
     * @param subscribeBy 关注方式
     */
    public void setSubscribeBy(Byte subscribeBy) {
        this.subscribeBy = subscribeBy;
    }

    /**
     * 获取二维码ID
     *
     * @return qrcode_id - 二维码ID
     */
    public Integer getQrcodeId() {
        return qrcodeId;
    }

    /**
     * 设置二维码ID
     *
     * @param qrcodeId 二维码ID
     */
    public void setQrcodeId(Integer qrcodeId) {
        this.qrcodeId = qrcodeId;
    }
}