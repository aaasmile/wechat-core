package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "member_qrcode_invited")
public class MemberQrcodeInvited {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 用户id
     */
    @Column(name = "member_id")
    private Integer memberId;

    /**
     * 邀请人id
     */
    @Column(name = "invited_by")
    private Integer invitedBy;

    /**
     * 状态：1 有效  0 无效
     */
    private Integer status;

    /**
     * 所属微信
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 二维码scene_str
     */
    private String scene;

    @Column(name = "created_at")
    private Date createdAt;

    /**
     * @return id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取用户id
     *
     * @return member_id - 用户id
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * 设置用户id
     *
     * @param memberId 用户id
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取邀请人id
     *
     * @return invited_by - 邀请人id
     */
    public Integer getInvitedBy() {
        return invitedBy;
    }

    /**
     * 设置邀请人id
     *
     * @param invitedBy 邀请人id
     */
    public void setInvitedBy(Integer invitedBy) {
        this.invitedBy = invitedBy;
    }

    /**
     * 获取状态：1 有效  0 无效
     *
     * @return status - 状态：1 有效  0 无效
     */
    public Integer getStatus() {
        return status;
    }

    /**
     * 设置状态：1 有效  0 无效
     *
     * @param status 状态：1 有效  0 无效
     */
    public void setStatus(Integer status) {
        this.status = status;
    }

    /**
     * 获取所属微信
     *
     * @return wechat_id - 所属微信
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * 设置所属微信
     *
     * @param wechatId 所属微信
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    /**
     * 获取二维码scene_str
     *
     * @return scene - 二维码scene_str
     */
    public String getScene() {
        return scene;
    }

    /**
     * 设置二维码scene_str
     *
     * @param scene 二维码scene_str
     */
    public void setScene(String scene) {
        this.scene = scene == null ? null : scene.trim();
    }

    /**
     * @return created_at
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}