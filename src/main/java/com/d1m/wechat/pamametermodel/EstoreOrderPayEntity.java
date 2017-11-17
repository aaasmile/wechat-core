package com.d1m.wechat.pamametermodel;

import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Getter
@Setter
public class EstoreOrderPayEntity {
    private Long id;
    private Long orderId;
    private String status;
    private String data;
    private String payName;
    private String payCode;
}
