package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "offline_activity_business")
public class OfflineActivityBusiness {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 线下活动ID
     */
    @Column(name = "offline_activity_id")
    private Integer offlineActivityId;

    /**
     * 门店ID
     */
    @Column(name = "business_id")
    private Integer businessId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 创建者ID
     */
    @Column(name = "creator_id")
    private Integer creatorId;

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
     * 获取线下活动ID
     *
     * @return offline_activity_id - 线下活动ID
     */
    public Integer getOfflineActivityId() {
        return offlineActivityId;
    }

    /**
     * 设置线下活动ID
     *
     * @param offlineActivityId 线下活动ID
     */
    public void setOfflineActivityId(Integer offlineActivityId) {
        this.offlineActivityId = offlineActivityId;
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
     * 获取创建者ID
     *
     * @return creator_id - 创建者ID
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建者ID
     *
     * @param creatorId 创建者ID
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }
}