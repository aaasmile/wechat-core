package com.d1m.wechat.pamametermodel;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class EstoreProductEntity {
    private Long id;
    private Long saleId;
    private String category;
    private Byte deliveryFree;
    private Long deliveryTplId;
    private String description;
    private JSONObject extAttr;
    private String name;
    private Byte onSale;
    private JSONObject specMeta;
    private Byte specType;
    private String tag;
    private Byte status;
    private List<EstoreProductSpecEntity> listSpec;
    private List<EstoreProductImageEntity> listImg;
    private Long wechatId;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public Long getSaleId() {
		return saleId;
	}
	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}
	public String getCategory() {
		return category;
	}
	public void setCategory(String category) {
		this.category = category;
	}
	public Byte getDeliveryFree() {
		return deliveryFree;
	}
	public void setDeliveryFree(Byte deliveryFree) {
		this.deliveryFree = deliveryFree;
	}
	public Long getDeliveryTplId() {
		return deliveryTplId;
	}
	public void setDeliveryTplId(Long deliveryTplId) {
		this.deliveryTplId = deliveryTplId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public JSONObject getExtAttr() {
		return extAttr;
	}
	public void setExtAttr(JSONObject extAttr) {
		this.extAttr = extAttr;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public Byte getOnSale() {
		return onSale;
	}
	public void setOnSale(Byte onSale) {
		this.onSale = onSale;
	}
	public JSONObject getSpecMeta() {
		return specMeta;
	}
	public void setSpecMeta(JSONObject specMeta) {
		this.specMeta = specMeta;
	}
	public Byte getSpecType() {
		return specType;
	}
	public void setSpecType(Byte specType) {
		this.specType = specType;
	}
	public String getTag() {
		return tag;
	}
	public void setTag(String tag) {
		this.tag = tag;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public List<EstoreProductSpecEntity> getListSpec() {
		return listSpec;
	}
	public void setListSpec(List<EstoreProductSpecEntity> listSpec) {
		this.listSpec = listSpec;
	}
	public List<EstoreProductImageEntity> getListImg() {
		return listImg;
	}
	public void setListImg(List<EstoreProductImageEntity> listImg) {
		this.listImg = listImg;
	}
	public Long getWechatId() {
		return wechatId;
	}
	public void setWechatId(Long wechatId) {
		this.wechatId = wechatId;
	}
}
