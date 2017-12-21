package com.d1m.wechat.dto;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

/**
 * Created by Stoney.Liu on 2017/12/19.
 */
@Getter
@Setter
public class GiftCardOrderDto {
    private String orderId;
    private String transId;
    private Date payFinishTime;
    private String openId;
    private String accepterOpenId;
    private Date createTime;
    private BigDecimal totalPrice;
    private String cardId;
    private String code;
    private String backgroundPicUrl;
}
