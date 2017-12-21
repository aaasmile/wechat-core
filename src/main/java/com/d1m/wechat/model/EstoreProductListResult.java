package com.d1m.wechat.model;

import com.alibaba.fastjson.JSONObject;
import io.swagger.models.auth.In;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

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
    private String sku;
    private String specId;
    private List<EstoreProductImage> listProductImage;
    private List<EstoreProductCategory> listProductCategory;
    private List<EstoreProductTag> listProductTag;
    private List<EstoreProductSpecListResult> listProductSpec;
}