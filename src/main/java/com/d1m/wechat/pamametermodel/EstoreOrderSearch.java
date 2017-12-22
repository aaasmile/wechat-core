package com.d1m.wechat.pamametermodel;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class EstoreOrderSearch extends BaseModel{
    private Long orderId;
    private String orderNo;
    private String deliveryName;
    private String deliveryPhone;
    private Byte payStatus;
    private String payType;
    private Byte status;
    private String expressNo;
    private Date startDate;
    private Date endDate;
    private String code;
    private String sku;
}
