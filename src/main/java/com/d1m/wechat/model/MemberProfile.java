package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "member_profile")
public class MemberProfile {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会员卡号
     */
    @Column(name = "card_id")
    private String cardId;

    /**
     * 会员卡类型
     */
    @Column(name = "card_type")
    private Byte cardType;

    /**
     * 到期时间
     */
    @Column(name = "issue_date")
    private Date issueDate;

    /**
     * 会员卡状态
     */
    @Column(name = "card_status")
    private Byte cardStatus;

    /**
     * 门店ID
     */
    @Column(name = "store_id")
    private String storeId;

    /**
     * 积分
     */
    private Integer credits;

    /**
     * 等级
     */
    private String level;

    /**
     * 会员姓名
     */
    private String name;

    /**
     * 会员地址
     */
    private String address;

    /**
     * 会员生日
     */
    @Column(name = "birth_date")
    private Date birthDate;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    private Integer memberId;

    /**
     * 绑定状态(0:已解绑,1:已绑定)
     */
    private Byte status;

    /**
     * 所属公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 接受促销信息
     */
    @Column(name = "accept_promotion")
    private Boolean acceptPromotion;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 绑定时间
     */
    @Column(name = "bind_at")
    private Date bindAt;

    /**
     * 解绑时间
     */
    @Column(name = "unbund_at")
    private Date unbundAt;

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
     * 获取会员卡号
     *
     * @return card_id - 会员卡号
     */
    public String getCardId() {
        return cardId;
    }

    /**
     * 设置会员卡号
     *
     * @param cardId 会员卡号
     */
    public void setCardId(String cardId) {
        this.cardId = cardId == null ? null : cardId.trim();
    }

    /**
     * 获取会员卡类型
     *
     * @return card_type - 会员卡类型
     */
    public Byte getCardType() {
        return cardType;
    }

    /**
     * 设置会员卡类型
     *
     * @param cardType 会员卡类型
     */
    public void setCardType(Byte cardType) {
        this.cardType = cardType;
    }

    /**
     * 获取到期时间
     *
     * @return issue_date - 到期时间
     */
    public Date getIssueDate() {
        return issueDate;
    }

    /**
     * 设置到期时间
     *
     * @param issueDate 到期时间
     */
    public void setIssueDate(Date issueDate) {
        this.issueDate = issueDate;
    }

    /**
     * 获取会员卡状态
     *
     * @return card_status - 会员卡状态
     */
    public Byte getCardStatus() {
        return cardStatus;
    }

    /**
     * 设置会员卡状态
     *
     * @param cardStatus 会员卡状态
     */
    public void setCardStatus(Byte cardStatus) {
        this.cardStatus = cardStatus;
    }

    /**
     * 获取门店ID
     *
     * @return store_id - 门店ID
     */
    public String getStoreId() {
        return storeId;
    }

    /**
     * 设置门店ID
     *
     * @param storeId 门店ID
     */
    public void setStoreId(String storeId) {
        this.storeId = storeId == null ? null : storeId.trim();
    }

    /**
     * 获取积分
     *
     * @return credits - 积分
     */
    public Integer getCredits() {
        return credits;
    }

    /**
     * 设置积分
     *
     * @param credits 积分
     */
    public void setCredits(Integer credits) {
        this.credits = credits;
    }

    /**
     * 获取等级
     *
     * @return level - 等级
     */
    public String getLevel() {
        return level;
    }

    /**
     * 设置等级
     *
     * @param level 等级
     */
    public void setLevel(String level) {
        this.level = level;
    }

    /**
     * 获取会员姓名
     *
     * @return name - 会员姓名
     */
    public String getName() {
        return name;
    }

    /**
     * 设置会员姓名
     *
     * @param name 会员姓名
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取会员地址
     *
     * @return address - 会员地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置会员地址
     *
     * @param address 会员地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取会员生日
     *
     * @return birth_date - 会员生日
     */
    public Date getBirthDate() {
        return birthDate;
    }

    /**
     * 设置会员生日
     *
     * @param birthDate 会员生日
     */
    public void setBirthDate(Date birthDate) {
        this.birthDate = birthDate;
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
     * 获取绑定状态(0:已解绑,1:已绑定)
     *
     * @return status - 绑定状态(0:已解绑,1:已绑定)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置绑定状态(0:已解绑,1:已绑定)
     *
     * @param status 绑定状态(0:已解绑,1:已绑定)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取所属公众号ID
     *
     * @return wechat_id - 所属公众号ID
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * 设置所属公众号ID
     *
     * @param wechatId 所属公众号ID
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    /**
     * 获取接受促销信息
     *
     * @return accept_promotion - 接受促销信息
     */
    public Boolean getAcceptPromotion() {
        return acceptPromotion;
    }

    /**
     * 设置接受促销信息
     *
     * @param acceptPromotion 接受促销信息
     */
    public void setAcceptPromotion(Boolean acceptPromotion) {
        this.acceptPromotion = acceptPromotion;
    }

    /**
     * 获取邮箱
     *
     * @return email - 邮箱
     */
    public String getEmail() {
        return email;
    }

    /**
     * 设置邮箱
     *
     * @param email 邮箱
     */
    public void setEmail(String email) {
        this.email = email == null ? null : email.trim();
    }

    /**
     * 获取绑定时间
     *
     * @return bind_at - 绑定时间
     */
    public Date getBindAt() {
        return bindAt;
    }

    /**
     * 设置绑定时间
     *
     * @param bindAt 绑定时间
     */
    public void setBindAt(Date bindAt) {
        this.bindAt = bindAt;
    }

    /**
     * 获取解绑时间
     *
     * @return unbund_at - 解绑时间
     */
    public Date getUnbundAt() {
        return unbundAt;
    }

    /**
     * 设置解绑时间
     *
     * @param unbundAt 解绑时间
     */
    public void setUnbundAt(Date unbundAt) {
        this.unbundAt = unbundAt;
    }
}