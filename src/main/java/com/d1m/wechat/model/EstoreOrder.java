package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;


@Table(name = "estore_order")
public class EstoreOrder {
    /**
     * 自增ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**
     * 订单号
     */
    @Column(name = "order_no")
    private String orderNo;

    /**
     * 购买人
     */
    @Column(name = "member_id")
    private Long memberId;

    /**
     * 积分商城使用，总积分
     */
    @Column(name = "total_point")
    private Integer totalPoint;

    /**
     * 实际付款金额（商品总金额+快递费-优惠）
     */
    @Column(name = "total_amount")
    private Long totalAmount;

    /**
     * 商品总金额
     */
    @Column(name = "product_amount")
    private Long productAmount;

    /**
     * 快递费
     */
    @Column(name = "delivery_fee")
    private Long deliveryFee;

    /**
     * 优惠扣减金额
     */
    private Long discount;

    /**
     * 支付方式，关联payment表
     */
    @Column(name = "payment_id")
    private Long paymentId;

    /**
     * 支付状态（1：已支付；0：未支付）
     */
    @Column(name = "pay_status")
    private Byte payStatus;

    /**
     * 订单状态（0：待付款；1：待发货；2：已发货；3：交易成功）
     */
    private Byte status;

    /**
     * 订单备注
     */
    private String remark;

    /**
     * 配送方式（1：快递；2：自提）
     */
    @Column(name = "delivery_type")
    private Byte deliveryType;

    /**
     * 配送扩展字段
     */
    @Column(name = "delivery_ext")
    private String deliveryExt;

    /**
     * 收货人
     */
    @Column(name = "delivery_name")
    private String deliveryName;

    /**
     * 收货人联系方式
     */
    @Column(name = "delivery_phone")
    private String deliveryPhone;

    /**
     * 其他联系方式
     */
    @Column(name = "delivery_other_phone")
    private String deliveryOtherPhone;

    /**
     * 收货人省份
     */
    @Column(name = "delivery_province")
    private String deliveryProvince;

    /**
     * 收货人市
     */
    @Column(name = "delivery_city")
    private String deliveryCity;

    /**
     * 收货人区
     */
    @Column(name = "delivery_district")
    private String deliveryDistrict;

    /**
     * 收货人详细地址
     */
    @Column(name = "delivery_address")
    private String deliveryAddress;

    /**
     * 快递单号
     */
    @Column(name = "express_no")
    private String expressNo;

    /**
     * 是否需要发票（1：是；0：否）
     */
    @Column(name = "need_invoice")
    private Byte needInvoice;

    /**
     * 发票类型（1：个人；2：公司）
     */
    @Column(name = "invoice_type")
    private Byte invoiceType;

    /**
     * 发票抬头
     */
    @Column(name = "invoice_title")
    private String invoiceTitle;

    /**
     * 税号
     */
    @Column(name = "invoice_tax_no")
    private String invoiceTaxNo;

    /**
     * 发票内容，如办公用品等
     */
    @Column(name = "invoice_content")
    private String invoiceContent;

    /**
     * 发票配送类型（0：同配送地址；1：其他地址）
     */
    @Column(name = "invoice_delivery_type")
    private Byte invoiceDeliveryType;

    /**
     * 发票寄送人
     */
    @Column(name = "invoice_name")
    private String invoiceName;

    /**
     * 发票寄送人联系方式
     */
    @Column(name = "invoice_phone")
    private String invoicePhone;

    /**
     * 发票配送省
     */
    @Column(name = "invoice_province")
    private String invoiceProvince;

    /**
     * 配送人市
     */
    @Column(name = "invoice_city")
    private String invoiceCity;

    /**
     * 配送人区
     */
    @Column(name = "invoice_district")
    private String invoiceDistrict;

    /**
     * 发票配送详细地址
     */
    @Column(name = "invoice_address")
    private String invoiceAddress;

    /**
     * 是否需要礼品卡（1：是；0：否）
     */
    @Column(name = "need_gift")
    private Byte needGift;

    /**
     * 礼品卡内容
     */
    @Column(name = "gift_content")
    private String giftContent;

    /**
     * 新增时间
     */
    @Column(name = "create_at")
    private Date createAt;

    /**
     * 最后更新时间
     */
    @Column(name = "update_at")
    private Date updateAt;

    /**
     * wechatID
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
     * 获取购买人
     *
     * @return member_id - 购买人
     */
    public Long getMemberId() {
        return memberId;
    }

    /**
     * 设置购买人
     *
     * @param memberId 购买人
     */
    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    /**
     * 获取积分商城使用，总积分
     *
     * @return total_point - 积分商城使用，总积分
     */
    public Integer getTotalPoint() {
        return totalPoint;
    }

