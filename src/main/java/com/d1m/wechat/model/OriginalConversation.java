package com.d1m.wechat.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.*;

@Table(name = "original_conversation")
public class OriginalConversation implements Serializable{
	private static final long serialVersionUID = 1999178666000101959L;

	/**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

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
     * 状态
     */
    private Byte status;

    /**
     * 会话处理开始时间
     */
    @Column(name = "execute_start")
    private Date executeStart;

    /**
     * 会话处理结束时间
     */
    @Column(name = "execute_end")
    private Date executeEnd;

    /**
     * 会话处理时间(毫秒)
     */
    @Column(name = "execute_millis")
    private Integer executeMillis;

    /**
     * 原始xml
     */
    private String content;

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

    /**
     * 获取会话处理开始时间
     *
     * @return execute_start - 会话处理开始时间
     */
    public Date getExecuteStart() {
        return executeStart;
    }

    /**
     * 设置会话处理开始时间
     *
     * @param executeStart 会话处理开始时间
     */
    public void setExecuteStart(Date executeStart) {
        this.executeStart = executeStart;
    }

    /**
     * 获取会话处理结束时间
     *
     * @return execute_end - 会话处理结束时间
     */
    public Date getExecuteEnd() {
        return executeEnd;
    }

    /**
     * 设置会话处理结束时间
     *
     * @param executeEnd 会话处理结束时间
     */
    public void setExecuteEnd(Date executeEnd) {
        this.executeEnd = executeEnd;
    }

    /**
     * 获取会话处理时间(毫秒)
     *
     * @return execute_millis - 会话处理时间(毫秒)
     */
    public Integer getExecuteMillis() {
        return executeMillis;
    }

    /**
     * 设置会话处理时间(毫秒)
     *
     * @param executeMillis 会话处理时间(毫秒)
     */
    public void setExecuteMillis(Integer executeMillis) {
        this.executeMillis = executeMillis;
    }

    /**
     * 获取原始xml
     *
     * @return content - 原始xml
     */
    public String getContent() {
        return content;
    }

    /**
     * 设置原始xml
     *
     * @param content 原始xml
     */
    public void setContent(String content) {
        this.content = content == null ? null : content.trim();
    }
}