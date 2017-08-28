package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "verify_token")
public class VerifyToken {
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
     * token
     */
    private String token;

    /**
     * code
     */
    private String code;

    /**
     * 是否已验证
     */
    private Boolean verified;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 失效时间
     */
    @Column(name = "expired_on")
    private Date expiredOn;

    /**
     * 接受对象(手机或者邮箱)
     */
    private String accept;

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
     * 获取token
     *
     * @return token - token
     */
    public String getToken() {
        return token;
    }

    /**
     * 设置token
     *
     * @param token token
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * 获取code
     *
     * @return code - code
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置code
     *
     * @param code code
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取是否已验证
     *
     * @return verified - 是否已验证
     */
    public Boolean getVerified() {
        return verified;
    }

    /**
     * 设置是否已验证
     *
     * @param verified 是否已验证
     */
    public void setVerified(Boolean verified) {
        this.verified = verified;
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
     * 获取失效时间
     *
     * @return expired_on - 失效时间
     */
    public Date getExpiredOn() {
        return expiredOn;
    }

    /**
     * 设置失效时间
     *
     * @param expiredOn 失效时间
     */
    public void setExpiredOn(Date expiredOn) {
        this.expiredOn = expiredOn;
    }

    /**
     * 获取接受对象(手机或者邮箱)
     *
     * @return accept - 接受对象(手机或者邮箱)
     */
    public String getAccept() {
        return accept;
    }

    /**
     * 设置接受对象(手机或者邮箱)
     *
     * @param accept 接受对象(手机或者邮箱)
     */
    public void setAccept(String accept) {
        this.accept = accept == null ? null : accept.trim();
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