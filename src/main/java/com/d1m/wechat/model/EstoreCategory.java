package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "estore_category")
public class EstoreCategory {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 分类名称
     */
    private String name;

    /**
     * 是否父亲节点（1：是；0：否）
     */
    @Column(name = "is_parent")
    private Byte isParent;

    /**
     * 上级节点ID
     */
    @Column(name = "parent_id")
    private Long parentId;

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
     * 获取分类名称
     *
     * @return name - 分类名称
     */
    public String getName() {
        return name;
    }

    /**
     * 设置分类名称
     *
     * @param name 分类名称
     */
    public void setName(String name) {
        this.name = name == null ? null : name.trim();
    }

    /**
     * 获取是否父亲节点（1：是；0：否）
     *
     * @return is_parent - 是否父亲节点（1：是；0：否）
     */
    public Byte getIsParent() {
        return isParent;
    }

    /**
     * 设置是否父亲节点（1：是；0：否）
     *
     * @param isParent 是否父亲节点（1：是；0：否）
     */
    public void setIsParent(Byte isParent) {
        this.isParent = isParent;
    }

    /**
     * 获取上级节点ID
     *
     * @return parent_id - 上级节点ID
     */
    public Long getParentId() {
        return parentId;
    }

    /**
     * 设置上级节点ID
     *
     * @param parentId 上级节点ID
     */
    public void setParentId(Long parentId) {
        this.parentId = parentId;
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