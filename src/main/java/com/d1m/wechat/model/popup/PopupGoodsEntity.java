package com.d1m.wechat.model.popup;

import com.d1m.wechat.model.popup.dao.PopupGoods;
import com.d1m.wechat.model.popup.dao.PopupGoodsSku;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

/**
 * Created by Jovi gu on 2017/9/6.
 */
@ApiModel("微店商品Entity参数")
public class PopupGoodsEntity {
	
	@ApiModelProperty("微店商品")
    private PopupGoods goods;
	
	@ApiModelProperty("商品SKU")
    private List<PopupGoodsSku> liGoodsSku;

	public PopupGoods getGoods() {
		return goods;
	}

	public void setGoods(PopupGoods goods) {
		this.goods = goods;
	}

	public List<PopupGoodsSku> getLiGoodsSku() {
		return liGoodsSku;
	}

	public void setLiGoodsSku(List<PopupGoodsSku> liGoodsSku) {
		this.liGoodsSku = liGoodsSku;
	}
}
