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
    private Short orderStatus;
    private String receiverName;
    private String receiverPhone;
    private String keys;

    private Integer orderId;
    private Integer wechatId;
    private Integer goodsId;
    private String name;
    private String sku;
    private String title;
    private Date createTime;
    private String company;
    private String province;
    private String city;
    private String area;
    private String address;
    private Integer memberId;
    private String openId;
    private String trackNo;
    private Short payStatus;
    private Short payType;
    private Integer price;

    Integer pageSize = 10;
    Integer pageNum = 1;
}
