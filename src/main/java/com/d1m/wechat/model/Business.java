package com.d1m.wechat.model;

import java.util.Date;
import javax.persistence.*;

public class Business {
    /**
     * 主键ID
     */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;

    @Column(name = "business_code")
    private String businessCode;

    /**
     * 门店名称
     */
    @Column(name = "business_name")
    private String businessName;

    /**
     * 分店名称
     */
    @Column(name = "branch_name")
    private String branchName;

    /**
     * 省
     */
    private Integer province;

    /**
     * 市
     */
    private Integer city;

    /**
     * 区/县
     */
    private String district;

    /**
     * 详细地址
     */
    private String address;

    /**
     * 电话
     */
    private String telephone;

    /**
     * 地理位置的经度
     */
    private Double longitude;

    /**
     * 地理位置的纬度
     */
    private Double latitude;

    private Double wxlat;
    private Double wxlng;

    /**
     * 特色服务，如免费wifi，免费停车，送货上门等商户能提供的特色功能或服务
     */
    private String special;

    /**
     * 营业时间，24 小时制表示，用“-”连接，如 8:00-20:00
     */
    @Column(name = "open_time")
    private String openTime;

    /**
     * 人均价格
     */
    @Column(name = "avg_price")
    private Integer avgPrice;

    /**
     * 创建时间
     */
    @Column(name = "created_at")
    private Date createdAt;

    /**
     * 创建者
     */
    @Column(name = "creator_id")
    private Integer creatorId;

    /**
     * 公众号ID
     */
    @Column(name = "wechat_id")
    private Integer wechatId;

    /**
     * 修改时间
     */
    @Column(name = "modify_at")
    private Date modifyAt;

    /**
     * 状态(1:使用,0:删除)
     */
    private Byte status;

    /**
     * 公交
     */
    private String bus;

    /**
     * 是否发布（0-未发布，1-已发布）
     */
    @Column(name = "is_push")
    private Byte isPush;

    /**
     * 商户简介，主要介绍商户信息等
     */
    private String introduction;

    /**
     * 推荐品，餐厅可为推荐菜；酒店为推荐套房；景点为推荐游玩景点等，针对自己行业的推荐内容
     */
    private String recommend;

    /**
     * 获取主键ID
     *
     * @return id - 主键ID
     */
    public Integer getId() {
        return id;
    }

    /**
     * 设置主键ID
     *
     * @param id 主键ID
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return business_code
     */
    public String getBusinessCode() {
        return businessCode;
    }

    /**
     * @param businessCode
     */
    public void setBusinessCode(String businessCode) {
        this.businessCode = businessCode == null ? null : businessCode.trim();
    }

    /**
     * 获取门店名称
     *
     * @return business_name - 门店名称
     */
    public String getBusinessName() {
        return businessName;
    }

    /**
     * 设置门店名称
     *
     * @param businessName 门店名称
     */
    public void setBusinessName(String businessName) {
        this.businessName = businessName == null ? null : businessName.trim();
    }

    /**
     * 获取分店名称
     *
     * @return branch_name - 分店名称
     */
    public String getBranchName() {
        return branchName;
    }

    /**
     * 设置分店名称
     *
     * @param branchName 分店名称
     */
    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    /**
     * 获取省
     *
     * @return province - 省
     */
    public Integer getProvince() {
        return province;
    }

    /**
     * 设置省
     *
     * @param province 省
     */
    public void setProvince(Integer province) {
        this.province = province;
    }

    /**
     * 获取市
     *
     * @return city - 市
     */
    public Integer getCity() {
        return city;
    }

    /**
     * 设置市
     *
     * @param city 市
     */
    public void setCity(Integer city) {
        this.city = city;
    }

    /**
     * 获取区/县
     *
     * @return district - 区/县
     */
    public String getDistrict() {
        return district;
    }

    /**
     * 设置区/县
     *
     * @param district 区/县
     */
    public void setDistrict(String district) {
        this.district = district == null ? null : district.trim();
    }

    /**
     * 获取详细地址
     *
     * @return address - 详细地址
     */
    public String getAddress() {
        return address;
    }

    /**
     * 设置详细地址
     *
     * @param address 详细地址
     */
    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    /**
     * 获取电话
     *
     * @return telephone - 电话
     */
    public String getTelephone() {
        return telephone;
    }

    /**
     * 设置电话
     *
     * @param telephone 电话
     */
    public void setTelephone(String telephone) {
        this.telephone = telephone == null ? null : telephone.trim();
    }

    /**
     * 获取地理位置的经度
     *
     * @return longitude - 地理位置的经度
     */
    public Double getLongitude() {
        return longitude;
    }

    /**
     * 设置地理位置的经度
     *
     * @param longitude 地理位置的经度
     */
    public void setLongitude(Double longitude) {
        this.longitude = longitude;
    }

    /**
     * 获取地理位置的纬度
     *
     * @return latitude - 地理位置的纬度
     */
    public Double getLatitude() {
        return latitude;
    }

    /**
     * 设置地理位置的纬度
     *
     * @param latitude 地理位置的纬度
     */
    public void setLatitude(Double latitude) {
        this.latitude = latitude;
    }

