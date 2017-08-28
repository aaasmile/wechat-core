package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

public class Wechat {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 名称
     */
    private String name;

    /**
     * appid
     */
    private String appid;

    /**
     * appscret
     */
    private String appscret;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    private String remark;

    /**
     * 用户ID
     */
    @Column(name = "user_id")
    private Integer userId;

    private String token;

    private String url;

    @Column(name = "encoding_aes_key")
    private String encodingAesKey;

    /**
     * 开发者微信号
     */
    @Column(name = "open_id")
    private String openId;

    private Byte status;

    /**
     * 微信公众号头像地址
     */
    @Column(name = "head_img_url")
    private String headImgUrl;

    /**
     * 公司ID
     */
    @Column(name = "company_id")
    private Integer companyId;

    /**
     * 优先级
     */
    private Integer priority;

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
     * 获取名称
     *
     * @return name - 名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置名称
     *
     * @param name 名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取appid
     *
     * @return appid - appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 设置appid
     *
     * @param appid appid
     */
    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    /**
     * 获取appscret
     *
     * @return appscret - appscret
     */
    public String getAppscret() {
        return appscret;
    }

    /**
     * 设置appscret
     *
     * @param appscret appscret
     */
    public void setAppscret(String appscret) {
        this.appscret = appscret == null ? null : appscret.trim();
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
     * @return remark
     */
    public String getRemark() {
        return remark;
    }

    /**
     * @param remark
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取用户ID
     *
     * @return user_id - 用户ID
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * 设置用户ID
     *
     * @param userId 用户ID
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return token
     */
    public String getToken() {
        return token;
    }

    /**
     * @param token
     */
    public void setToken(String token) {
        this.token = token == null ? null : token.trim();
    }

    /**
     * @return url
     */
    public String getUrl() {
        return url;
    }

    /**
     * @param url
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * @return encoding_aes_key
     */
    public String getEncodingAesKey() {
        return encodingAesKey;
    }

    /**
     * @param encodingAesKey
     */
    public void setEncodingAesKey(String encodingAesKey) {
        this.encodingAesKey = encodingAesKey == null ? null : encodingAesKey.trim();
    }

    /**
     * 获取开发者微信号
     *
     * @return open_id - 开发者微信号
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置开发者微信号
     *
     * @param openId 开发者微信号
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * @return status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取微信公众号头像地址
     *
     * @return head_img_url - 微信公众号头像地址
     */
    public String getHeadImgUrl() {
        return headImgUrl;
    }

    /**
     * 设置微信公众号头像地址
     *
     * @param headImgUrl 微信公众号头像地址
     */
    public void setHeadImgUrl(String headImgUrl) {
        this.headImgUrl = headImgUrl == null ? null : headImgUrl.trim();
    }

    /**
     * 获取公司ID
     *
     * @return company_id - 公司ID
     */
    public Integer getCompanyId() {
        return companyId;
    }

    /**
     * 设置公司ID
     *
     * @param companyId 公司ID
     */
    public void setCompanyId(Integer companyId) {
        this.companyId = companyId;
    }

    /**
     * 获取优先级
     *
     * @return priority - 优先级
     */
    public Integer getPriority() {
        return priority;
    }

    /**
     * 设置优先级
     *
     * @param priority 优先级
     */
    public void setPriority(Integer priority) {
        this.priority = priority;
    }
}