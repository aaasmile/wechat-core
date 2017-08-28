package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "business_photo")
public class BusinessPhoto {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 门店ID
     */
    @Column(name = "business_id")
    private Integer businessId;

    /**
     * 本地图片路径
     */
    @Column(name = "photo_url")
    private String photoUrl;

    /**
     * 微信上传路径
     */
    @Column(name = "wx_url")
    private String wxUrl;

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
     * 获取门店ID
     *
     * @return business_id - 门店ID
     */
    public Integer getBusinessId() {
        return businessId;
    }

    /**
     * 设置门店ID
     *
     * @param businessId 门店ID
     */
    public void setBusinessId(Integer businessId) {
        this.businessId = businessId;
    }

    /**
     * 获取本地图片路径
     *
     * @return photo_url - 本地图片路径
     */
    public String getPhotoUrl() {
        return photoUrl;
    }

    /**
     * 设置本地图片路径
     *
     * @param photoUrl 本地图片路径
     */
    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl == null ? null : photoUrl.trim();
    }

    /**
     * 获取微信上传路径
     *
     * @return wx_url - 微信上传路径
     */
    public String getWxUrl() {
        return wxUrl;
    }

    /**
     * 设置微信上传路径
     *
     * @param wxUrl 微信上传路径
     */
    public void setWxUrl(String wxUrl) {
        this.wxUrl = wxUrl == null ? null : wxUrl.trim();
    }
}