    /**
     * 设置积分商城使用，总积分
     *
     * @param totalPoint 积分商城使用，总积分
     */
    public void setTotalPoint(Integer totalPoint) {
        this.totalPoint = totalPoint;
    }

    /**
     * 获取实际付款金额（商品总金额+快递费-优惠）
     *
     * @return total_amount - 实际付款金额（商品总金额+快递费-优惠）
     */
    public Long getTotalAmount() {
        return totalAmount;
    }

    /**
     * 设置实际付款金额（商品总金额+快递费-优惠）
     *
     * @param totalAmount 实际付款金额（商品总金额+快递费-优惠）
     */
    public void setTotalAmount(Long totalAmount) {
        this.totalAmount = totalAmount;
    }

    /**
     * 获取商品总金额
     *
     * @return product_amount - 商品总金额
     */
    public Long getProductAmount() {
        return productAmount;
    }

    /**
     * 设置商品总金额
     *
     * @param productAmount 商品总金额
     */
    public void setProductAmount(Long productAmount) {
        this.productAmount = productAmount;
    }

    /**
     * 获取快递费
     *
     * @return delivery_fee - 快递费
     */
    public Long getDeliveryFee() {
        return deliveryFee;
    }

    /**
     * 设置快递费
     *
     * @param deliveryFee 快递费
     */
    public void setDeliveryFee(Long deliveryFee) {
        this.deliveryFee = deliveryFee;
    }

    /**
     * 获取优惠扣减金额
     *
     * @return discount - 优惠扣减金额
     */
    public Long getDiscount() {
        return discount;
    }

