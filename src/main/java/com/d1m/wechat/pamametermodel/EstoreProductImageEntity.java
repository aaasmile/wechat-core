package com.d1m.wechat.pamametermodel;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class EstoreProductImageEntity {
    private Long id;
    private Long materialId;
    private Integer seq;
    private Byte type;
    private Byte isDel;
    private String url;
    private String title;
    private String tag;
//    private Long productId;
//    private Long productSpecId;
//    private Long wechatId;
}
