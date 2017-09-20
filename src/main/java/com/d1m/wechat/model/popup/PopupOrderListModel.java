package com.d1m.wechat.model.popup;

/**
 * Created by Owen Jia on 2017/6/12.
 */
public class PopupOrderListModel {
    String orderId;
    String endDate;
    String startDate;
    OrderEnum orderStatus;
    PayTypeEnum payType;
    String receiverName;
    String receiverPhone;

    String keys;
    Integer pageSize = 10;
    Integer pageNum = 1;

    Integer wechatId;

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public Integer getWechatId() {
        return wechatId;
    }

    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    public OrderEnum getOrderStatus() {
        return orderStatus;
    }

    public void setOrderStatus(OrderEnum orderStatus) {
        this.orderStatus = orderStatus;
    }

    public PayTypeEnum getPayType() {
        return payType;
    }

    public void setPayType(PayTypeEnum payType) {
        this.payType = payType;
    }

    public String getReceiverName() {
        return receiverName;
    }

    public void setReceiverName(String receiverName) {
        this.receiverName = receiverName;
    }

    public String getReceiverPhone() {
        return receiverPhone;
    }

    public void setReceiverPhone(String receiverPhone) {
        this.receiverPhone = receiverPhone;
    }

    public String getEndDate() {
        return endDate;
    }

    public void setEndDate(String endDate) {
        this.endDate = endDate;
    }

    public String getStartDate() {
        return startDate;
    }

    public void setStartDate(String startDate) {
        this.startDate = startDate;
    }

    public String getKeys() {
        return keys;
    }

    public void setKeys(String keys) {
        this.keys = keys;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }

    public Integer getPageNum() {
        return pageNum;
    }

    public void setPageNum(Integer pageNum) {
        this.pageNum = pageNum;
    }

    @Override
    public String toString() {
        return "PopupOrderListModel{" +
                "endDate='" + endDate + '\'' +
                ", startDate='" + startDate + '\'' +
                ", orderStatus=" + orderStatus +
                ", payType=" + payType +
                ", receiverName='" + receiverName + '\'' +
                ", receiverPhone='" + receiverPhone + '\'' +
                ", keys='" + keys + '\'' +
                ", pageSize=" + pageSize +
                ", pageNum=" + pageNum +
                ", wechatId=" + wechatId +
                '}';
    }
}
