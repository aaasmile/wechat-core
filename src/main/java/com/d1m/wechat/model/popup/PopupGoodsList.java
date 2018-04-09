package com.d1m.wechat.model.popup;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.model.enums.GoodsEnum;

/**
 * Created by Jovi gu on 2017/9/6.
 */
public class PopupGoodsList {
    Integer id;
    Integer wechatId;
    String name;
    String sku;
    JSONObject imgUrls;
    Integer points;
    Short status;
    Integer sort;
    Integer sortBestSell;
    Integer redeem;
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public Integer getWechatId() {
		return wechatId;
	}
	public void setWechatId(Integer wechatId) {
		this.wechatId = wechatId;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getSku() {
		return sku;
	}
	public void setSku(String sku) {
		this.sku = sku;
	}
	public JSONObject getImgUrls() {
		return imgUrls;
	}
	public void setImgUrls(JSONObject imgUrls) {
		this.imgUrls = imgUrls;
	}
	public Integer getPoints() {
		return points;
	}
	public void setPoints(Integer points) {
		this.points = points;
	}
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Integer getSort() {
		return sort;
	}
	public void setSort(Integer sort) {
		this.sort = sort;
	}
	public Integer getSortBestSell() {
		return sortBestSell;
	}
	public void setSortBestSell(Integer sortBestSell) {
		this.sortBestSell = sortBestSell;
	}
	public Integer getRedeem() {
		return redeem;
	}
	public void setRedeem(Integer redeem) {
		this.redeem = redeem;
	}
}
