package com.d1m.wechat.model;

import javax.persistence.*;

public class Company {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 公司名称
     */
    private String name;

    /**
     * 公司编号
     */
    private String code;

    /**
     *  公司logo链接地址
     */
    private String logo;

    /**
     * 状态（0-删除，1-使用）
     */
    private Byte status;

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
     * 获取公司名称
     *
     * @return name - 公司名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置公司名称
     *
     * @param name 公司名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取公司编号
     *
     * @return code - 公司编号
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置公司编号
     *
     * @param code 公司编号
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取 公司logo链接地址
     *
     * @return logo -  公司logo链接地址
     */
    public String getLogo() {
        return logo;
    }

    /**
     * 设置 公司logo链接地址
     *
     * @param logo  公司logo链接地址
     */
    public void setLogo(String logo) {
        this.logo = logo == null ? null : logo.trim();
    }

    /**
     * 获取状态（0-删除，1-使用）
     *
     * @return status - 状态（0-删除，1-使用）
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态（0-删除，1-使用）
     *
     * @param status 状态（0-删除，1-使用）
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}