    /**
     * 获取特色服务，如免费wifi，免费停车，送货上门等商户能提供的特色功能或服务
     *
     * @return special - 特色服务，如免费wifi，免费停车，送货上门等商户能提供的特色功能或服务
     */
    public String getSpecial() {
        return special;
    }

    /**
     * 设置特色服务，如免费wifi，免费停车，送货上门等商户能提供的特色功能或服务
     *
     * @param special 特色服务，如免费wifi，免费停车，送货上门等商户能提供的特色功能或服务
     */
    public void setSpecial(String special) {
        this.special = special == null ? null : special.trim();
    }

    /**
     * 获取营业时间，24 小时制表示，用“-”连接，如 8:00-20:00
     *
     * @return open_time - 营业时间，24 小时制表示，用“-”连接，如 8:00-20:00
     */
    public String getOpenTime() {
        return openTime;
    }

    /**
     * 设置营业时间，24 小时制表示，用“-”连接，如 8:00-20:00
     *
     * @param openTime 营业时间，24 小时制表示，用“-”连接，如 8:00-20:00
     */
    public void setOpenTime(String openTime) {
        this.openTime = openTime == null ? null : openTime.trim();
    }

    /**
     * 获取人均价格
     *
     * @return avg_price - 人均价格
     */
    public Integer getAvgPrice() {
        return avgPrice;
    }

    /**
     * 设置人均价格
     *
     * @param avgPrice 人均价格
     */
    public void setAvgPrice(Integer avgPrice) {
        this.avgPrice = avgPrice;
    }

    /**
     * 获取创建时间
     *
     * @return created_at - 创建时间
     */
    public Date getCreatedAt() {
        return createdAt;
    }

    /**
     * 设置创建时间
     *
     * @param createdAt 创建时间
     */
    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 获取创建者
     *
     * @return creator_id - 创建者
     */
    public Integer getCreatorId() {
        return creatorId;
    }

    /**
     * 设置创建者
     *
     * @param creatorId 创建者
     */
    public void setCreatorId(Integer creatorId) {
        this.creatorId = creatorId;
    }

    /**
     * 获取公众号ID
     *
     * @return wechat_id - 公众号ID
     */
    public Integer getWechatId() {
        return wechatId;
    }

    /**
     * 设置公众号ID
     *
     * @param wechatId 公众号ID
     */
    public void setWechatId(Integer wechatId) {
        this.wechatId = wechatId;
    }

    /**
     * 获取修改时间
     *
     * @return modify_at - 修改时间
     */
    public Date getModifyAt() {
        return modifyAt;
    }

    /**
     * 设置修改时间
     *
     * @param modifyAt 修改时间
     */
    public void setModifyAt(Date modifyAt) {
        this.modifyAt = modifyAt;
    }

    /**
     * 获取状态(1:使用,0:删除)
     *
     * @return status - 状态(1:使用,0:删除)
     */
    public Byte getStatus() {
        return status;
    }

    /**
     * 设置状态(1:使用,0:删除)
     *
     * @param status 状态(1:使用,0:删除)
     */
    public void setStatus(Byte status) {
        this.status = status;
    }

    /**
     * 获取公交
     *
     * @return bus - 公交
     */
    public String getBus() {
        return bus;
    }

    /**
     * 设置公交
     *
     * @param bus 公交
     */
    public void setBus(String bus) {
        this.bus = bus == null ? null : bus.trim();
    }

    /**
     * 获取是否发布（0-未发布，1-已发布）
     *
     * @return is_push - 是否发布（0-未发布，1-已发布）
     */
    public Byte getIsPush() {
        return isPush;
    }

    /**
     * 设置是否发布（0-未发布，1-已发布）
     *
     * @param isPush 是否发布（0-未发布，1-已发布）
     */
    public void setIsPush(Byte isPush) {
        this.isPush = isPush;
    }

    /**
     * 获取商户简介，主要介绍商户信息等
     *
     * @return introduction - 商户简介，主要介绍商户信息等
     */
    public String getIntroduction() {
        return introduction;
    }

    /**
     * 设置商户简介，主要介绍商户信息等
     *
     * @param introduction 商户简介，主要介绍商户信息等
     */
    public void setIntroduction(String introduction) {
        this.introduction = introduction == null ? null : introduction.trim();
    }

    /**
     * 获取推荐品，餐厅可为推荐菜；酒店为推荐套房；景点为推荐游玩景点等，针对自己行业的推荐内容
     *
     * @return recommend - 推荐品，餐厅可为推荐菜；酒店为推荐套房；景点为推荐游玩景点等，针对自己行业的推荐内容
     */
    public String getRecommend() {
        return recommend;
    }

    /**
     * 设置推荐品，餐厅可为推荐菜；酒店为推荐套房；景点为推荐游玩景点等，针对自己行业的推荐内容
     *
     * @param recommend 推荐品，餐厅可为推荐菜；酒店为推荐套房；景点为推荐游玩景点等，针对自己行业的推荐内容
     */
    public void setRecommend(String recommend) {
        this.recommend = recommend == null ? null : recommend.trim();
    }

	public Double getWxlat() {
		return wxlat;
	}

	public void setWxlat(Double wxlat) {
		this.wxlat = wxlat;
	}

	public Double getWxlng() {
		return wxlng;
	}

	public void setWxlng(Double wxlng) {
		this.wxlng = wxlng;
	}
}