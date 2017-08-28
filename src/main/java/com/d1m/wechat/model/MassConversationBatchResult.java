package com.d1m.wechat.model;

import javax.persistence.*;
import java.util.Date;

@Table(name = "mass_conversation_batch_result")
public class MassConversationBatchResult {
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
     * 群发消息ID
     */
    @Column(name = "msg_id")
    private String msgId;

    /**
     * 群发分组的第几批次
     */
    @Column(name = "pi_ci")
    private Integer piCi;

    /**
     * 结果
     */
    private Byte status;

    /**
     * 消息类型
     */
    @Column(name = "msg_type")
    private Byte msgType;

    /**
     * 群发内容
     */
    @Column(name = "msg_content")
    private String msgContent;

    /**
     * tag_id下粉丝数；或者openid_list中的粉丝数
     */
    @Column(name = "total_count")
    private Integer totalCount;

    /**
     * 过滤后准备发送的粉丝数
     */
    @Column(name = "filter_count")
    private Integer filterCount;

    /**
     * 发送成功的粉丝数
     */
    @Column(name = "send_count")
    private Integer sendCount;

    /**
     * 发送失败的粉丝数
     */
    @Column(name = "error_count")
    private Integer errorCount;

    /**
     * 群发会话ID
     */
    @Column(name = "conversation_id")
    private Integer conversationId;

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
     * 获取群发消息ID
     *
     * @return msg_id - 群发消息ID
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * 设置群发消息ID
     *
     * @param msgId 群发消息ID
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
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
     * 获取结果
     *
     * @return status - 结果
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置结果
     *
     * @param status 结果
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取tag_id下粉丝数；或者openid_list中的粉丝数
     *
     * @return total_count - tag_id下粉丝数；或者openid_list中的粉丝数
     */
    public Integer getTotalCount() {
        return totalCount;
    }

    /**
     * 设置tag_id下粉丝数；或者openid_list中的粉丝数
     *
     * @param totalCount tag_id下粉丝数；或者openid_list中的粉丝数
     */
    public void setTotalCount(Integer totalCount) {
        this.totalCount = totalCount;
    }

    /**
     * 获取过滤后准备发送的粉丝数
     *
     * @return filter_count - 过滤后准备发送的粉丝数
     */
    public Integer getFilterCount() {
        return filterCount;
    }

    /**
     * 设置过滤后准备发送的粉丝数
     *
     * @param filterCount 过滤后准备发送的粉丝数
     */
    public void setFilterCount(Integer filterCount) {
        this.filterCount = filterCount;
    }

    /**
     * 获取发送成功的粉丝数
     *
     * @return send_count - 发送成功的粉丝数
     */
    public Integer getSendCount() {
        return sendCount;
    }

    /**
     * 设置发送成功的粉丝数
     *
     * @param sendCount 发送成功的粉丝数
     */
    public void setSendCount(Integer sendCount) {
        this.sendCount = sendCount;
    }

    /**
     * 获取发送失败的粉丝数
     *
     * @return error_count - 发送失败的粉丝数
     */
    public Integer getErrorCount() {
        return errorCount;
    }

    /**
     * 设置发送失败的粉丝数
     *
     * @param errorCount 发送失败的粉丝数
     */
    public void setErrorCount(Integer errorCount) {
        this.errorCount = errorCount;
    }

    /**
     * 获取群发会话ID
     *
     * @return conversation_id - 群发会话ID
     */
    public Integer getConversationId() {
        return conversationId;
    }

    /**
     * 设置群发会话ID
     *
     * @param conversationId 群发会话ID
     */
    public void setConversationId(Integer conversationId) {
        this.conversationId = conversationId;
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

    public Byte getMsgType() {
        return msgType;
    }

    public void setMsgType(Byte msgType) {
        this.msgType = msgType;
    }

    public String getMsgContent() {
        return msgContent;
    }

    public void setMsgContent(String msgContent) {
        this.msgContent = msgContent;
    }
}