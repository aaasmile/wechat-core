package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "estore_config")
public class EstoreConfig {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分段、分组key值
     */
    private String segment;

    /**
     * 配置键名
     */
    @Column(name = "`key`")
    private String key;
    @Column(name = "`value`")
    private String value;

    /**
     * 状态（1：启用；0：禁用）
     */
    @Column(name = "`status`")
    private Byte status;

    /**
     * 微信ID
     */
    @Column(name = "wechat_id")
    private Long wechatId;

    /**
     * 获取自增ID
     *
     * @return id - 自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增ID
     *
     * @param id 自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取分段、分组key值
     *
     * @return segment - 分段、分组key值
     */
    public String getSegment() {
        return segment;
    }

    /**
     * 设置分段、分组key值
     *
     * @param segment 分段、分组key值
     */
    public void setSegment(String segment) {
        this.segment = segment == null ? null : segment.trim();
    }

    /**
     * 获取配置键名
     *
     * @return key - 配置键名
     */
    public String getKey() {
        return key;
    }

    /**
     * 设置配置键名
     *
     * @param key 配置键名
     */
    public void setKey(String key) {
        this.key = key == null ? null : key.trim();
    }

    /**
     * @return value
     */
    public String getValue() {
        return value;
    }

    /**
     * @param value
     */
    public void setValue(String value) {
        this.value = value == null ? null : value.trim();
    }

    /**
     * 获取状态（1：启用；0：禁用）
     *
     * @return status - 状态（1：启用；0：禁用）
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态（1：启用；0：禁用）
     *
     * @param status 状态（1：启用；0：禁用）
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取微信ID
     *
     * @return wechat_id - 微信ID
     */
    public Long getWechatId() {
        return wechatId;
    }

    /**
     * 设置微信ID
     *
     * @param wechatId 微信ID
     */
    public void setWechatId(Long wechatId) {
        this.wechatId = wechatId;
    }
}