package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "oauth_url_log")
public class OauthUrlLog {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 授权用户OPENID
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 授权ID
     */
    @Column(name = "oauth_url_id")
    private Integer oauthUrlId;

    /**
     * 跳转URL
     */
    @Column(name = "redirect_url")
    private String redirectUrl;

    /**
     * 来源
     */
    @Column(name = "source")
    private String source;

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
     * 获取授权用户OPENID
     *
     * @return open_id - 授权用户OPENID
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置授权用户OPENID
     *
     * @param openId 授权用户OPENID
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * 获取授权ID
     *
     * @return oauth_url_id - 授权ID
     */
    public Integer getOauthUrlId() {
        return oauthUrlId;
    }

    /**
     * 设置授权ID
     *
     * @param oauthUrlId 授权ID
     */
    public void setOauthUrlId(Integer oauthUrlId) {
        this.oauthUrlId = oauthUrlId;
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

    public String getRedirectUrl() {
        return redirectUrl;
    }

    public void setRedirectUrl(String redirectUrl) {
        this.redirectUrl = redirectUrl;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }
}