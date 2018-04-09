package com.d1m.wechat.model;

import com.alibaba.fastjson.JSONObject;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EstoreOrderListResult {

    private Long id;
    private String orderNo;
    private Long memberId;
    private Integer totalPoint;
    private BigDecimal totalAmount;
    private Long productAmount;
    private Long deliveryFee;
    private Long discount;
    private Long paymentId;
    private Byte payStatus;
    private Byte status;
    private String remark;
    private Byte deliveryType;
    private String deliveryExt;
    private String deliveryName;
    private String deliveryPhone;
    private String deliveryOtherPhone;
    private String deliveryProvince;
    private String deliveryCity;
    private String deliveryDistrict;
    private String deliveryAddress;
    private String expressNo;
    private Byte needInvoice;
    private Byte invoiceType;
    private String invoiceTitle;
    private String invoiceTaxNo;
    private String invoiceContent;
    private Byte invoiceDeliveryType;
    private String invoiceName;
    private String invoicePhone;
    private String invoiceProvince;
    private String invoiceCity;
    private String invoiceDistrict;
    private String invoiceAddress;
    private Byte needGift;
    private String giftContent;
    private Date createAt;
    private Date updateAt;
    private Long wechatId;
    private String cbStatus;
    private String cbData;
    private String payTime;
    private String payName;
    private String payCode;
//    private Long productId;
//    private Long productSpecId;
//    private Integer quantity;
//    private BigDecimal price;
//    private Integer point;
    private List<EstoreOrderProduct> listOrderProduct;
    private String openId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getOrderNo() {
		return orderNo;
	}
	public void setOrderNo(String orderNo) {
		this.orderNo = orderNo;
	}
	public Long getMemberId() {
		return memberId;
	}
	public void setMemberId(Long memberId) {
		this.memberId = memberId;
	}
	public Integer getTotalPoint() {
		return totalPoint;
	}
	public void setTotalPoint(Integer totalPoint) {
		this.totalPoint = totalPoint;
	}
	public BigDecimal getTotalAmount() {
		return totalAmount;
	}
	public void setTotalAmount(BigDecimal totalAmount) {
		this.totalAmount = totalAmount;
	}
	public Long getProductAmount() {
		return productAmount;
	}
	public void setProductAmount(Long productAmount) {
		this.productAmount = productAmount;
	}
	public Long getDeliveryFee() {
		return deliveryFee;
	}
	public void setDeliveryFee(Long deliveryFee) {
		this.deliveryFee = deliveryFee;
	}
	public Long getDiscount() {
		return discount;
	}
	public void setDiscount(Long discount) {
		this.discount = discount;
	}
	public Long getPaymentId() {
		return paymentId;
	}
	public void setPaymentId(Long paymentId) {
		this.paymentId = paymentId;
	}
	public Byte getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Byte payStatus) {
		this.payStatus = payStatus;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public Byte getDeliveryType() {
		return deliveryType;
	}
	public void setDeliveryType(Byte deliveryType) {
		this.deliveryType = deliveryType;
	}
	public String getDeliveryExt() {
		return deliveryExt;
	}
	public void setDeliveryExt(String deliveryExt) {
		this.deliveryExt = deliveryExt;
	}
	public String getDeliveryName() {
		return deliveryName;
	}
	public void setDeliveryName(String deliveryName) {
		this.deliveryName = deliveryName;
	}
	public String getDeliveryPhone() {
		return deliveryPhone;
	}
	public void setDeliveryPhone(String deliveryPhone) {
		this.deliveryPhone = deliveryPhone;
	}
	public String getDeliveryOtherPhone() {
		return deliveryOtherPhone;
	}
	public void setDeliveryOtherPhone(String deliveryOtherPhone) {
		this.deliveryOtherPhone = deliveryOtherPhone;
	}
	public String getDeliveryProvince() {
		return deliveryProvince;
	}
	public void setDeliveryProvince(String deliveryProvince) {
		this.deliveryProvince = deliveryProvince;
	}
	public String getDeliveryCity() {
		return deliveryCity;
	}
	public void setDeliveryCity(String deliveryCity) {
		this.deliveryCity = deliveryCity;
	}
	public String getDeliveryDistrict() {
		return deliveryDistrict;
	}
	public void setDeliveryDistrict(String deliveryDistrict) {
		this.deliveryDistrict = deliveryDistrict;
	}
	public String getDeliveryAddress() {
		return deliveryAddress;
	}
	public void setDeliveryAddress(String deliveryAddress) {
		this.deliveryAddress = deliveryAddress;
	}
	public String getExpressNo() {
		return expressNo;
	}
	public void setExpressNo(String expressNo) {
		this.expressNo = expressNo;
	}
	public Byte getNeedInvoice() {
		return needInvoice;
	}
	public void setNeedInvoice(Byte needInvoice) {
		this.needInvoice = needInvoice;
	}
	public Byte getInvoiceType() {
		return invoiceType;
	}
	public void setInvoiceType(Byte invoiceType) {
		this.invoiceType = invoiceType;
	}
	public String getInvoiceTitle() {
		return invoiceTitle;
	}
	public void setInvoiceTitle(String invoiceTitle) {
		this.invoiceTitle = invoiceTitle;
	}
	public String getInvoiceTaxNo() {
		return invoiceTaxNo;
	}
	public void setInvoiceTaxNo(String invoiceTaxNo) {
		this.invoiceTaxNo = invoiceTaxNo;
	}
	public String getInvoiceContent() {
		return invoiceContent;
	}
	public void setInvoiceContent(String invoiceContent) {
		this.invoiceContent = invoiceContent;
	}
	public Byte getInvoiceDeliveryType() {
		return invoiceDeliveryType;
	}
	public void setInvoiceDeliveryType(Byte invoiceDeliveryType) {
		this.invoiceDeliveryType = invoiceDeliveryType;
	}
	public String getInvoiceName() {
		return invoiceName;
	}
	public void setInvoiceName(String invoiceName) {
		this.invoiceName = invoiceName;
	}
	public String getInvoicePhone() {
		return invoicePhone;
	}
	public void setInvoicePhone(String invoicePhone) {
		this.invoicePhone = invoicePhone;
	}
	public String getInvoiceProvince() {
		return invoiceProvince;
	}
	public void setInvoiceProvince(String invoiceProvince) {
		this.invoiceProvince = invoiceProvince;
	}
	public String getInvoiceCity() {
		return invoiceCity;
	}
	public void setInvoiceCity(String invoiceCity) {
		this.invoiceCity = invoiceCity;
	}
	public String getInvoiceDistrict() {
		return invoiceDistrict;
	}
	public void setInvoiceDistrict(String invoiceDistrict) {
		this.invoiceDistrict = invoiceDistrict;
	}
	public String getInvoiceAddress() {
		return invoiceAddress;
	}
	public void setInvoiceAddress(String invoiceAddress) {
		this.invoiceAddress = invoiceAddress;
	}
	public Byte getNeedGift() {
		return needGift;
	}
	public void setNeedGift(Byte needGift) {
		this.needGift = needGift;
	}
	public String getGiftContent() {
		return giftContent;
	}
	public void setGiftContent(String giftContent) {
		this.giftContent = giftContent;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Date getUpdateAt() {
		return updateAt;
	}
	public void setUpdateAt(Date updateAt) {
		this.updateAt = updateAt;
	}
	public Long getWechatId() {
		return wechatId;
	}
	public void setWechatId(Long wechatId) {
		this.wechatId = wechatId;
	}
	public String getCbStatus() {
		return cbStatus;
	}
	public void setCbStatus(String cbStatus) {
		this.cbStatus = cbStatus;
	}
	public String getCbData() {
		return cbData;
	}
	public void setCbData(String cbData) {
		this.cbData = cbData;
	}
	public String getPayTime() {
		return payTime;
	}
	public void setPayTime(String payTime) {
		this.payTime = payTime;
	}
	public String getPayName() {
		return payName;
	}
	public void setPayName(String payName) {
		this.payName = payName;
	}
	public String getPayCode() {
		return payCode;
	}
	public void setPayCode(String payCode) {
		this.payCode = payCode;
	}
	public List<EstoreOrderProduct> getListOrderProduct() {
		return listOrderProduct;
	}
	public void setListOrderProduct(List<EstoreOrderProduct> listOrderProduct) {
		this.listOrderProduct = listOrderProduct;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
}