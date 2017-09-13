package com.d1m.wechat.model.popup.dao;

import java.util.Date;
import javax.persistence.*;

@Table(name = "popup_order")
public class PopupOrder {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "member_id")
    private Long memberId;

    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 1微信 2支付宝 3银联 4积分
     */
    @Column(name = "pay_type")
    private Byte payType;

    /**
     * 0未支付 1已支付 2取消 3待发货 4已发货 5完成
     */
    @Column(name = "pay_status")
    private Byte payStatus;

    /**
     * 支付系统异步通知状态
     */
    @Column(name = "notify_status")
    private Byte notifyStatus;

    @Column(name = "notify_update_time")
    private Date notifyUpdateTime;

    @Column(name = "pay_order_no")
    private String payOrderNo;

    @Column(name = "create_time")
    private Date createTime;

    @Column(name = "update_time")
    private Date updateTime = new Date();

    /**
     * @return id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return member_id
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * @param memberId
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * @return wechat_id
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * @param wechatId
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    /**
     * 获取1微信 2支付宝 3银联 4积分
     *
     * @return pay_type - 1微信 2支付宝 3银联 4积分
     */
    public Byte getPayType() {
        return payType;
    }

    /**
     * 设置1微信 2支付宝 3银联 4积分
     *
     * @param payType 1微信 2支付宝 3银联 4积分
     */
    public void setPayType(Byte payType) {
        this.payType = payType;
    }

    /**
     * 获取0未支付 1已支付 2取消 3待发货 4已发货 5完成
     *
     * @return pay_status - 0未支付 1已支付 2取消 3待发货 4已发货 5完成
     */
    public Byte getPayStatus() {
        return payStatus;
    }

    /**
     * 设置0未支付 1已支付 2取消 3待发货 4已发货 5完成
     *
     * @param payStatus 0未支付 1已支付 2取消 3待发货 4已发货 5完成
     */
    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取支付系统异步通知状态
     *
     * @return notify_status - 支付系统异步通知状态
     */
    public Byte getNotifyStatus() {
        return notifyStatus;
    }

    /**
     * 设置支付系统异步通知状态
     *
     * @param notifyStatus 支付系统异步通知状态
     */
    public void setNotifyStatus(Byte notifyStatus) {
        this.notifyStatus = notifyStatus;
    }

    /**
     * @return notify_update_time
     */
    public Date getNotifyUpdateTime() {
        return notifyUpdateTime;
    }

    /**
     * @param notifyUpdateTime
     */
    public void setNotifyUpdateTime(Date notifyUpdateTime) {
        this.notifyUpdateTime = notifyUpdateTime;
    }

    /**
     * @return pay_order_no
     */
    public String getPayOrderNo() {
        return payOrderNo;
    }

    /**
     * @param payOrderNo
     */
    public void setPayOrderNo(String payOrderNo) {
        this.payOrderNo = payOrderNo == null ? null : payOrderNo.trim();
    }

    /**
     * @return create_time
     */
    public Date getCreateTime() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    /**
     * @return update_time
     */
    public Date getUpdateTime() {
        return updateTime;
    }

    /**
     * @param updateTime
     */
    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}