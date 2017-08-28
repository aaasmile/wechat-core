package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "template_result")
public class TemplateResult {
    /**
     * 消息ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    /**
     * openID
     */
    @Column(name = "open_id")
    private String openId;

    /**
     * 微信消息模板ID
     */
    @Column(name = "template_id")
    private String templateId;

    /**
     * 模板跳转链接
     */
    private String url;

    /**
     * 发送时间
     */
    @Column(name = "push_at")
    private Date pushAt;

    /**
     * 发送返回消息ID
     */
    @Column(name = "msg_id")
    private String msgId;

    /**
     * 微信实际创建时间
     */
    @Column(name = "create_at")
    private Date createAt;

    /**
     * 发送状态（1-发送成功，0-发送失败）
     */
    private Byte status;

    /**
     * 回文消息
     */
    private String msg;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 发送内容
     */
    private String data;

    /**
     * 获取消息ID
     *
     * @return id - 消息ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置消息ID
     *
     * @param id 消息ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * 获取openID
     *
     * @return open_id - openID
     */
    public String getOpenId() {
        return openId;
    }

    /**
     * 设置openID
     *
     * @param openId openID
     */
    public void setOpenId(String openId) {
        this.openId = openId == null ? null : openId.trim();
    }

    /**
     * 获取微信消息模板ID
     *
     * @return template_id - 微信消息模板ID
     */
    public String getTemplateId() {
        return templateId;
    }

    /**
     * 设置微信消息模板ID
     *
     * @param templateId 微信消息模板ID
     */
    public void setTemplateId(String templateId) {
        this.templateId = templateId == null ? null : templateId.trim();
    }

    /**
     * 获取模板跳转链接
     *
     * @return url - 模板跳转链接
     */
    public String getUrl() {
        return url;
    }

    /**
     * 设置模板跳转链接
     *
     * @param url 模板跳转链接
     */
    public void setUrl(String url) {
        this.url = url == null ? null : url.trim();
    }

    /**
     * 获取发送时间
     *
     * @return push_at - 发送时间
     */
    public Date getPushAt() {
        return pushAt;
    }

    /**
     * 设置发送时间
     *
     * @param pushAt 发送时间
     */
    public void setPushAt(Date pushAt) {
        this.pushAt = pushAt;
    }

    /**
     * 获取发送返回消息ID
     *
     * @return msg_id - 发送返回消息ID
     */
    public String getMsgId() {
        return msgId;
    }

    /**
     * 设置发送返回消息ID
     *
     * @param msgId 发送返回消息ID
     */
    public void setMsgId(String msgId) {
        this.msgId = msgId == null ? null : msgId.trim();
    }

    /**
     * 获取微信实际创建时间
     *
     * @return create_at - 微信实际创建时间
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置微信实际创建时间
     *
     * @param createAt 微信实际创建时间
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取发送状态（1-发送成功，0-发送失败）
     *
     * @return status - 发送状态（1-发送成功，0-发送失败）
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置发送状态（1-发送成功，0-发送失败）
     *
     * @param status 发送状态（1-发送成功，0-发送失败）
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取回文消息
     *
     * @return msg - 回文消息
     */
    public String getMsg() {
        return msg;
    }

    /**
     * 设置回文消息
     *
     * @param msg 回文消息
     */
    public void setMsg(String msg) {
        this.msg = msg == null ? null : msg.trim();
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
     * 获取发送内容
     *
     * @return data - 发送内容
     */
    public String getData() {
        return data;
    }

    /**
     * 设置发送内容
     *
     * @param data 发送内容
     */
    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}