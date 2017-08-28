package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "member_group")
public class MemberGroup {
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
     * 微信用户组ID
     */
    @Column(name = "wx_group_id")
    private Integer wxGroupId;

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
     * 获取微信用户组ID
     *
     * @return wx_group_id - 微信用户组ID
     */
    public Integer getWxGroupId() {
        return wxGroupId;
    }

    /**
     * 设置微信用户组ID
     *
     * @param wxGroupId 微信用户组ID
     */
    public void setWxGroupId(Integer wxGroupId) {
        this.wxGroupId = wxGroupId;
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
}