package com.d1m.wechat.dto;

import java.math.BigDecimal;
import java.util.List;

public class BusinessDto {

	private Integer id;

	private String businessName;

	private String branchName;

	private Integer province;

	private Integer city;

	private String district;

	private String address;

	private String telephone;

	private Double longitude;

	private Double latitude;

	private Double wxlng;

	private Double wxlat;

	private String special;

	private String openTime;

	private BigDecimal avgPrice;

	private String introduction;

	private String recommend;

	private List<String> photoList;

	private String businessCode;

	private String bus;

	private BigDecimal distance;
	
	private Integer isPush;

	public BigDecimal getDistance() {
		return distance;
	}

	public void setDistance(BigDecimal distance) {
		this.distance = distance;
	}

	public String getAddress() {
		return address;
	}

	public BigDecimal getAvgPrice() {
		return avgPrice;
	}

	public String getBranchName() {
		return branchName;
	}

	public String getBus() {
		return bus;
	}

	public String getBusinessCode() {
		return businessCode;
	}

	public String getBusinessName() {
		return businessName;
	}

	public Integer getCity() {
		return city;
	}

	public String getDistrict() {
		return district;
	}

	public Integer getId() {
		return id;
	}

	public String getIntroduction() {
		return introduction;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getOpenTime() {
		return openTime;
	}

	public List<String> getPhotoList() {
		return photoList;
	}

	public Integer getProvince() {
		return province;
	}

	public String getRecommend() {
		return recommend;
	}

	public String getSpecial() {
		return special;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAvgPrice(BigDecimal avgPrice) {
		this.avgPrice = avgPrice;
	}

	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}

	public void setBus(String bus) {
		this.bus = bus;
	}

	public void setBusinessCode(String businessCode) {
		this.businessCode = businessCode;
	}

	public void setBusinessName(String businessName) {
		this.businessName = businessName;
	}

	public void setCity(Integer city) {
		this.city = city;
	}

	public void setDistrict(String district) {
		this.district = district;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setOpenTime(String openTime) {
		this.openTime = openTime;
	}

	public void setPhotoList(List<String> photoList) {
		this.photoList = photoList;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public void setTelephone(String telephone) {
		this.telephone = telephone;
	}

	public Integer getIsPush() {
		return isPush;
	}

	public void setIsPush(Integer isPush) {
		this.isPush = isPush;
	}

}
