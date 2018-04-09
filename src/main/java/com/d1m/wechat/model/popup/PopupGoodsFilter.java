package com.d1m.wechat.model.popup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;

/**
 * Created by Jovi gu on 2017/9/6.
 */
@ApiModel("微店商品参数")
public class PopupGoodsFilter {
	@ApiModelProperty("公众号ID")
    Integer wechatId;
	@ApiModelProperty("商品名称")
    String name;
	@ApiModelProperty("SKU")
    String sku;
	@ApiModelProperty("状态")
    Short status;

	@ApiModelProperty("分页大小")
    Integer pageSize = 10;
	@ApiModelProperty("当前页")
    Integer pageNum = 1;
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
	public Short getStatus() {
		return status;
	}
	public void setStatus(Short status) {
		this.status = status;
	}
	public Integer getPageSize() {
		return pageSize;
	}
	public void setPageSize(Integer pageSize) {
		this.pageSize = pageSize;
	}
	public Integer getPageNum() {
		return pageNum;
	}
	public void setPageNum(Integer pageNum) {
		this.pageNum = pageNum;
	}
}