    /**
     * 设置优惠扣减金额
     *
     * @param discount 优惠扣减金额
     */
    public void setDiscount(Long discount) {
        this.discount = discount;
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
     * 获取支付状态（1：已支付；0：未支付）
     *
     * @return pay_status - 支付状态（1：已支付；0：未支付）
     */
    public Byte getPayStatus() {
        return payStatus;
    }

    /**
     * 设置支付状态（1：已支付；0：未支付）
     *
     * @param payStatus 支付状态（1：已支付；0：未支付）
     */
    public void setPayStatus(Byte payStatus) {
        this.payStatus = payStatus;
    }

    /**
     * 获取订单状态（0：待付款；1：待发货；2：已发货；3：交易成功）
     *
     * @return status - 订单状态（0：待付款；1：待发货；2：已发货；3：交易成功）
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置订单状态（0：待付款；1：待发货；2：已发货；3：交易成功）
     *
     * @param status 订单状态（0：待付款；1：待发货；2：已发货；3：交易成功）
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取订单备注
     *
     * @return remark - 订单备注
     */
    public String getRemark() {
        return remark;
    }

    /**
     * 设置订单备注
     *
     * @param remark 订单备注
     */
    public void setRemark(String remark) {
        this.remark = remark == null ? null : remark.trim();
    }

    /**
     * 获取配送方式（1：快递；2：自提）
     *
     * @return delivery_type - 配送方式（1：快递；2：自提）
     */
    public Byte getDeliveryType() {
        return deliveryType;
    }

    /**
     * 设置配送方式（1：快递；2：自提）
     *
     * @param deliveryType 配送方式（1：快递；2：自提）
     */
    public void setDeliveryType(Byte deliveryType) {
        this.deliveryType = deliveryType;
    }

    /**
     * 获取配送扩展字段
     *
     * @return delivery_ext - 配送扩展字段
     */
    public String getDeliveryExt() {
        return deliveryExt;
    }

    /**
     * 设置配送扩展字段
     *
     * @param deliveryExt 配送扩展字段
     */
    public void setDeliveryExt(String deliveryExt) {
        this.deliveryExt = deliveryExt == null ? null : deliveryExt.trim();
    }

    /**
     * 获取收货人
     *
     * @return delivery_name - 收货人
     */
    public String getDeliveryName() {
        return deliveryName;
    }

    /**
     * 设置收货人
     *
     * @param deliveryName 收货人
     */
    public void setDeliveryName(String deliveryName) {
        this.deliveryName = deliveryName == null ? null : deliveryName.trim();
    }

    /**
     * 获取收货人联系方式
     *
     * @return delivery_phone - 收货人联系方式
     */
    public String getDeliveryPhone() {
        return deliveryPhone;
    }

    /**
     * 设置收货人联系方式
     *
     * @param deliveryPhone 收货人联系方式
     */
    public void setDeliveryPhone(String deliveryPhone) {
        this.deliveryPhone = deliveryPhone == null ? null : deliveryPhone.trim();
    }

    /**
     * 获取其他联系方式
     *
     * @return delivery_other_phone - 其他联系方式
     */
    public String getDeliveryOtherPhone() {
        return deliveryOtherPhone;
    }

    /**
     * 设置其他联系方式
     *
     * @param deliveryOtherPhone 其他联系方式
     */
    public void setDeliveryOtherPhone(String deliveryOtherPhone) {
        this.deliveryOtherPhone = deliveryOtherPhone == null ? null : deliveryOtherPhone.trim();
    }

    /**
     * 获取收货人省份
     *
     * @return delivery_province - 收货人省份
     */
    public String getDeliveryProvince() {
        return deliveryProvince;
    }

    /**
     * 设置收货人省份
     *
     * @param deliveryProvince 收货人省份
     */
    public void setDeliveryProvince(String deliveryProvince) {
        this.deliveryProvince = deliveryProvince == null ? null : deliveryProvince.trim();
    }

    /**
     * 获取收货人市
     *
     * @return delivery_city - 收货人市
     */
    public String getDeliveryCity() {
        return deliveryCity;
    }

    /**
     * 设置收货人市
     *
     * @param deliveryCity 收货人市
     */
    public void setDeliveryCity(String deliveryCity) {
        this.deliveryCity = deliveryCity == null ? null : deliveryCity.trim();
    }

    /**
     * 获取收货人区
     *
     * @return delivery_district - 收货人区
     */
    public String getDeliveryDistrict() {
        return deliveryDistrict;
    }

    /**
     * 设置收货人区
     *
     * @param deliveryDistrict 收货人区
     */
    public void setDeliveryDistrict(String deliveryDistrict) {
        this.deliveryDistrict = deliveryDistrict == null ? null : deliveryDistrict.trim();
    }

    /**
     * 获取收货人详细地址
     *
     * @return delivery_address - 收货人详细地址
     */
    public String getDeliveryAddress() {
        return deliveryAddress;
    }

    /**
     * 设置收货人详细地址
     *
     * @param deliveryAddress 收货人详细地址
     */
    public void setDeliveryAddress(String deliveryAddress) {
        this.deliveryAddress = deliveryAddress == null ? null : deliveryAddress.trim();
    }

    /**
     * 获取快递单号
     *
     * @return express_no - 快递单号
     */
    public String getExpressNo() {
        return expressNo;
    }

    /**
     * 设置快递单号
     *
     * @param expressNo 快递单号
     */
    public void setExpressNo(String expressNo) {
        this.expressNo = expressNo == null ? null : expressNo.trim();
    }

    /**
     * 获取是否需要发票（1：是；0：否）
     *
     * @return need_invoice - 是否需要发票（1：是；0：否）
     */
    public Byte getNeedInvoice() {
        return needInvoice;
    }

    /**
     * 设置是否需要发票（1：是；0：否）
     *
     * @param needInvoice 是否需要发票（1：是；0：否）
     */
    public void setNeedInvoice(Byte needInvoice) {
        this.needInvoice = needInvoice;
    }

    /**
     * 获取发票类型（1：个人；2：公司）
     *
     * @return invoice_type - 发票类型（1：个人；2：公司）
     */
    public Byte getInvoiceType() {
        return invoiceType;
    }

    /**
     * 设置发票类型（1：个人；2：公司）
     *
     * @param invoiceType 发票类型（1：个人；2：公司）
     */
    public void setInvoiceType(Byte invoiceType) {
        this.invoiceType = invoiceType;
    }

    /**
     * 获取发票抬头
     *
     * @return invoice_title - 发票抬头
     */
    public String getInvoiceTitle() {
        return invoiceTitle;
    }

    /**
     * 设置发票抬头
     *
     * @param invoiceTitle 发票抬头
     */
    public void setInvoiceTitle(String invoiceTitle) {
        this.invoiceTitle = invoiceTitle == null ? null : invoiceTitle.trim();
    }

    /**
     * 获取税号
     *
     * @return invoice_tax_no - 税号
     */
    public String getInvoiceTaxNo() {
        return invoiceTaxNo;
    }

    /**
     * 设置税号
     *
     * @param invoiceTaxNo 税号
     */
    public void setInvoiceTaxNo(String invoiceTaxNo) {
        this.invoiceTaxNo = invoiceTaxNo == null ? null : invoiceTaxNo.trim();
    }

    /**
     * 获取发票内容，如办公用品等
     *
     * @return invoice_content - 发票内容，如办公用品等
     */
    public String getInvoiceContent() {
        return invoiceContent;
    }

    /**
     * 设置发票内容，如办公用品等
     *
     * @param invoiceContent 发票内容，如办公用品等
     */
    public void setInvoiceContent(String invoiceContent) {
        this.invoiceContent = invoiceContent == null ? null : invoiceContent.trim();
    }

    /**
     * 获取发票配送类型（0：同配送地址；1：其他地址）
     *
     * @return invoice_delivery_type - 发票配送类型（0：同配送地址；1：其他地址）
     */
    public Byte getInvoiceDeliveryType() {
        return invoiceDeliveryType;
    }

    /**
     * 设置发票配送类型（0：同配送地址；1：其他地址）
     *
     * @param invoiceDeliveryType 发票配送类型（0：同配送地址；1：其他地址）
     */
    public void setInvoiceDeliveryType(Byte invoiceDeliveryType) {
        this.invoiceDeliveryType = invoiceDeliveryType;
    }

    /**
     * 获取发票寄送人
     *
     * @return invoice_name - 发票寄送人
     */
    public String getInvoiceName() {
        return invoiceName;
    }

    /**
     * 设置发票寄送人
     *
     * @param invoiceName 发票寄送人
     */
    public void setInvoiceName(String invoiceName) {
        this.invoiceName = invoiceName == null ? null : invoiceName.trim();
    }

    /**
     * 获取发票寄送人联系方式
     *
     * @return invoice_phone - 发票寄送人联系方式
     */
    public String getInvoicePhone() {
        return invoicePhone;
    }

    /**
     * 设置发票寄送人联系方式
     *
     * @param invoicePhone 发票寄送人联系方式
     */
    public void setInvoicePhone(String invoicePhone) {
        this.invoicePhone = invoicePhone == null ? null : invoicePhone.trim();
    }

    /**
     * 获取发票配送省
     *
     * @return invoice_province - 发票配送省
     */
    public String getInvoiceProvince() {
        return invoiceProvince;
    }

    /**
     * 设置发票配送省
     *
     * @param invoiceProvince 发票配送省
     */
    public void setInvoiceProvince(String invoiceProvince) {
        this.invoiceProvince = invoiceProvince == null ? null : invoiceProvince.trim();
    }

    /**
     * 获取配送人市
     *
     * @return invoice_city - 配送人市
     */
    public String getInvoiceCity() {
        return invoiceCity;
    }

    /**
     * 设置配送人市
     *
     * @param invoiceCity 配送人市
     */
    public void setInvoiceCity(String invoiceCity) {
        this.invoiceCity = invoiceCity == null ? null : invoiceCity.trim();
    }

    /**
     * 获取配送人区
     *
     * @return invoice_district - 配送人区
     */
    public String getInvoiceDistrict() {
        return invoiceDistrict;
    }

    /**
     * 设置配送人区
     *
     * @param invoiceDistrict 配送人区
     */
    public void setInvoiceDistrict(String invoiceDistrict) {
        this.invoiceDistrict = invoiceDistrict == null ? null : invoiceDistrict.trim();
    }

    /**
     * 获取发票配送详细地址
     *
     * @return invoice_address - 发票配送详细地址
     */
    public String getInvoiceAddress() {
        return invoiceAddress;
    }

    /**
     * 设置发票配送详细地址
     *
     * @param invoiceAddress 发票配送详细地址
     */
    public void setInvoiceAddress(String invoiceAddress) {
        this.invoiceAddress = invoiceAddress == null ? null : invoiceAddress.trim();
    }

    /**
     * 获取是否需要礼品卡（1：是；0：否）
     *
     * @return need_gift - 是否需要礼品卡（1：是；0：否）
     */
    public Byte getNeedGift() {
        return needGift;
    }

    /**
     * 设置是否需要礼品卡（1：是；0：否）
     *
     * @param needGift 是否需要礼品卡（1：是；0：否）
     */
    public void setNeedGift(Byte needGift) {
        this.needGift = needGift;
    }

    /**
     * 获取礼品卡内容
     *
     * @return gift_content - 礼品卡内容
     */
    public String getGiftContent() {
        return giftContent;
    }

    /**
     * 设置礼品卡内容
     *
     * @param giftContent 礼品卡内容
     */
    public void setGiftContent(String giftContent) {
        this.giftContent = giftContent == null ? null : giftContent.trim();
    }

    /**
     * 获取新增时间
     *
     * @return create_at - 新增时间
     */
    public Date getCreateAt() {
        return createAt;
    }

    /**
     * 设置新增时间
     *
     * @param createAt 新增时间
     */
    public void setCreateAt(Date createAt) {
        this.createAt = createAt;
    }

    /**
     * 获取最后更新时间
     *
     * @return update_at - 最后更新时间
     */
    public Date getUpdateAt() {
        return updateAt;
    }

    /**
     * 设置最后更新时间
     *
     * @param updateAt 最后更新时间
     */
    public void setUpdateAt(Date updateAt) {
        this.updateAt = updateAt;
    }

    /**
     * 获取wechatID
     *
     * @return wechat_id - wechatID
     */
    public Long getWechatId() {
        return wechatId;
    }

    /**
     * 设置wechatID
     *
     * @param wechatId wechatID
     */
    public void setWechatId(Long wechatId) {
        this.wechatId = wechatId;
    }

    public String getOrderNo() {
        return orderNo;
    }
    public void setOrderNo(String orderNo) {
        this.orderNo = orderNo;
    }
}