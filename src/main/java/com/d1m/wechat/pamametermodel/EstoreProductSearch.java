package com.d1m.wechat.pamametermodel;

import com.alibaba.fastjson.JSONObject;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class EstoreProductSearch extends BaseModel implements Cloneable{
    private Long pId;
    private Long specId;
    private String name;
    private String desc;
    private String sku;
    private Byte status;
    private Long wechatId;

    @Override
    public EstoreProductSearch clone() {
        EstoreProductSearch product = null;
        try{
            product = (EstoreProductSearch)super.clone();
        }catch(CloneNotSupportedException e) {
            e.printStackTrace();
        }
        return product;
    }
}
