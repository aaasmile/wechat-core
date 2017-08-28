package com.d1m.wechat.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "mass_conversation")
public class MassConversation {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会话ID
     */
    @Column(name = "conversation_id")
    private Integer conversationId;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    private Integer memberId;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 群发分组的第几批次
     */
    @Column(name = "pi_ci")
    private Integer piCi;

    /**
     * 创建人
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 状态
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
     * 获取会话ID
     *
     * @return conversation_id - 会话ID
     */
    public Integer getConversationId() {
        return conversationId;
    }

    /**
     * 设置会话ID
     *
     * @param conversationId 会话ID
     */
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
    }

    /**
     * 获取会员ID
     *
     * @return member_id - 会员ID
     */
    public Integer getMemberId() {
        return memberId;
    }

    /**
     * 设置会员ID
     *
     * @param memberId 会员ID
     */
    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
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
     * 获取群发分组的第几批次
     *
     * @return pi_ci - 群发分组的第几批次
     */
    public Integer getPiCi() {
        return piCi;
    }

    /**
     * 设置群发分组的第几批次
     *
     * @param piCi 群发分组的第几批次
     */
    public void setPiCi(Integer piCi) {
        this.piCi = piCi;
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
     * 获取状态
     *
     * @return status - 状态
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态
     *
     * @param status 状态
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}