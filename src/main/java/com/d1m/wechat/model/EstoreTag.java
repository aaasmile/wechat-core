package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "estore_tag")
public class EstoreTag {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 标签名称，如“热卖”，“清仓”等
     */
    private String name;

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
     * 获取标签名称，如“热卖”，“清仓”等
     *
     * @return name - 标签名称，如“热卖”，“清仓”等
     */
    public String getName() {
        return name;
    }

    /**
     * 设置标签名称，如“热卖”，“清仓”等
     *
     * @param name 标签名称，如“热卖”，“清仓”等
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
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