package com.d1m.wechat.model.popup;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;

import java.util.Date;

/**
 * Created by Owen Jia on 2017/6/12.
 */
public class OrderGoodsDto {
    Integer wechatId;

    Integer memberId;
    String msmPhone;
    Integer msmOpen;
    Integer id;
    String giftContent;
    JSONObject address;
    String addressT;
    JSONObject invoiceAddress;
    String invoiceAddressT;
    String invoiceInfoT;
    JSONObject invoiceInfo;
    Integer invoiceOpen;
    OrderEnum invoiceProp;
    OrderEnum invoiceType;
    Date createTime;
    OrderEnum deliveryType;
    Date deliveryDate;

    /**
     * 商品关联属性
     */
    Integer goodsId;
    String goodsName;
    PayTypeEnum payType;
    OrderEnum payStatus;
    OrderEnum invoiceStatus;
    Integer goodsPrice;
    Integer goodsSum;
    String goodsRemarks;
    JSONArray goodsProps;
    String goodsPropsT;


    @Override
    public String toString() {
        return "OrderGoodsDto{" +
                "createTime='" + createTime + '\'' +
                ", id='" + id + '\'' +
                ", memberId='" + memberId + '\'' +
                ", address='" + address + '\'' +
                ", giftContent='" + giftContent + '\'' +
                ", invoiceOpen='" + invoiceOpen + '\'' +
                ", invoiceInfo='" + invoiceInfo + '\'' +
                ", invoiceType='" + invoiceType + '\'' +
                ", payType='" + payType + '\'' +
                '}';
    }

    public OrderEnum getInvoiceType() {
        return invoiceType;
    }

    public PayTypeEnum getPayType() {
        return payType;
    }

    public void setPayType(PayTypeEnum payType) {
        this.payType = payType;
    }

    public void setInvoiceProp(OrderEnum invoiceProp) {
        this.invoiceProp = invoiceProp;
    }

    public void setInvoiceType(OrderEnum invoiceType) {
        this.invoiceType = invoiceType;
    }

    public OrderEnum getDeliveryType() {
        return deliveryType;
    }

    public void setDeliveryType(OrderEnum deliveryType) {
        this.deliveryType = deliveryType;
    }

    public Date getDeliveryDate() {
        return deliveryDate;
    }

    public void setDeliveryDate(Date deliveryDate) {
        this.deliveryDate = deliveryDate;
    }

    public Integer getInvoiceOpen() {
        return invoiceOpen;
    }

    public void setInvoiceOpen(Integer invoiceOpen) {
        this.invoiceOpen = invoiceOpen;
    }

    public JSONObject getInvoiceInfo() {
        return invoiceInfo;
    }

    public void setInvoiceInfo(JSONObject invoiceInfo) {
        this.invoiceInfo = invoiceInfo;
    }

    public String getInvoiceInfoT() {
        return invoiceInfoT;
    }

    public void setInvoiceInfoT(String invoiceInfoT) {
        this.invoiceInfoT = invoiceInfoT;
    }

    public JSONObject getAddress() {
        return address;
    }

    public void setAddress(JSONObject address) {
        this.address = address;
    }

    public JSONObject getInvoiceAddress() {
        return invoiceAddress;
    }

    public void setInvoiceAddress(JSONObject invoiceAddress) {
        this.invoiceAddress = invoiceAddress;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getWechatId() {
        return wechatId;
    }

    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    public Integer getMemberId() {
        return memberId;
    }

    public void setMemberId(Integer memberId) {
        this.memberId = memberId;
    }

    public String getMsmPhone() {
        return msmPhone;
    }

    public void setMsmPhone(String msmPhone) {
        this.msmPhone = msmPhone;
    }

    public Integer getMsmOpen() {
        return msmOpen;
    }

    public void setMsmOpen(Integer msmOpen) {
        this.msmOpen = msmOpen;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getGiftContent() {
        return giftContent;
    }

    public void setGiftContent(String giftContent) {
        this.giftContent = giftContent;
    }

    public String getAddressT() {
        return addressT;
    }

    public void setAddressT(String addressT) {
        this.addressT = addressT;
    }

    public String getInvoiceAddressT() {
        return invoiceAddressT;
    }

    public void setInvoiceAddressT(String invoiceAddressT) {
        this.invoiceAddressT = invoiceAddressT;
    }

    public Integer getGoodsId() {
        return goodsId;
    }

    public void setGoodsId(Integer goodsId) {
        this.goodsId = goodsId;
    }

    public String getGoodsName() {
        return goodsName;
    }

    public void setGoodsName(String goodsName) {
        this.goodsName = goodsName;
    }

    public OrderEnum getInvoiceProp() {
        return invoiceProp;
    }

    public OrderEnum getPayStatus() {
        return payStatus;
    }

    public void setPayStatus(OrderEnum payStatus) {
        this.payStatus = payStatus;
    }

    public OrderEnum getInvoiceStatus() {
        return invoiceStatus;
    }

    public void setInvoiceStatus(OrderEnum invoiceStatus) {
        this.invoiceStatus = invoiceStatus;
    }

    public Integer getGoodsPrice() {
        return goodsPrice;
    }

    public void setGoodsPrice(Integer goodsPrice) {
        this.goodsPrice = goodsPrice;
    }

    public Integer getGoodsSum() {
        return goodsSum;
    }

    public void setGoodsSum(Integer goodsSum) {
        this.goodsSum = goodsSum;
    }

    public String getGoodsRemarks() {
        return goodsRemarks;
    }

    public void setGoodsRemarks(String goodsRemarks) {
        this.goodsRemarks = goodsRemarks;
    }

    public JSONArray getGoodsProps() {
        return goodsProps;
    }

    public void setGoodsProps(JSONArray goodsProps) {
        this.goodsProps = goodsProps;
    }

    public String getGoodsPropsT() {
        return goodsPropsT;
    }

    public void setGoodsPropsT(String goodsPropsT) {
        this.goodsPropsT = goodsPropsT;
    }

}
