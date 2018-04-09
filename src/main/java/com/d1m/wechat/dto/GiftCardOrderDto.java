package com.d1m.wechat.dto;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Stoney.Liu on 2017/12/19.
 */
public class GiftCardOrderDto {
    private String orderId;
    private String transId;
    private Date payFinishTime;
    private String openId;
    private String accepterOpenId;
    private Date createTime;
    private BigDecimal totalPrice;
    private String cardId;
    private String code;
    private String backgroundPicUrl;
	public String getOrderId() {
		return orderId;
	}
	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	public String getTransId() {
		return transId;
	}
	public void setTransId(String transId) {
		this.transId = transId;
	}
	public Date getPayFinishTime() {
		return payFinishTime;
	}
	public void setPayFinishTime(Date payFinishTime) {
		this.payFinishTime = payFinishTime;
	}
	public String getOpenId() {
		return openId;
	}
	public void setOpenId(String openId) {
		this.openId = openId;
	}
	public String getAccepterOpenId() {
		return accepterOpenId;
	}
	public void setAccepterOpenId(String accepterOpenId) {
		this.accepterOpenId = accepterOpenId;
	}
	public Date getCreateTime() {
		return createTime;
	}
	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}
	public BigDecimal getTotalPrice() {
		return totalPrice;
	}
	public void setTotalPrice(BigDecimal totalPrice) {
		this.totalPrice = totalPrice;
	}
	public String getCardId() {
		return cardId;
	}
	public void setCardId(String cardId) {
		this.cardId = cardId;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public String getBackgroundPicUrl() {
		return backgroundPicUrl;
	}
	public void setBackgroundPicUrl(String backgroundPicUrl) {
		this.backgroundPicUrl = backgroundPicUrl;
	}
}
