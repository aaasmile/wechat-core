package com.d1m.wechat.pamametermodel;

import com.alibaba.fastjson.JSONObject;

import java.math.BigDecimal;
import java.util.List;

public class EstoreOrderProductEntity {
    private Long id;
    private Long orderId;
    private Long productId;
    private Long productSpecId;
    private Integer quantity;
    private BigDecimal marketPrice;
    private BigDecimal price;
    private Integer point;
    private String productCode;
    private String productName;
    private JSONObject extAttr;
//    private Byte specType;
//    private JSONObject specMeta;
    private String sku;
    private Byte spSpecType;
    private JSONObject spSpecValue;
    private Double weight;
    private Double volume;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getOrderId() {
		return orderId;
	}
	public void setOrderId(Long orderId) {
		this.orderId = orderId;
	}
	public Long getProductId() {
		return productId;
	}
	public void setProductId(Long productId) {
		this.productId = productId;
	}
	public Long getProductSpecId() {
		return productSpecId;
	}
	public void setProductSpecId(Long productSpecId) {
		this.productSpecId = productSpecId;
	}
	public Integer getQuantity() {
		return quantity;
	}
	public void setQuantity(Integer quantity) {
		this.quantity = quantity;
	}
	public BigDecimal getMarketPrice() {
		return marketPrice;
	}
	public void setMarketPrice(BigDecimal marketPrice) {
		this.marketPrice = marketPrice;
	}
	public BigDecimal getPrice() {
		return price;
	}
	public void setPrice(BigDecimal price) {
		this.price = price;
	}
	public Integer getPoint() {
		return point;
	}
	public void setPoint(Integer point) {
		this.point = point;
	}
	public String getProductCode() {
		return productCode;
	}
	public void setProductCode(String productCode) {
		this.productCode = productCode;
	}
	public String getProductName() {
		return productName;
	}
	public void setProductName(String productName) {
		this.productName = productName;
	}
	public JSONObject getExtAttr() {
		return extAttr;
	}
	public void setExtAttr(JSONObject extAttr) {
		this.extAttr = extAttr;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public Byte getSpSpecType() {
		return spSpecType;
	}
	public void setSpSpecType(Byte spSpecType) {
		this.spSpecType = spSpecType;
	}
	public JSONObject getSpSpecValue() {
		return spSpecValue;
	}
	public void setSpSpecValue(JSONObject spSpecValue) {
		this.spSpecValue = spSpecValue;
	}
	public Double getWeight() {
		return weight;
	}
	public void setWeight(Double weight) {
		this.weight = weight;
	}
	public Double getVolume() {
		return volume;
	}
	public void setVolume(Double volume) {
		this.volume = volume;
	}
}
