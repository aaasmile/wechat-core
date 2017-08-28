package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

public class Session {
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
     * 场次
     */
    private String session;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 人数上限
     */
    @Column(name = "member_limit")
    private Integer memberLimit;

    /**
     * 统计报名人数
     */
    private Integer apply;

    /**
     * 场次状态(0-删除,1-进行中,2-已暂停)
     */
    private Byte status;

    /**
     * 创建者ID
     */
    @Column(name = "creator_id")
    private Integer creatorId;

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
     * 获取场次
     *
     * @return session - 场次
     */
    public String getSession() {
        return session;
    }

    /**
     * 设置场次
     *
     * @param session 场次
     */
    public void setSession(String session) {
        this.session = session == null ? null : session.trim();
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
     * 获取人数上限
     *
     * @return member_limit - 人数上限
     */
    public Integer getMemberLimit() {
        return memberLimit;
    }

    /**
     * 设置人数上限
     *
     * @param memberLimit 人数上限
     */
    public void setMemberLimit(Integer memberLimit) {
        this.memberLimit = memberLimit;
    }

    /**
     * 获取统计报名人数
     *
     * @return apply - 统计报名人数
     */
    public Integer getApply() {
        return apply;
    }

    /**
     * 设置统计报名人数
     *
     * @param apply 统计报名人数
     */
    public void setApply(Integer apply) {
        this.apply = apply;
    }

    /**
     * 获取场次状态(0-删除,1-进行中,2-已暂停)
     *
     * @return status - 场次状态(0-删除,1-进行中,2-已暂停)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置场次状态(0-删除,1-进行中,2-已暂停)
     *
     * @param status 场次状态(0-删除,1-进行中,2-已暂停)
     */
    public void setStatus(Byte status) {
        this.status = status;
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