package com.d1m.wechat.model.popup;

/**
 * Created by Owen Jia on 2017/6/15.
 */
public enum PayTypeEnum {

    UNKNOW(0,"未支付"),
    WECHAT_PAY(1,"微信"),
    ALIPAY(2,"支付宝"),
    UNION_PAY(3,"银联");

    private Integer code;
    private String desc;

    PayTypeEnum(Integer code, String desc) {
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

}
