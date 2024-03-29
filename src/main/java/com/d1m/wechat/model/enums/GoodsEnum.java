package com.d1m.wechat.model.enums;

/**
 * Created by Owen Jia on 2017/6/14.
 */
public enum GoodsEnum {
    SALING(0,"在售中"),
    OFF_SALING(1,"已下架"),
    REAL_GOODS(1,"实物商品");

    private Integer code;
    private String desc;

    GoodsEnum(Integer code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

//    public Integer getCodeByName(GoodsEnum name){
//        if(name == null) return null;
//        for(GoodsEnum t: GoodsEnum.values()){
//            if(t == name) return t.getCode();
//        }
//        return null;
//    }
}
