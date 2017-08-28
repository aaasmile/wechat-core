package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

public class Function {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 父级ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 功能代码
     */
    private String code;

    /**
     * 功能名称-中文
     */
    @Column(name = "name_cn")
    private String nameCn;

    /**
     * 功能名称-英文
     */
    @Column(name = "name_en")
    private String nameEn;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 创建用户ID
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 角色ID
     */
    @Column(name = "role_id")
    private Integer roleId;

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
     * 获取父级ID
     *
     * @return parent_id - 父级ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父级ID
     *
     * @param parentId 父级ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取功能代码
     *
     * @return code - 功能代码
     */
    public String getCode() {
        return code;
    }

    /**
     * 设置功能代码
     *
     * @param code 功能代码
     */
    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    /**
     * 获取功能名称-中文
     *
     * @return name_cn - 功能名称-中文
     */
    public String getNameCn() {
        return nameCn;
    }

    /**
     * 设置功能名称-中文
     *
     * @param nameCn 功能名称-中文
     */
    public void setNameCn(String nameCn) {
        this.nameCn = nameCn == null ? null : nameCn.trim();
    }

    /**
     * 获取功能名称-英文
     *
     * @return name_en - 功能名称-英文
     */
    public String getNameEn() {
        return nameEn;
    }

    /**
     * 设置功能名称-英文
     *
     * @param nameEn 功能名称-英文
     */
    public void setNameEn(String nameEn) {
        this.nameEn = nameEn == null ? null : nameEn.trim();
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
     * 获取创建用户ID
     *
     * @return creator_id - 创建用户ID
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建用户ID
     *
     * @param creatorId 创建用户ID
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取角色ID
     *
     * @return role_id - 角色ID
     */
    public Integer getRoleId() {
        return roleId;
    }

    /**
     * 设置角色ID
     *
     * @param roleId 角色ID
     */
    public void setRoleId(Integer roleId) {
        this.roleId = roleId;
    }
}
