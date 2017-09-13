package com.d1m.wechat.pamametermodel;

import java.util.List;

import com.d1m.wechat.dto.MaterialDto;

public class BusinessModel extends BaseModel {

	private Integer id;

	private String businessCode;

	private String businessName;

	private String branchName;

	private Integer province;

	private Integer city;

	private String district;

	private String address;

	private String telephone;

	private Double longitude;

	private Double latitude;

	private String recommend;

	private String special;

	private String introduction;

	private String openStartTime;

	private String openEndTime;

	private Integer avgPrice;

	private List<String> photoList;

	private String street;

	private String sid;

	private String bus;

	private Boolean push;

	private List<String> absolutePhotoList;

	private Double lat;

	private Double lng;

	private Integer isPush;

	private String categories;

	private String query;

	private List<MaterialDto> materialList;

	private Integer updateStatus;

	public List<String> getAbsolutePhotoList() {
		return absolutePhotoList;
	}

	public String getAddress() {
		return address;
	}

	public Integer getAvgPrice() {
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

	public Double getLat() {
		return lat;
	}

	public Double getLatitude() {
		return latitude;
	}

	public Double getLng() {
		return lng;
	}

	public Double getLongitude() {
		return longitude;
	}

	public String getOpenEndTime() {
		return openEndTime;
	}

	public String getOpenStartTime() {
		return openStartTime;
	}

	public List<String> getPhotoList() {
		return photoList;
	}

	public Integer getProvince() {
		return province;
	}

	public Boolean getPush() {
		return push;
	}

	public String getRecommend() {
		return recommend;
	}

	public String getSid() {
		return sid;
	}

	public String getSpecial() {
		return special;
	}

	public String getStreet() {
		return street;
	}

	public String getTelephone() {
		return telephone;
	}

	public void setAbsolutePhotoList(List<String> absolutePhotoList) {
		this.absolutePhotoList = absolutePhotoList;
	}

	public void setAddress(String address) {
		this.address = address;
	}

	public void setAvgPrice(Integer avgPrice) {
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

	public void setLat(Double lat) {
		this.lat = lat;
	}

	public void setLatitude(Double latitude) {
		this.latitude = latitude;
	}

	public void setLng(Double lng) {
		this.lng = lng;
	}

	public void setLongitude(Double longitude) {
		this.longitude = longitude;
	}

	public void setOpenEndTime(String openEndTime) {
		this.openEndTime = openEndTime;
	}

	public void setOpenStartTime(String openStartTime) {
		this.openStartTime = openStartTime;
	}

	public void setPhotoList(List<String> photoList) {
		this.photoList = photoList;
	}

	public void setProvince(Integer province) {
		this.province = province;
	}

	public void setPush(Boolean push) {
		this.push = push;
	}

	public void setRecommend(String recommend) {
		this.recommend = recommend;
	}

	public void setSid(String sid) {
		this.sid = sid;
	}

	public void setSpecial(String special) {
		this.special = special;
	}

	public void setStreet(String street) {
		this.street = street;
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

	public String getCategories() {
		return categories;
	}

	public void setCategories(String categories) {
		this.categories = categories;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public List<MaterialDto> getMaterialList() {
		return materialList;
	}

	public void setMaterialList(List<MaterialDto> materialList) {
		this.materialList = materialList;
	}

	public Integer getUpdateStatus() {
		return updateStatus;
	}

	public void setUpdateStatus(Integer updateStatus) {
		this.updateStatus = updateStatus;
	}

}
