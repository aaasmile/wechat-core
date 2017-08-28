package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "member_reply")
public class MemberReply {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 会员ID
     */
    @Column(name = "member_id")
    private Integer memberId;

    /**
     * 自动回复ID
     */
    @Column(name = "reply_id")
    private Integer replyId;

    /**
     * 会话ID
     */
    @Column(name = "conversation_id")
    private Integer conversationId;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

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
     * 获取自动回复ID
     *
     * @return reply_id - 自动回复ID
     */
    public Integer getReplyId() {
        return replyId;
    }

    /**
     * 设置自动回复ID
     *
     * @param replyId 自动回复ID
     */
    public void setReplyId(Integer replyId) {
        this.replyId = replyId;
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