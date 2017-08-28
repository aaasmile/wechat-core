package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "member_scan_qrcode")
public class MemberScanQrcode {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    private Integer memberId;

    @Column(name = "open_id")
    private String openId;

    @Column(name = "union_id")
    private String unionId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 扫码时的关注状态
     */
    @Column(name = "is_subscribe")
    private Boolean isSubscribe;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 二维码ID
     */
    @Column(name = "qrcode_id")
    private Integer qrcodeId;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
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
     * @return open_id
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * @param openId
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * @return union_id
     */
    public String getUnionId() {
        return unionId;
    }

    /**
     * @param unionId
     */
    public void setUnionId(String unionId) {
        this.unionId = unionId == null ? null : unionId.trim();
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
     * 获取扫码时的关注状态
     *
     * @return is_subscribe - 扫码时的关注状态
     */
    public Boolean getIsSubscribe() {
        return isSubscribe;
    }

    /**
     * 设置扫码时的关注状态
     *
     * @param isSubscribe 扫码时的关注状态
     */
    public void setIsSubscribe(Boolean isSubscribe) {
        this.isSubscribe = isSubscribe;
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