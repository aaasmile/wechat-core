package com.d1m.wechat.model.popup;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import io.swagger.annotations.ApiParam;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jovi gu on 2017/9/6.
 */
@Setter
@Getter
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

}
