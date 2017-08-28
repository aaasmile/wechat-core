package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "qrcode_action_engine")
public class QrcodeActionEngine {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 二维码ID
     */
    @Column(name = "qrcode_id")
    private Integer qrcodeId;

    /**
     * 行为引擎规则ID
     */
    @Column(name = "action_engine_id")
    private Integer actionEngineId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 创建用户ID
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

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
     * 获取二维码ID
     *
     * @return qrcode_id - 二维码ID
     */
    public Integer getQrcodeId() {
        return qrcodeId;
    }

    /**
     * 设置二维码ID
     *
     * @param qrcodeId 二维码ID
     */
    public void setQrcodeId(Integer qrcodeId) {
        this.qrcodeId = qrcodeId;
    }

    /**
     * 获取行为引擎规则ID
     *
     * @return action_engine_id - 行为引擎规则ID
     */
    public Integer getActionEngineId() {
        return actionEngineId;
    }

    /**
     * 设置行为引擎规则ID
     *
     * @param actionEngineId 行为引擎规则ID
     */
    public void setActionEngineId(Integer actionEngineId) {
        this.actionEngineId = actionEngineId;
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
     * 获取创建用户ID
     *
     * @return creator_id - 创建用户ID
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建用户ID
     *
     * @param creatorId 创建用户ID
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