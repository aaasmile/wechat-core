package com.d1m.wechat.model.popup.dao;

import java.util.Date;
import javax.persistence.*;

@Table(name = "popup_order_express")
public class PopupOrderExpress {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单号
     */
    @Column(name = "order_id")
    private Long orderId;

    /**
     * 快递单号
     */
    @Column(name = "track_no")
    private String trackNo;

    /**
     * 快递公司
     */
    private String company;

    private Byte status = 0;

    @Column(name = "create_time")
    private Date createTime = new Date();

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
     * 获取订单号
     *
     * @return order_id - 订单号
     */
    public Long getOrderId() {
        return orderId;
    }

    /**
     * 设置订单号
     *
     * @param orderId 订单号
     */
    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    /**
     * 获取快递单号
     *
     * @return track_no - 快递单号
     */
    public String getTrackNo() {
        return trackNo;
    }

    /**
     * 设置快递单号
     *
     * @param trackNo 快递单号
     */
    public void setTrackNo(String trackNo) {
        this.trackNo = trackNo == null ? null : trackNo.trim();
    }

    /**
     * 获取快递公司
     *
     * @return company - 快递公司
     */
    public String getCompany() {
        return company;
    }

    /**
     * 设置快递公司
     *
     * @param company 快递公司
     */
    public void setCompany(String company) {
        this.company = company == null ? null : company.trim();
    }

    /**
     * @return status
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * @param status
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * @return create_date
     */
    public Date getCreateDate() {
        return createTime;
    }

    /**
     * @param createTime
     */
    public void setCreateDate(Date createTime) {
        this.createTime = createTime;
    }
}