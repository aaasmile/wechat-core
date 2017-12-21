package com.d1m.wechat.pamametermodel;

import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.List;

@Getter
@Setter
public class EstoreProductSpecEntity {
    private Long id;
    private List<EstoreProductImageEntity> listImg;
    private BigDecimal marketPrice;
    private Integer point;
    private BigDecimal price;
    private String sku;
    private Byte specType;
    private JSONObject specValue;
    private Integer stock;
    private Double volume;
    private Double weight;
    private Byte status;
    private Byte isDel;
//    private Long productId;
//    private Long wechatId;
}
