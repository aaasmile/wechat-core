package com.d1m.wechat.model.popup;

import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jovi gu on 2017/9/6.
 */
@Setter
@Getter
public class PopupGoodsFilter {
    Integer wechatId;
    String name;
    String sku;
    Short status = 1;

    Integer pageSize = 10;
    Integer pageNum = 1;

}
