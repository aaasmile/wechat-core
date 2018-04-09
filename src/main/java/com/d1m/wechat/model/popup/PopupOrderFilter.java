package com.d1m.wechat.model.popup;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * Created by Jovi gu on 2017/9/6.
 */
@ApiModel("微店订单参数")
public class PopupOrderFilter {
	
	@ApiModelProperty("结束时间")
    private String endDate;
	@ApiModelProperty("开始时间")
    private String startDate;
	@ApiModelProperty("收件人名字")
    private String receiverName;
	@ApiModelProperty("收件人手机")
    private String receiverPhone;
	@ApiModelProperty("支付类型")
    private Byte payType;
	@ApiModelProperty("支付状态")
    private Byte payStatus;
	@ApiModelProperty("订单ID")
    private Integer orderId;
	@ApiModelProperty("公众号ID")
    private Integer wechatId;
	@ApiModelProperty("分页大小")
    Integer pageSize = 10;
	@ApiModelProperty("当前页")
    Integer pageNum = 1;
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
	public Byte getPayType() {
		return payType;
	}
	public void setPayType(Byte payType) {
		this.payType = payType;
	}
	public Byte getPayStatus() {
		return payStatus;
	}
	public void setPayStatus(Byte payStatus) {
		this.payStatus = payStatus;
	}
	public Integer getOrderId() {
		return orderId;
	}
	public void setOrderId(Integer orderId) {
		this.orderId = orderId;
	}
	public Integer getWechatId() {
		return wechatId;
	}
	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
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
}
