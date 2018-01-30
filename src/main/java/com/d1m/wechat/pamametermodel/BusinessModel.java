package com.d1m.wechat.pamametermodel;

import java.util.List;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

@ApiModel("门店")
public class BusinessModel extends BaseModel {
	
	@ApiModelProperty("门店ID")
	private Integer id;
	
	@ApiModelProperty("门店编号")
	private String businessCode;
	
	@ApiModelProperty("门店名称")
	private String businessName;
	
	@ApiModelProperty("分店名称")
	private String branchName;

	@ApiModelProperty("省")
	private Integer province;

	@ApiModelProperty("市")
	private Integer city;
	
	@ApiModelProperty("区/县")
	private String district;
	
	@ApiModelProperty("详细地址")
	private String address;
	
	@ApiModelProperty("电话")
	private String telephone;
	
	@ApiModelProperty("地理位置的经度")
	private Double longitude;
	
	@ApiModelProperty("地理位置的纬度")
	private Double latitude;
	
	@ApiModelProperty("推荐品，餐厅可为推荐菜；酒店为推荐套房；景点为推荐游玩景点等，针对自己行业的推荐内容")
	private String recommend;
	
	@ApiModelProperty("特色服务，如免费wifi，免费停车，送货上门等商户能提供的特色功能或服务")
	private String special;

	@ApiModelProperty("商户简介，主要介绍商户信息等")
	private String introduction;

	@ApiModelProperty("营业时间：自定义时间")
	private String openTime;

	@ApiModelProperty("营业时间：开始时间")
	private String openStartTime;

	@ApiModelProperty("营业时间：结束时间")
	private String openEndTime;
	
	@ApiModelProperty("人均价格")
	private Integer avgPrice;

	private List<String> photoList;
	
	@ApiModelProperty("街道")
	private String street;

	private String sid;

	private String bus;

	private Boolean push;

	private List<String> absolutePhotoList;
	
	@ApiModelProperty("纬度")
	private Double lat;
	
	@ApiModelProperty("经度")
	private Double lng;

	private Integer isPush;

	private List<String> categories;

	private String query;

	private String shortUrl;

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

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
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

	public List<String> getCategories() {
		return categories;
	}

	public void setCategories(List<String> categories) {
		this.categories = categories;
	}

	public String getQuery() {
		return query;
	}

	public void setQuery(String query) {
		this.query = query;
	}

	public String getShortUrl() {
		return shortUrl;
	}

	public void setShortUrl(String shortUrl) {
		this.shortUrl = shortUrl;
	}

}
