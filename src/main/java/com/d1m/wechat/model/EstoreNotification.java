package com.d1m.wechat.model;

import javax.persistence.*;

@Table(name = "estore_notification")
public class EstoreNotification {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 事件KEY，订单HOOK用
     */
    @Column(name = "event_key")
    private String eventKey;

    /**
     * 事件名称
     */
    @Column(name = "event_name")
    private String eventName;

    /**
     * 通知对象（1：顾客；2：商家；3：开发人员）
     */
    @Column(name = "notify_object")
    private Byte notifyObject;

    /**
     * 消息模板（统一变量库处理）
     */
    @Column(name = "notify_tpl")
    private String notifyTpl;

    /**
     * 通知方式（1：SMS；2：模板消息，3、邮件）
     */
    private Byte type;

    /**
     * 是否启用（1：启用；0：禁用）
     */
    private Byte enable;

    /**
     * 微信ID
     */
    @Column(name = "wechat_id")
    private Long wechatId;

    /**
     * 获取自增ID
     *
     * @return id - 自增ID
     */
    public Long getId() {
        return id;
    }

    /**
     * 设置自增ID
     *
     * @param id 自增ID
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * 获取事件KEY，订单HOOK用
     *
     * @return event_key - 事件KEY，订单HOOK用
     */
    public String getEventKey() {
        return eventKey;
    }

    /**
     * 设置事件KEY，订单HOOK用
     *
     * @param eventKey 事件KEY，订单HOOK用
     */
    public void setEventKey(String eventKey) {
        this.eventKey = eventKey == null ? null : eventKey.trim();
    }

    /**
     * 获取事件名称
     *
     * @return event_name - 事件名称
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * 设置事件名称
     *
     * @param eventName 事件名称
     */
    public void setEventName(String eventName) {
        this.eventName = eventName == null ? null : eventName.trim();
    }

    /**
     * 获取通知对象（1：顾客；2：商家；3：开发人员）
     *
     * @return notify_object - 通知对象（1：顾客；2：商家；3：开发人员）
     */
    public Byte getNotifyObject() {
        return notifyObject;
    }

    /**
     * 设置通知对象（1：顾客；2：商家；3：开发人员）
     *
     * @param notifyObject 通知对象（1：顾客；2：商家；3：开发人员）
     */
    public void setNotifyObject(Byte notifyObject) {
        this.notifyObject = notifyObject;
    }

    /**
     * 获取消息模板（统一变量库处理）
     *
     * @return notify_tpl - 消息模板（统一变量库处理）
     */
    public String getNotifyTpl() {
        return notifyTpl;
    }

    /**
     * 设置消息模板（统一变量库处理）
     *
     * @param notifyTpl 消息模板（统一变量库处理）
     */
    public void setNotifyTpl(String notifyTpl) {
        this.notifyTpl = notifyTpl == null ? null : notifyTpl.trim();
    }

    /**
     * 获取通知方式（1：SMS；2：模板消息，3、邮件）
     *
     * @return type - 通知方式（1：SMS；2：模板消息，3、邮件）
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置通知方式（1：SMS；2：模板消息，3、邮件）
     *
     * @param type 通知方式（1：SMS；2：模板消息，3、邮件）
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取是否启用（1：启用；0：禁用）
     *
     * @return enable - 是否启用（1：启用；0：禁用）
     */
    public Byte getEnable() {
        return enable;
    }

    /**
     * 设置是否启用（1：启用；0：禁用）
     *
     * @param enable 是否启用（1：启用；0：禁用）
     */
    public void setEnable(Byte enable) {
        this.enable = enable;
    }

    /**
     * 获取微信ID
     *
     * @return wechat_id - 微信ID
     */
    public Long getWechatId() {
        return wechatId;
    }

    /**
     * 设置微信ID
     *
     * @param wechatId 微信ID
     */
    public void setWechatId(Long wechatId) {
        this.wechatId = wechatId;
    }
}