package com.d1m.wechat.pamametermodel;

import java.math.BigDecimal;
import java.util.Date;

public class GiftCardOrderSearch extends BaseModel{
    private String orderId;
    private String transId;
    private Date payStart;
    private Date payEnd;
    private String openId;
    private String accepterOpenId;
    private String cardId;
    private String code;
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
	public Date getPayStart() {
		return payStart;
	}
	public void setPayStart(Date payStart) {
		this.payStart = payStart;
	}
	public Date getPayEnd() {
		return payEnd;
	}
	public void setPayEnd(Date payEnd) {
		this.payEnd = payEnd;
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
}
