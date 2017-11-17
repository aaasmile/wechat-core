package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

@Table(name = "estore_order_pay")
public class EstoreOrderPay {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单状态（按支付方式返回存储）
     */
    private String status;

    /**
     * 支付方式，关联payment表
     */
    @Column(name = "payment_id")
    private Long paymentId;

    /**
     * 创建时间
     */
    @Column(name = "create_at")
    private Date createAt;

    /**
     * 订单ID
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 微信ID
     */
    @Column(name = "wechat_id")
    private Long wechatId;

    /**
     * 支付原始报文信息
     */
    private String data;

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
     * 获取订单状态（按支付方式返回存储）
     *
     * @return status - 订单状态（按支付方式返回存储）
     */
    public String getStatus() {
        return status;
    }

    /**
     * 设置订单状态（按支付方式返回存储）
     *
     * @param status 订单状态（按支付方式返回存储）
     */
    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    /**
     * 获取支付方式，关联payment表
     *
     * @return payment_id - 支付方式，关联payment表
     */
    public Long getPaymentId() {
        return paymentId;
    }

    /**
     * 设置支付方式，关联payment表
     *
     * @param paymentId 支付方式，关联payment表
     */
    public void setPaymentId(Long paymentId) {
        this.paymentId = paymentId;
    }

    /**
     * 获取创建时间
     *
     * @return create_at - 创建时间
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置创建时间
     *
     * @param createAt 创建时间
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取订单ID
     *
     * @return order_id - 订单ID
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单ID
     *
     * @param orderId 订单ID
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
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

    /**
     * 获取支付原始报文信息
     *
     * @return data - 支付原始报文信息
     */
    public String getData() {
        return data;
    }

    /**
     * 设置支付原始报文信息
     *
     * @param data 支付原始报文信息
     */
    public void setData(String data) {
        this.data = data == null ? null : data.trim();
    }
}