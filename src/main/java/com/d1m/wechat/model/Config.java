package com.d1m.wechat.model;

import javax.persistence.*;

public class Config {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 组名code
     */
    @Column(name = "cfg_group")
    private String cfgGroup;

    /**
     * 组名label
     */
    @Column(name = "cfg_group_label")
    private String cfgGroupLabel;

    /**
     * 键名label
     */
    @Column(name = "cfg_key_label")
    private String cfgKeyLabel;

    /**
     * 键名code
     */
    @Column(name = "cfg_key")
    private String cfgKey;

    /**
     * 键值
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
     * 获取组名code
     *
     * @return cfg_group - 组名code
     */
    public String getCfgGroup() {
        return cfgGroup;
    }

    /**
     * 设置组名code
     *
     * @param cfgGroup 组名code
     */
    public void setCfgGroup(String cfgGroup) {
        this.cfgGroup = cfgGroup == null ? null : cfgGroup.trim();
    }

    /**
     * 获取组名label
     *
     * @return cfg_group_label - 组名label
     */
    public String getCfgGroupLabel() {
        return cfgGroupLabel;
    }

    /**
     * 设置组名label
     *
     * @param cfgGroupLabel 组名label
     */
    public void setCfgGroupLabel(String cfgGroupLabel) {
        this.cfgGroupLabel = cfgGroupLabel == null ? null : cfgGroupLabel.trim();
    }

    /**
     * 获取键名label
     *
     * @return cfg_key_label - 键名label
     */
    public String getCfgKeyLabel() {
        return cfgKeyLabel;
    }

    /**
     * 设置键名label
     *
     * @param cfgKeyLabel 键名label
     */
    public void setCfgKeyLabel(String cfgKeyLabel) {
        this.cfgKeyLabel = cfgKeyLabel == null ? null : cfgKeyLabel.trim();
    }

    /**
     * 获取键名code
     *
     * @return cfg_key - 键名code
     */
    public String getCfgKey() {
        return cfgKey;
    }

    /**
     * 设置键名code
     *
     * @param cfgKey 键名code
     */
    public void setCfgKey(String cfgKey) {
        this.cfgKey = cfgKey == null ? null : cfgKey.trim();
    }

    /**
     * 获取键值
     *
     * @return cfg_value - 键值
     */
    public String getCfgValue() {
        return cfgValue;
    }

    /**
     * 设置键值
     *
     * @param cfgValue 键值
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