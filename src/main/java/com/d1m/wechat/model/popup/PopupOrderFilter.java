package com.d1m.wechat.model.popup;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

/**
 * Created by Jovi gu on 2017/9/6.
 */
@Setter
@Getter
public class PopupOrderFilter {

    private String endDate;
    private String startDate;
    private String receiverName;
    private String receiverPhone;
    private Byte payType;
    private Byte payStatus;

    private Integer orderId;
    private Integer wechatId;

    Integer pageSize = 10;
    Integer pageNum = 1;
}
