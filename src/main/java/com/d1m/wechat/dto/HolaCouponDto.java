package com.d1m.wechat.dto;

import java.util.Date;

public class HolaCouponDto {

	private Integer id;

	private String name;

	private String grno;

	private String shortName;

	private String conditions;

	private String code;

	private String giftType;

	/**
	 * 折价券类别
	 */
	private String type;

	private String rebateMethod;

	private Double rebateSum;

	private String couponTitleBgcolor;

	private String couponBgcolor;

	private String couponDescription;

	private String startDate;

	private String endDate;

	private String couponPic;

	private Byte status;

	private String receiveAt;

	private String verificationAt;

	private String validityStartDate;

	private String validityEndDate;

	private Date validityEndAt;

	public String getCode() {
		return code;
	}

	public String getConditions() {
		return conditions;
	}

	public String getCouponBgcolor() {
		return couponBgcolor;
	}

	public String getCouponDescription() {
		return couponDescription;
	}

	public String getCouponPic() {
		return couponPic;
	}

	public String getCouponTitleBgcolor() {
		return couponTitleBgcolor;
	}

	public String getEndDate() {
		return endDate;
	}

	public String getGiftType() {
		return giftType;
	}

	public String getGrno() {
		return grno;
	}

	public Integer getId() {
		return id;
	}

	public String getName() {
		return name;
	}

	public String getRebateMethod() {
		return rebateMethod;
	}

	public Double getRebateSum() {
		return rebateSum;
	}

	public String getReceiveAt() {
		return receiveAt;
	}

	public String getShortName() {
		return shortName;
	}

	public String getStartDate() {
		return startDate;
	}

	public Byte getStatus() {
		if (receiveAt != null && verificationAt == null
				&& validityEndAt.compareTo(new Date()) >= 0) {
			return 0;
		} else if (verificationAt != null) {
			return 1;
		} else if (receiveAt != null && verificationAt == null
				&& validityEndAt.compareTo(new Date()) < 0) {
			return 2;
		}
		return status;
	}

	public String getType() {
		return type;
	}

	public Date getValidityEndAt() {
		return validityEndAt;
	}

	public String getValidityEndDate() {
		return validityEndDate;
	}

	public String getValidityStartDate() {
		return validityStartDate;
	}

	public String getVerificationAt() {
		return verificationAt;
	}

	public void setCode(String code) {
		this.code = code;
	}

	public void setConditions(String conditions) {
		this.conditions = conditions;
	}

	public void setCouponBgcolor(String couponBgcolor) {
		this.couponBgcolor = couponBgcolor;
	}

	public void setCouponDescription(String couponDescription) {
		this.couponDescription = couponDescription;
	}

	public void setCouponPic(String couponPic) {
		this.couponPic = couponPic;
	}

	public void setCouponTitleBgcolor(String couponTitleBgcolor) {
		this.couponTitleBgcolor = couponTitleBgcolor;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setGiftType(String giftType) {
		this.giftType = giftType;
	}

	public void setGrno(String grno) {
		this.grno = grno;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setRebateMethod(String rebateMethod) {
		this.rebateMethod = rebateMethod;
	}

	public void setRebateSum(Double rebateSum) {
		this.rebateSum = rebateSum;
	}

	public void setReceiveAt(String receiveAt) {
		this.receiveAt = receiveAt;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setStatus(Byte status) {
		this.status = status;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValidityEndAt(Date validityEndAt) {
		this.validityEndAt = validityEndAt;
	}

	public void setValidityEndDate(String validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	public void setValidityStartDate(String validityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	public void setVerificationAt(String verificationAt) {
		this.verificationAt = verificationAt;
	}

}
