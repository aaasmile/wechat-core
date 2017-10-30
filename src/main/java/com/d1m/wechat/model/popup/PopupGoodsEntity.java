package com.d1m.wechat.model.popup;

import com.d1m.wechat.model.popup.dao.PopupGoods;
import com.d1m.wechat.model.popup.dao.PopupGoodsSku;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * Created by Jovi gu on 2017/9/6.
 */
@Setter
@Getter
@ApiModel("微店商品Entity参数")
public class PopupGoodsEntity {
	
	@ApiModelProperty("微店商品")
    private PopupGoods goods;
	
	@ApiModelProperty("商品SKU")
    private List<PopupGoodsSku> liGoodsSku;
}
