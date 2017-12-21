package com.d1m.wechat.pamametermodel;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class GiftCardOrderSearch extends BaseModel{
    private String orderId;
    private String transId;
    private Date payStart;
    private Date payEnd;
    private String openId;
    private String accepterOpenId;
    private String cardId;
    private String code;
}
