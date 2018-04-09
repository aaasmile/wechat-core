package com.d1m.wechat.pamametermodel;

import com.alibaba.fastjson.JSONObject;

import java.util.List;

public class EstoreProductSearch extends BaseModel implements Cloneable{
    private Long productId;
    private Long specId;
    private String name;
    private String desc;
    private String sku;
    private Byte status;
    private Long wechatId;

    @Override
    public EstoreProductSearch clone() {
        EstoreProductSearch product = null;
        try{
            product = (EstoreProductSearch)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return product;
    }

	public Long getProductId() {
		return productId;
	}

	public void setProductId(Long productId) {
		this.productId = productId;
	}

	public Long getSpecId() {
		return specId;
	}

	public void setSpecId(Long specId) {
		this.specId = specId;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDesc() {
		return desc;
	}

	public void setDesc(String desc) {
		this.desc = desc;
	}

	public String getSku() {
		return sku;
	}

	public void setSku(String sku) {
		this.sku = sku;
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
}
