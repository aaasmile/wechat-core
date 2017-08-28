package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "action_engine")
public class ActionEngine {
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
     * 状态(0:关闭,1:启用)
     */
    private Byte status;

    /**
     * 有效开始时间
     */
    @Column(name = "start_at")
    private Date startAt;

    /**
     * 有效结束时间
     */
    @Column(name = "end_at")
    private Date endAt;

    /**
     * 运行类型(1:总是运行,2:cron定时）
     */
    @Column(name = "run_type")
    private Byte runType;

    /**
     * 权重
     */
    private Integer weight;

    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 完成条件(json格式)
     */
    private String conditions;

    /**
     * 完成条件sql
     */
    @Column(name = "condition_sql")
    private String conditionSql;

    /**
     * 产生的效果(json格式)
     */
    private String effect;

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
     * 获取创建人
     *
     * @return creator_id - 创建人
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人
     *
     * @param creatorId 创建人
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取状态(0:关闭,1:启用)
     *
     * @return status - 状态(0:关闭,1:启用)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态(0:关闭,1:启用)
     *
     * @param status 状态(0:关闭,1:启用)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取有效开始时间
     *
     * @return start_at - 有效开始时间
     */
    public Date getStartAt() {
        return startAt;
    }

    /**
     * 设置有效开始时间
     *
     * @param startAt 有效开始时间
     */
    public void setStartAt(Date startAt) {
        this.startAt = startAt;
    }

    /**
     * 获取有效结束时间
     *
     * @return end_at - 有效结束时间
     */
    public Date getEndAt() {
        return endAt;
    }

    /**
     * 设置有效结束时间
     *
     * @param endAt 有效结束时间
     */
    public void setEndAt(Date endAt) {
        this.endAt = endAt;
    }

    /**
     * 获取运行类型(1:总是运行,2:cron定时）
     *
     * @return run_type - 运行类型(1:总是运行,2:cron定时）
     */
    public Byte getRunType() {
        return runType;
    }

    /**
     * 设置运行类型(1:总是运行,2:cron定时）
     *
     * @param runType 运行类型(1:总是运行,2:cron定时）
     */
    public void setRunType(Byte runType) {
        this.runType = runType;
    }

    /**
     * 获取权重
     *
     * @return weight - 权重
     */
    public Integer getWeight() {
        return weight;
    }

    /**
     * 设置权重
     *
     * @param weight 权重
     */
    public void setWeight(Integer weight) {
        this.weight = weight;
    }

    /**
     * @return wechat_id
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * @param wechatId
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    /**
     * 获取完成条件(json格式)
     *
     * @return conditions - 完成条件(json格式)
     */
    public String getConditions() {
        return conditions;
    }

    /**
     * 设置完成条件(json格式)
     *
     * @param conditions 完成条件(json格式)
     */
    public void setConditions(String conditions) {
        this.conditions = conditions == null ? null : conditions.trim();
    }

    /**
     * 获取完成条件sql
     *
     * @return condition_sql - 完成条件sql
     */
    public String getConditionSql() {
        return conditionSql;
    }

    /**
     * 设置完成条件sql
     *
     * @param conditionSql 完成条件sql
     */
    public void setConditionSql(String conditionSql) {
        this.conditionSql = conditionSql == null ? null : conditionSql.trim();
    }

    /**
     * 获取产生的效果(json格式)
     *
     * @return effect - 产生的效果(json格式)
     */
    public String getEffect() {
        return effect;
    }

    /**
     * 设置产生的效果(json格式)
     *
     * @param effect 产生的效果(json格式)
     */
    public void setEffect(String effect) {
        this.effect = effect == null ? null : effect.trim();
    }
}