package com.d1m.wechat.pamametermodel;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class EstoreOrderProductEntity {
    private Long id;
    private Long orderId;
    private Long productId;
    private Long productSpecId;
    private Integer quantity;
    private BigDecimal marketPrice;
    private BigDecimal price;
    private Integer point;
    private String productCode;
    private String productName;
    private JSONObject extAttr;
//    private Byte specType;
//    private JSONObject specMeta;
    private String sku;
    private Byte spSpecType;
    private JSONObject spSpecValue;
    private Double weight;
    private Double volume;
}
