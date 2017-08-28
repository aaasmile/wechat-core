package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "coupon_member")
public class CouponMember {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 推送状态：0未推送，1已推送
     */
    @Column(name = "push_status")
    private Byte pushStatus;

    @Column(name = "push_at")
    private Date pushAt;

    /**
     * 核销状态(0:未核销,1:已核销)
     */
    private Byte status;

    /**
     * 领取时间
     */
    @Column(name = "receive_at")
    private Date receiveAt;

    /**
     * 核销时间
     */
    @Column(name = "verification_at")
    private Date verificationAt;

    /**
     * 电子券ID
     */
    @Column(name = "coupon_id")
    private Integer couponId;

    /**
     * 活动ID
     */
    @Column(name = "coupon_setting_id")
    private Integer couponSettingId;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    private Integer memberId;

    /**
     * 核销门店
     */
    @Column(name = "business_id")
    private Integer businessId;

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
     * 获取推送状态：0未推送，1已推送
     *
     * @return push_status - 推送状态：0未推送，1已推送
     */
    public Byte getPushStatus() {
        return pushStatus;
    }

    /**
     * 设置推送状态：0未推送，1已推送
     *
     * @param pushStatus 推送状态：0未推送，1已推送
     */
    public void setPushStatus(Byte pushStatus) {
        this.pushStatus = pushStatus;
    }

    /**
     * @return push_at
     */
    public Date getPushAt() {
        return pushAt;
    }

    /**
     * @param pushAt
     */
    public void setPushAt(Date pushAt) {
        this.pushAt = pushAt;
    }

    /**
     * 获取核销状态(0:未核销,1:已核销)
     *
     * @return status - 核销状态(0:未核销,1:已核销)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置核销状态(0:未核销,1:已核销)
     *
     * @param status 核销状态(0:未核销,1:已核销)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取领取时间
     *
     * @return receive_at - 领取时间
     */
    public Date getReceiveAt() {
        return receiveAt;
    }

    /**
     * 设置领取时间
     *
     * @param receiveAt 领取时间
     */
    public void setReceiveAt(Date receiveAt) {
        this.receiveAt = receiveAt;
    }

    /**
     * 获取核销时间
     *
     * @return verification_at - 核销时间
     */
    public Date getVerificationAt() {
        return verificationAt;
    }

    /**
     * 设置核销时间
     *
     * @param verificationAt 核销时间
     */
    public void setVerificationAt(Date verificationAt) {
        this.verificationAt = verificationAt;
    }

    /**
     * 获取电子券ID
     *
     * @return coupon_id - 电子券ID
     */
    public Integer getCouponId() {
        return couponId;
    }

    /**
     * 设置电子券ID
     *
     * @param couponId 电子券ID
     */
    public void setCouponId(Integer couponId) {
        this.couponId = couponId;
    }

    /**
     * 获取活动ID
     *
     * @return coupon_setting_id - 活动ID
     */
    public Integer getCouponSettingId() {
        return couponSettingId;
    }

    /**
     * 设置活动ID
     *
     * @param couponSettingId 活动ID
     */
    public void setCouponSettingId(Integer couponSettingId) {
        this.couponSettingId = couponSettingId;
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
     * 获取核销门店
     *
     * @return business_id - 核销门店
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * 设置核销门店
     *
     * @param businessId 核销门店
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
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