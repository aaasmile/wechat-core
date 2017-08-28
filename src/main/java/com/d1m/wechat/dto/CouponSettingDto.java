package com.d1m.wechat.dto;

import java.util.List;

public class CouponSettingDto {

	private Integer id;

	private String createdAt;

	private String name;

	private String shortName;

	private String conditions;

	private String grno;

	private String channel;

	private String type;

	private String giftType;

	private String startDate;

	private String endDate;

	private String validityStartDate;

	private String validityEndDate;

	private String rebateMethod;

	private Double rebateSum;

	private String issueLimitType;

	private Double issueLimitValue;

	private Integer couponSum;

	private Byte fetchType;

	private Integer winProbability;

	private Integer timesOfJoin;

	private Integer timesOfWin;

	private String couponDescription;

	private String couponPic;

	private String couponBgcolor;

	private String couponTitleBgcolor;

	private List<BusinessDto> businesses;

	private String pordInfos;

	public List<BusinessDto> getBusinesses() {
		return businesses;
	}

	public String getChannel() {
		return channel;
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

	public Integer getCouponSum() {
		return couponSum;
	}

	public String getCouponTitleBgcolor() {
		return couponTitleBgcolor;
	}

	public String getCreatedAt() {
		return createdAt;
	}

	public String getEndDate() {
		return endDate;
	}

	public Byte getFetchType() {
		return fetchType;
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

	public String getIssueLimitType() {
		return issueLimitType;
	}

	public Double getIssueLimitValue() {
		return issueLimitValue;
	}

	public String getName() {
		return name;
	}

	public String getPordInfos() {
		return pordInfos;
	}

	public String getRebateMethod() {
		return rebateMethod;
	}

	public Double getRebateSum() {
		return rebateSum;
	}

	public String getShortName() {
		return shortName;
	}

	public String getStartDate() {
		return startDate;
	}

	public Integer getTimesOfJoin() {
		return timesOfJoin;
	}

	public Integer getTimesOfWin() {
		return timesOfWin;
	}

	public String getType() {
		return type;
	}

	public String getValidityEndDate() {
		return validityEndDate;
	}

	public String getValidityStartDate() {
		return validityStartDate;
	}

	public Integer getWinProbability() {
		return winProbability;
	}

	public void setBusinesses(List<BusinessDto> businesses) {
		this.businesses = businesses;
	}

	public void setChannel(String channel) {
		this.channel = channel;
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

	public void setCouponSum(Integer couponSum) {
		this.couponSum = couponSum;
	}

	public void setCouponTitleBgcolor(String couponTitleBgcolor) {
		this.couponTitleBgcolor = couponTitleBgcolor;
	}

	public void setCreatedAt(String createdAt) {
		this.createdAt = createdAt;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public void setFetchType(Byte fetchType) {
		this.fetchType = fetchType;
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

	public void setIssueLimitType(String issueLimitType) {
		this.issueLimitType = issueLimitType;
	}

	public void setIssueLimitValue(Double issueLimitValue) {
		this.issueLimitValue = issueLimitValue;
	}

	public void setName(String name) {
		this.name = name;
	}

	public void setPordInfos(String pordInfos) {
		this.pordInfos = pordInfos;
	}

	public void setRebateMethod(String rebateMethod) {
		this.rebateMethod = rebateMethod;
	}

	public void setRebateSum(Double rebateSum) {
		this.rebateSum = rebateSum;
	}

	public void setShortName(String shortName) {
		this.shortName = shortName;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public void setTimesOfJoin(Integer timesOfJoin) {
		this.timesOfJoin = timesOfJoin;
	}

	public void setTimesOfWin(Integer timesOfWin) {
		this.timesOfWin = timesOfWin;
	}

	public void setType(String type) {
		this.type = type;
	}

	public void setValidityEndDate(String validityEndDate) {
		this.validityEndDate = validityEndDate;
	}

	public void setValidityStartDate(String validityStartDate) {
		this.validityStartDate = validityStartDate;
	}

	public void setWinProbability(Integer winProbability) {
		this.winProbability = winProbability;
	}

}
