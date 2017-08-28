package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "conversation_index")
public class ConversationIndex {
    /**
     * 主键id
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * 微信消息id
     */
    @Column(name = "msg_id")
    private Long msgId;

    /**
     * openId
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 会话时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 获取主键id
     *
     * @return id - 主键id
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键id
     *
     * @param id 主键id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取微信消息id
     *
     * @return msg_id - 微信消息id
     */
    public Long getMsgId() {
        return msgId;
    }

    /**
     * 设置微信消息id
     *
     * @param msgId 微信消息id
     */
    public void setMsgId(Long msgId) {
        this.msgId = msgId;
    }

    /**
     * 获取openId
     *
     * @return open_id - openId
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置openId
     *
     * @param openId openId
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * 获取会话时间
     *
     * @return created_at - 会话时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置会话时间
     *
     * @param createdAt 会话时间
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