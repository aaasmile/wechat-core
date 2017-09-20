package com.d1m.wechat.model.popup;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.model.enums.OrderEnum;
import com.d1m.wechat.model.enums.PayTypeEnum;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Jovi gu on 2017/9/6.
 */
@Setter
@Getter
public class PopupOrderList {

    private String endDate;
    private String startDate;
    private String receiverName;
    private String receiverPhone;

    private Integer orderId;
    private Integer wechatId;
    private Integer goodsId;
    private String goodsName;
    private String sku;
    private String shade;
    private Date orderCreateTime;
    private Date deliveryCreateTime;
    private String company;
    private Integer province;
    private String provinceName;
    private Integer city;
    private String cityName;
    private Integer area;
    private String areaName;
    private String address;
    private Integer memberId;
    private String openId;
    private String trackNo;
    private Short payStatus;
    private Short payType;
    private Integer price;
    private Long points;

}
