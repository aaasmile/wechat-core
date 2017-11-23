package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "material_mini_program")
public class MaterialMiniProgram {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 素材ID
     */
    @Column(name = "material_id")
    private Integer materialId;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 小程序的标题
     */
    private String title;

    /**
     * 小程序的appid
     */
    private String appid;

    /**
     * 小程序的页面路径
     */
    private String pagepath;

    /**
     * 小程序卡片图片的媒体ID
     */
    @Column(name = "thumb_media_id")
    private String thumbMediaId;

    @Column(name = "creator_id")
    private Integer creatorId;

    @Column(name = "created_at")
    private Date createdAt;

    private Byte status;

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
     * 获取素材ID
     *
     * @return material_id - 素材ID
     */
    public Integer getMaterialId() {
        return materialId;
    }

    /**
     * 设置素材ID
     *
     * @param materialId 素材ID
     */
    public void setMaterialId(Integer materialId) {
        this.materialId = materialId;
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
     * 获取小程序的标题
     *
     * @return title - 小程序的标题
     */
    public String getTitle() {
        return title;
    }

    /**
     * 设置小程序的标题
     *
     * @param title 小程序的标题
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取小程序的appid
     *
     * @return appid - 小程序的appid
     */
    public String getAppid() {
        return appid;
    }

    /**
     * 设置小程序的appid
     *
     * @param appid 小程序的appid
     */
    public void setAppid(String appid) {
        this.appid = appid == null ? null : appid.trim();
    }

    /**
     * 获取小程序的页面路径
     *
     * @return pagepath - 小程序的页面路径
     */
    public String getPagepath() {
        return pagepath;
    }

    /**
     * 设置小程序的页面路径
     *
     * @param pagepath 小程序的页面路径
     */
    public void setPagepath(String pagepath) {
        this.pagepath = pagepath == null ? null : pagepath.trim();
    }

    /**
     * 获取小程序卡片图片的媒体ID
     *
     * @return thumb_media_id - 小程序卡片图片的媒体ID
     */
    public String getThumbMediaId() {
        return thumbMediaId;
    }

    /**
     * 设置小程序卡片图片的媒体ID
     *
     * @param thumbMediaId 小程序卡片图片的媒体ID
     */
    public void setThumbMediaId(String thumbMediaId) {
        this.thumbMediaId = thumbMediaId == null ? null : thumbMediaId.trim();
    }

    /**
     * @return creator_id
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * @param creatorId
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
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
}