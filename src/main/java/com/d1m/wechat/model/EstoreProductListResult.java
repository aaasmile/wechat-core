package com.d1m.wechat.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

public class EstoreProductListResult {
    private Long id;
    private String name;
    private String code;
    private JSONObject extAttr;
    private Byte specType;
    private JSONObject specMeta;
    private Date createAt;
    private Date modifyAt;
    private Byte status;
    private Long wechatId;
    private String description;
    private Long saleId;
    private Byte onSale;
    private Byte deliveryFree;
    private Long deliveryTplId;
    private String sku;
    private String specId;
    private List<EstoreProductImage> listProductImage;
    private List<EstoreProductCategory> listProductCategory;
    private List<EstoreProductTag> listProductTag;
    private List<EstoreProductSpecListResult> listProductSpec;
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getCode() {
		return code;
	}
	public void setCode(String code) {
		this.code = code;
	}
	public JSONObject getExtAttr() {
		return extAttr;
	}
	public void setExtAttr(JSONObject extAttr) {
		this.extAttr = extAttr;
	}
	public Byte getSpecType() {
		return specType;
	}
	public void setSpecType(Byte specType) {
		this.specType = specType;
	}
	public JSONObject getSpecMeta() {
		return specMeta;
	}
	public void setSpecMeta(JSONObject specMeta) {
		this.specMeta = specMeta;
	}
	public Date getCreateAt() {
		return createAt;
	}
	public void setCreateAt(Date createAt) {
		this.createAt = createAt;
	}
	public Date getModifyAt() {
		return modifyAt;
	}
	public void setModifyAt(Date modifyAt) {
		this.modifyAt = modifyAt;
	}
	public Byte getStatus() {
		return status;
	}
	public void setStatus(Byte status) {
		this.status = status;
	}
	public Long getWechatId() {
		return wechatId;
	}
	public void setWechatId(Long wechatId) {
		this.wechatId = wechatId;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	public Long getSaleId() {
		return saleId;
	}
	public void setSaleId(Long saleId) {
		this.saleId = saleId;
	}
	public Byte getOnSale() {
		return onSale;
	}
	public void setOnSale(Byte onSale) {
		this.onSale = onSale;
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
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public String getSpecId() {
		return specId;
	}
	public void setSpecId(String specId) {
		this.specId = specId;
	}
	public List<EstoreProductImage> getListProductImage() {
		return listProductImage;
	}
	public void setListProductImage(List<EstoreProductImage> listProductImage) {
		this.listProductImage = listProductImage;
	}
	public List<EstoreProductCategory> getListProductCategory() {
		return listProductCategory;
	}
	public void setListProductCategory(List<EstoreProductCategory> listProductCategory) {
		this.listProductCategory = listProductCategory;
	}
	public List<EstoreProductTag> getListProductTag() {
		return listProductTag;
	}
	public void setListProductTag(List<EstoreProductTag> listProductTag) {
		this.listProductTag = listProductTag;
	}
	public List<EstoreProductSpecListResult> getListProductSpec() {
		return listProductSpec;
	}
	public void setListProductSpec(List<EstoreProductSpecListResult> listProductSpec) {
		this.listProductSpec = listProductSpec;
	}
}