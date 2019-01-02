package com.d1m.wechat.model;

import java.io.Serializable;
import java.util.Date;

/**
 * 素材分类表
 *
 * @program: wechat-core
 * @Date: 2018/12/6 15:40
 * @Author: Liu weilin
 * @Description:
 */
public class MaterialCategory implements Serializable {
    private static final long serialVersionUID = 1L;

    //主键ID
    private String id;
    //分类名称
    private String name;
    //是否删除（0 否，1 是）
    private String deleted;
    //公众号ID
    private Integer wechatId;
    //创建时间
    private Date createdAt;
    //创建人
    private Integer createdBy;
    //最后更新时间
    private Date lasteUpdatedAt;
    //最后更新人
    private Integer lasteUpdatedBy;

    //图文数量
    private Integer newsCount;

    public Integer getNewsCount() {
        return newsCount;
    }

    public void setNewsCount(Integer newsCount) {
        this.newsCount = newsCount;
    }

    /**
     * 设置：主键ID
     */
    public void setId(String id) {
        this.id = id;
    }

    /**
     * 获取：主键ID
     */
    public String getId() {
        return id;
    }
    /**
     * 设置：分类名称
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * 获取：分类名称
     */
    public String getName() {
        return name;
    }
    /**
     * 设置：是否删除（0 否，1 是）
     */
    public void setDeleted(String deleted) {
        this.deleted = deleted;
    }

    /**
     * 获取：是否删除（0 否，1 是）
     */
    public String getDeleted() {
        return deleted;
    }
    /**
     * 设置：公众号ID
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    /**
     * 获取：公众号ID
     */
    public Integer getWechatId() {
        return wechatId;
    }
    /**
     * 设置：创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取：创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }
    /**
     * 设置：创建人
     */
    public void setCreatedBy(Integer createdBy) {
        this.createdBy = createdBy;
    }

    /**
     * 获取：创建人
     */
    public Integer getCreatedBy() {
        return createdBy;
    }
    /**
     * 设置：最后更新时间
     */
    public void setLasteUpdatedAt(Date lasteUpdatedAt) {
        this.lasteUpdatedAt = lasteUpdatedAt;
    }

    /**
     * 获取：最后更新时间
     */
    public Date getLasteUpdatedAt() {
        return lasteUpdatedAt;
    }
    /**
     * 设置：最后更新人
     */
    public void setLasteUpdatedBy(Integer lasteUpdatedBy) {
        this.lasteUpdatedBy = lasteUpdatedBy;
    }

    /**
     * 获取：最后更新人
     */
    public Integer getLasteUpdatedBy() {
        return lasteUpdatedBy;
    }
}
