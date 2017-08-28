package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "customer_service_config")
public class CustomerServiceConfig {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 组名
     */
    @Column(name = "cfg_group")
    private String cfgGroup;

    /**
     * 组名标签
     */
    @Column(name = "cfg_group_label")
    private String cfgGroupLabel;

    /**
     * key
     */
    @Column(name = "cfg_key")
    private String cfgKey;

    /**
     * key标签
     */
    @Column(name = "cfg_key_label")
    private String cfgKeyLabel;

    /**
     * 值
     */
    @Column(name = "cfg_value")
    private String cfgValue;

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
     * 获取组名
     *
     * @return cfg_group - 组名
     */
    public String getCfgGroup() {
        return cfgGroup;
    }

    /**
     * 设置组名
     *
     * @param cfgGroup 组名
     */
    public void setCfgGroup(String cfgGroup) {
        this.cfgGroup = cfgGroup == null ? null : cfgGroup.trim();
    }

    /**
     * 获取组名标签
     *
     * @return cfg_group_label - 组名标签
     */
    public String getCfgGroupLabel() {
        return cfgGroupLabel;
    }

    /**
     * 设置组名标签
     *
     * @param cfgGroupLabel 组名标签
     */
    public void setCfgGroupLabel(String cfgGroupLabel) {
        this.cfgGroupLabel = cfgGroupLabel == null ? null : cfgGroupLabel.trim();
    }

    /**
     * 获取key
     *
     * @return cfg_key - key
     */
    public String getCfgKey() {
        return cfgKey;
    }

    /**
     * 设置key
     *
     * @param cfgKey key
     */
    public void setCfgKey(String cfgKey) {
        this.cfgKey = cfgKey == null ? null : cfgKey.trim();
    }

    /**
     * 获取key标签
     *
     * @return cfg_key_label - key标签
     */
    public String getCfgKeyLabel() {
        return cfgKeyLabel;
    }

    /**
     * 设置key标签
     *
     * @param cfgKeyLabel key标签
     */
    public void setCfgKeyLabel(String cfgKeyLabel) {
        this.cfgKeyLabel = cfgKeyLabel == null ? null : cfgKeyLabel.trim();
    }

    /**
     * 获取值
     *
     * @return cfg_value - 值
     */
    public String getCfgValue() {
        return cfgValue;
    }

    /**
     * 设置值
     *
     * @param cfgValue 值
     */
    public void setCfgValue(String cfgValue) {
        this.cfgValue = cfgValue == null ? null : cfgValue.trim();
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