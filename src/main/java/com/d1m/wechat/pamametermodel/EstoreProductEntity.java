package com.d1m.wechat.pamametermodel;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstoreProductEntity {
    private Long id;
    private Long saleId;
    private String category;
    private Byte deliveryFree;
    private Long deliveryTplId;
    private String description;
    private JSONObject extAttr;
    private String name;
    private Byte onSale;
    private JSONObject specMeta;
    private Byte specType;
    private String tag;
    private Byte status;
    private List<EstoreProductSpecEntity> listSpec;
    private List<EstoreProductImageEntity> listImg;
    private Long wechatId;
}
