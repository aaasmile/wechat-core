package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "business_result")
public class BusinessResult {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 微信的门店ID
     */
    @Column(name = "poi_id")
    private String poiId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 商户内部ID
     */
    @Column(name = "uniq_id")
    private String uniqId;

    /**
     * 微信门店发送状态（0-失败，1-成功）
     */
    private Byte status;

    /**
     * 推送信息
     */
    private String msg;

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
     * 获取微信的门店ID
     *
     * @return poi_id - 微信的门店ID
     */
    public String getPoiId() {
        return poiId;
    }

    /**
     * 设置微信的门店ID
     *
     * @param poiId 微信的门店ID
     */
    public void setPoiId(String poiId) {
        this.poiId = poiId == null ? null : poiId.trim();
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
     * 获取商户内部ID
     *
     * @return uniq_id - 商户内部ID
     */
    public String getUniqId() {
        return uniqId;
    }

    /**
     * 设置商户内部ID
     *
     * @param uniqId 商户内部ID
     */
    public void setUniqId(String uniqId) {
        this.uniqId = uniqId == null ? null : uniqId.trim();
    }

    /**
     * 获取微信门店发送状态（0-失败，1-成功）
     *
     * @return status - 微信门店发送状态（0-失败，1-成功）
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置微信门店发送状态（0-失败，1-成功）
     *
     * @param status 微信门店发送状态（0-失败，1-成功）
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取推送信息
     *
     * @return msg - 推送信息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置推送信息
     *
     * @param msg 推送信息
     */
    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
    }
}