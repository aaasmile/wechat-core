package com.d1m.wechat.model.popup;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.model.enums.GoodsEnum;
import lombok.Getter;
import lombok.Setter;

/**
 * Created by Jovi gu on 2017/9/6.
 */
@Setter
@Getter
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

}
