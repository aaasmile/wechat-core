package com.d1m.wechat.model;

import org.springframework.data.annotation.Id;

import javax.persistence.Column;
import javax.persistence.Table;
import java.util.Date;

/**
 * 素材分类表
 *
 * @program: wechat-core
 * @Date: 2018/12/6 15:40
 * @Author: Liu weilin
 * @Description:
 */
@Table(name = "material_category")
public class MaterialCategory {

    @Id
    @Column(name = "`id`")
    //@KeySql(genId=UUIdGenId.class)
    private String id;

    /**
     * 素材名称
     */
    @Column(name = "name")
    private String name;

    /**
     * 是否删除（0 否，1 是）
     */
    @Column(name = "is_deleted")
    private Boolean isDeleted;

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
     * 创建人
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 最后更新时间
     */
    @Column(name = "laste_updated_at")
    private Date lasteUpdatedAt;

    /**
     * 最后更新人
     */
    @Column(name = "laste_updated_by")
    private Date lasteUpdatedBy;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Boolean getDeleted() {
        return isDeleted;
    }

    public void setDeleted(Boolean deleted) {
        isDeleted = deleted;
    }

    public Integer getWechatId() {
        return wechatId;
    }

    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getCreatorId() {
        return creatorId;
    }

    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    public Date getLasteUpdatedAt() {
        return lasteUpdatedAt;
    }

    public void setLasteUpdatedAt(Date lasteUpdatedAt) {
        this.lasteUpdatedAt = lasteUpdatedAt;
    }

    public Date getLasteUpdatedBy() {
        return lasteUpdatedBy;
    }

    public void setLasteUpdatedBy(Date lasteUpdatedBy) {
        this.lasteUpdatedBy = lasteUpdatedBy;
    }
}
