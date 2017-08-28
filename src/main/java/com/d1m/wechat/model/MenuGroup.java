package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "menu_group")
public class MenuGroup {
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
     * 个性化菜单规则ID
     */
    @Column(name = "menu_rule_id")
    private Integer menuRuleId;

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
     * 微信同步推送时间
     */
    @Column(name = "push_at")
    private Date pushAt;

    /**
     * 微信菜单组ID
     */
    @Column(name = "wx_menu_id")
    private String wxMenuId;

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
     * 获取个性化菜单规则ID
     *
     * @return menu_rule_id - 个性化菜单规则ID
     */
    public Integer getMenuRuleId() {
        return menuRuleId;
    }

    /**
     * 设置个性化菜单规则ID
     *
     * @param menuRuleId 个性化菜单规则ID
     */
    public void setMenuRuleId(Integer menuRuleId) {
        this.menuRuleId = menuRuleId;
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
     * 获取微信同步推送时间
     *
     * @return push_at - 微信同步推送时间
     */
    public Date getPushAt() {
        return pushAt;
    }

    /**
     * 设置微信同步推送时间
     *
     * @param pushAt 微信同步推送时间
     */
    public void setPushAt(Date pushAt) {
        this.pushAt = pushAt;
    }

    /**
     * 获取微信菜单组ID
     *
     * @return wx_menu_id - 微信菜单组ID
     */
    public String getWxMenuId() {
        return wxMenuId;
    }

    /**
     * 设置微信菜单组ID
     *
     * @param wxMenuId 微信菜单组ID
     */
    public void setWxMenuId(String wxMenuId) {
        this.wxMenuId = wxMenuId == null ? null : wxMenuId.trim();
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
}