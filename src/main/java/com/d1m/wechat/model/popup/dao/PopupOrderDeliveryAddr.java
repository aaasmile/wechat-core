package com.d1m.wechat.model.popup.dao;

import javax.persistence.*;

@Table(name = "popup_order_delivery_addr")
public class PopupOrderDeliveryAddr {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    @Column(name = "addr_id")
    private Long addrId;

    @Column(name = "msm_open")
    private Byte msmOpen;

    @Column(name = "msm_phone")
    private String msmPhone;

    /**
     * 类型：0都可以，1工作日，2非工作日
     */
    @Column(name = "delivery_type")
    private Byte deliveryType;

    /**
     * 发票地址 0否 1是
     */
    @Column(name = "is_invoice")
    private Byte isInvoice;

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
     * @return order_id
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * @param orderId
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * @return addr_id
     */
    public Long getAddrId() {
        return addrId;
    }

    /**
     * @param addrId
     */
    public void setAddrId(Long addrId) {
        this.addrId = addrId;
    }

    /**
     * @return msm_open
     */
    public Byte getMsmOpen() {
        return msmOpen;
    }

    /**
     * @param msmOpen
     */
    public void setMsmOpen(Byte msmOpen) {
        this.msmOpen = msmOpen;
    }

    /**
     * @return msm_phone
     */
    public String getMsmPhone() {
        return msmPhone;
    }

    /**
     * @param msmPhone
     */
    public void setMsmPhone(String msmPhone) {
        this.msmPhone = msmPhone == null ? null : msmPhone.trim();
    }

    /**
     * 获取类型：0都可以，1工作日，2非工作日
     *
     * @return delivery_type - 类型：0都可以，1工作日，2非工作日
     */
    public Byte getDeliveryType() {
        return deliveryType;
    }

    /**
     * 设置类型：0都可以，1工作日，2非工作日
     *
     * @param deliveryType 类型：0都可以，1工作日，2非工作日
     */
    public void setDeliveryType(Byte deliveryType) {
        this.deliveryType = deliveryType;
    }

    /**
     * 获取发票地址 0否 1是
     *
     * @return is_invoice - 发票地址 0否 1是
     */
    public Byte getIsInvoice() {
        return isInvoice;
    }

    /**
     * 设置发票地址 0否 1是
     *
     * @param isInvoice 发票地址 0否 1是
     */
    public void setIsInvoice(Byte isInvoice) {
        this.isInvoice = isInvoice;
    }
}