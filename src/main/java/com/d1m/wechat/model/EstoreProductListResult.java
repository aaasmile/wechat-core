package com.d1m.wechat.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
public class EstoreProductListResult {
    private Long id;
    private String name;
    private String code;
    private JSONObject extAttr;
    private Byte specType;
    private JSONObject specMeta;
    private Date createAt;
    private Date modifyAt;
    private Byte status;
    private Long wechatId;
    private String description;
    private Long saleId;
    private Byte onSale;
    private Byte deliveryFree;
    private Long deliveryTplId;
    private Long spId;
    private String sku;
    private Byte spSpecType;
    private JSONObject spSpecValue;
    private BigDecimal marketPrice;
    private BigDecimal price;
    private Integer point;
    private Integer stock;
    private Double weight;
    private Double volume;
    private Byte spStatus;
}