package com.d1m.wechat.model.popup.dao;

import javax.persistence.*;

@Table(name = "popup_order_invoice")
public class PopupOrderInvoice {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "order_id")
    private Long orderId;

    /**
     * 企业信用代码
     */
    @Column(name = "credit_code")
    private String creditCode;

    @Column(name = "person_no")
    private String personNo;

    private String title;

    /**
     * 类型：1个人，2公司
     */
    private Byte type;

    /**
     * 发票性质：1增值税普通发票 2普通发票
     */
    private Byte prop;

    /**
     * 0未寄出，1已寄出，2完成
     */
    private Byte status;

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
     * 获取企业信用代码
     *
     * @return credit_code - 企业信用代码
     */
    public String getCreditCode() {
        return creditCode;
    }

    /**
     * 设置企业信用代码
     *
     * @param creditCode 企业信用代码
     */
    public void setCreditCode(String creditCode) {
        this.creditCode = creditCode == null ? null : creditCode.trim();
    }

    /**
     * @return person_no
     */
    public String getPersonNo() {
        return personNo;
    }

    /**
     * @param personNo
     */
    public void setPersonNo(String personNo) {
        this.personNo = personNo == null ? null : personNo.trim();
    }

    /**
     * @return title
     */
    public String getTitle() {
        return title;
    }

    /**
     * @param title
     */
    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    /**
     * 获取类型：1个人，2公司
     *
     * @return type - 类型：1个人，2公司
     */
    public Byte getType() {
        return type;
    }

    /**
     * 设置类型：1个人，2公司
     *
     * @param type 类型：1个人，2公司
     */
    public void setType(Byte type) {
        this.type = type;
    }

    /**
     * 获取发票性质：1增值税普通发票 2普通发票
     *
     * @return prop - 发票性质：1增值税普通发票 2普通发票
     */
    public Byte getProp() {
        return prop;
    }

    /**
     * 设置发票性质：1增值税普通发票 2普通发票
     *
     * @param prop 发票性质：1增值税普通发票 2普通发票
     */
    public void setProp(Byte prop) {
        this.prop = prop;
    }

    /**
     * 获取0未寄出，1已寄出，2完成
     *
     * @return status - 0未寄出，1已寄出，2完成
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置0未寄出，1已寄出，2完成
     *
     * @param status 0未寄出，1已寄出，2完成
     */
    public void setStatus(Byte status) {
        this.status = status;
    }
}