package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "business_category")
public class BusinessCategory {
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
     * 父级ID【0：一级】
     */
    @Column(name = "parent_id")
    private Integer parentId;

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
     * 获取父级ID【0：一级】
     *
     * @return parent_id - 父级ID【0：一级】
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父级ID【0：一级】
     *
     * @param parentId 父级ID【0：一级】
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }
}