package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

public class Menu {
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
     * 动作类型
     */
    private Byte type;

    /**
     * 点击类型KEY值
     */
    @Column(name = "menu_key")
    private Integer menuKey;

    /**
     * 网页链接
     */
    private String url;

    /**
     * 永久素材mediaId
     */
    @Column(name = "media_id")
    private Integer mediaId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 更新时间
     */
    @Column(name = "modify_at")
    private Date modifyAt;

    /**
     * 创建用户ID
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 菜单组ID
     */
    @Column(name = "menu_group_id")
    private Integer menuGroupId;

    /**
     * 父级菜单ID
     */
    @Column(name = "parent_id")
    private Integer parentId;

    /**
     * 状态(0:删除,1:使用)
     */
    private Byte status;

    /**
     * 删除时间
     */
    @Column(name = "deleted_at")
    private Date deletedAt;

    /**
     * 删除用户ID
     */
    @Column(name = "deletor_id")
    private Integer deletorId;

    /**
     * 顺序
     */
    private Integer seq;

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
     * 获取动作类型
     *
     * @return type - 动作类型
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置动作类型
     *
     * @param type 动作类型
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取点击类型KEY值
     *
     * @return menu_key - 点击类型KEY值
     */
    public Integer getMenuKey() {
        return menuKey;
    }

    /**
     * 设置点击类型KEY值
     *
     * @param menuKey 点击类型KEY值
     */
    public void setMenuKey(Integer menuKey) {
        this.menuKey = menuKey;
    }

    /**
     * 获取网页链接
     *
     * @return url - 网页链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置网页链接
     *
     * @param url 网页链接
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取永久素材mediaId
     *
     * @return media_id - 永久素材mediaId
     */
    public Integer getMediaId() {
        return mediaId;
    }

    /**
     * 设置永久素材mediaId
     *
     * @param mediaId 永久素材mediaId
     */
    public void setMediaId(Integer mediaId) {
        this.mediaId = mediaId;
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
     * 获取更新时间
     *
     * @return modify_at - 更新时间
     */
    public Date getModifyAt() {
        return modifyAt;
    }

    /**
     * 设置更新时间
     *
     * @param modifyAt 更新时间
     */
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
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

    /**
     * 获取菜单组ID
     *
     * @return menu_group_id - 菜单组ID
     */
    public Integer getMenuGroupId() {
        return menuGroupId;
    }

    /**
     * 设置菜单组ID
     *
     * @param menuGroupId 菜单组ID
     */
    public void setMenuGroupId(Integer menuGroupId) {
        this.menuGroupId = menuGroupId;
    }

    /**
     * 获取父级菜单ID
     *
     * @return parent_id - 父级菜单ID
     */
    public Integer getParentId() {
        return parentId;
    }

    /**
     * 设置父级菜单ID
     *
     * @param parentId 父级菜单ID
     */
    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    /**
     * 获取状态(0:删除,1:使用)
     *
     * @return status - 状态(0:删除,1:使用)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态(0:删除,1:使用)
     *
     * @param status 状态(0:删除,1:使用)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取删除时间
     *
     * @return deleted_at - 删除时间
     */
    public Date getDeletedAt() {
        return deletedAt;
    }

    /**
     * 设置删除时间
     *
     * @param deletedAt 删除时间
     */
    public void setDeletedAt(Date deletedAt) {
        this.deletedAt = deletedAt;
    }

    /**
     * 获取删除用户ID
     *
     * @return deletor_id - 删除用户ID
     */
    public Integer getDeletorId() {
        return deletorId;
    }

    /**
     * 设置删除用户ID
     *
     * @param deletorId 删除用户ID
     */
    public void setDeletorId(Integer deletorId) {
        this.deletorId = deletorId;
    }

    /**
     * 获取顺序
     *
     * @return seq - 顺序
     */
    public Integer getSeq() {
        return seq;
    }

    /**
     * 设置顺序
     *
     * @param seq 顺序
     */
    public void setSeq(Integer seq) {
        this.seq = seq;
    }
}