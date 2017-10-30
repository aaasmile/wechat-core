package com.d1m.wechat.dto;

import java.util.Date;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("优惠券")
public class CouponDto {
	
	@ApiModelProperty("优惠券ID")
	private Integer id;
	
	@ApiModelProperty("优惠券编码")
	private String code;
	
	@ApiModelProperty("来源(0:D1M,1:CRM)")
	private Byte source;
	
	@ApiModelProperty("使用状态(0:未领用,1:已领用,2:已核销)")
	private Byte status;
	
	@ApiModelProperty("优惠券用户关联ID")
	private Integer couponMemberId;
	
	@ApiModelProperty("创建时间")
	private Date createdAt;
	
	@ApiModelProperty("领取时间")
	private Date receiveAt;
	
	@ApiModelProperty("店铺名称")
	private String businessName;
	
	@ApiModelProperty("核销时间")
	private Date verificationAt;
	
	@ApiModelProperty("openId")
	private String openId;
	
	@ApiModelProperty("活动编号")
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
