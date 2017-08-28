package com.d1m.wechat.dto;

import java.util.Date;

public class CouponDto {

	private Integer id;

	private String code;

	private Byte source;

	private Byte status;

	private Integer couponMemberId;

	private Date createdAt;

	private Date receiveAt;

	private String businessName;

	private Date verificationAt;

	private String openId;

	private String grno;

	public String getBusinessName() {
		return businessName;
	}

	public String getCode() {
		return code;
	}

	public Integer getCouponMemberId() {
		return couponMemberId;
	}

	public Date getCreatedAt() {
		return createdAt;
	}

	public String getGrno() {
		return grno;
	}

	public Integer getId() {
		return id;
	}

	public String getOpenId() {
		return openId;
	}

	public Date getReceiveAt() {
		return receiveAt;
	}

	public Byte getSource() {
		return source;
	}

	public Byte getStatus() {
		return status;
	}

	public Date getVerificationAt() {
		return verificationAt;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setCouponMemberId(Integer couponMemberId) {
		this.couponMemberId = couponMemberId;
	}

	public void setCreatedAt(Date createdAt) {
		this.createdAt = createdAt;
	}

	public void setGrno(String grno) {
		this.grno = grno;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setOpenId(String openId) {
		this.openId = openId;
	}

	public void setReceiveAt(Date receiveAt) {
		this.receiveAt = receiveAt;
	}

	public void setSource(Byte source) {
		this.source = source;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setVerificationAt(Date verificationAt) {
		this.verificationAt = verificationAt;
	}

}
