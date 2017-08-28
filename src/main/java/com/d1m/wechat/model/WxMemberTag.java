package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "wx_member_tag")
public class WxMemberTag {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 标签ID
     */
    @Column(name = "wx_id")
    private Integer wxId;

    /**
     * 名称
     */
    private String name;

    /**
     * 创建人
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 创建时间
     */
    @Column(name = "creator_at")
    private Date creatorAt;

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
     * 获取标签ID
     *
     * @return wx_id - 标签ID
     */
    public Integer getWxId() {
        return wxId;
    }

    /**
     * 设置标签ID
     *
     * @param wxId 标签ID
     */
    public void setWxId(Integer wxId) {
        this.wxId = wxId;
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
     * 获取创建人
     *
     * @return creator_id - 创建人
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建人
     *
     * @param creatorId 创建人
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取创建时间
     *
     * @return creator_at - 创建时间
     */
    public Date getCreatorAt() {
        return creatorAt;
    }

    /**
     * 设置创建时间
     *
     * @param creatorAt 创建时间
     */
    public void setCreatorAt(Date creatorAt) {
        this.creatorAt = creatorAt;
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