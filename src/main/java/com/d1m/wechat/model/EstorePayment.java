package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "estore_payment")
public class EstorePayment {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 支付方式名称
     */
    private String name;

    /**
     * 唯一标识符
     */
    private String code;

    /**
     * 支付相关配置（JSON格式）
     */
    private String config;

    /**
     * 是否启用（1：是；0：否）
     */
    private Byte enable;

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
     * 获取支付方式名称
     *
     * @return name - 支付方式名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置支付方式名称
     *
     * @param name 支付方式名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取唯一标识符
     *
     * @return code - 唯一标识符
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置唯一标识符
     *
     * @param code 唯一标识符
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取支付相关配置（JSON格式）
     *
     * @return config - 支付相关配置（JSON格式）
     */
    public String getConfig() {
        return config;
    }

    /**
     * 设置支付相关配置（JSON格式）
     *
     * @param config 支付相关配置（JSON格式）
     */
    public void setConfig(String config) {
        this.config = config == null ? null : config.trim();
    }

    /**
     * 获取是否启用（1：是；0：否）
     *
     * @return enable - 是否启用（1：是；0：否）
     */
    public Byte getEnable() {
        return enable;
    }

    /**
     * 设置是否启用（1：是；0：否）
     *
     * @param enable 是否启用（1：是；0：否）
     */
    public void setEnable(Byte enable) {
        this.enable = enable;
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