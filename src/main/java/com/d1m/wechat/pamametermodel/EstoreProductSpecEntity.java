package com.d1m.wechat.pamametermodel;

import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;

import java.math.BigDecimal;
import java.util.List;

public class EstoreProductSpecEntity {
    private Long id;
    private List<EstoreProductImageEntity> listImg;
    private BigDecimal marketPrice;
    private Integer point;
    private BigDecimal price;
    private String sku;
    private Byte specType;
    private JSONObject specValue;
    private Integer stock;
    private Double volume;
    private Double weight;
    private Byte status;
    private Byte isDel;
//    private Long productId;
//    private Long wechatId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public List<EstoreProductImageEntity> getListImg() {
		return listImg;
	}
	public void setListImg(List<EstoreProductImageEntity> listImg) {
		this.listImg = listImg;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Byte getSpecType() {
		return specType;
	}
	public void setSpecType(Byte specType) {
		this.specType = specType;
	}
	public JSONObject getSpecValue() {
		return specValue;
	}
	public void setSpecValue(JSONObject specValue) {
		this.specValue = specValue;
	}
	public Integer getStock() {
		return stock;
	}
	public void setStock(Integer stock) {
		this.stock = stock;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Byte getIsDel() {
		return isDel;
	}
	public void setIsDel(Byte isDel) {
		this.isDel = isDel;
	}
}
