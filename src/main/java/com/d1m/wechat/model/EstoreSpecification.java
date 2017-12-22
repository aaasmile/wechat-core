package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "estore_specification")
public class EstoreSpecification {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 规格名称
     */
    private String name;

    /**
     * 规格值列表（半角逗号分隔）
     */
    private String values;

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
     * 获取规格名称
     *
     * @return name - 规格名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置规格名称
     *
     * @param name 规格名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取规格值列表（半角逗号分隔）
     *
     * @return values - 规格值列表（半角逗号分隔）
     */
    public String getValues() {
        return values;
    }

    /**
     * 设置规格值列表（半角逗号分隔）
     *
     * @param values 规格值列表（半角逗号分隔）
     */
    public void setValues(String values) {
        this.values = values == null ? null : values.trim();
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