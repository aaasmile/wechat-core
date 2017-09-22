package com.d1m.wechat.model.enums;

/**
 * Created by Owen Jia on 2017/6/14.
 */
public enum OrderEnum {
    COMPANY((byte)1,"公司"),
    PERSONAL((byte)2,"个人"),

    ORDINARY_VALUEADD_TAX_INVOICE((byte)1,"普通增值税发票"),
    GENERAL_INVOICE((byte)2,"普通发票"),

    ALLDAYS((byte)0,"都可以"),
    WORKDAY((byte)1,"工作日"),
    NOT_WORKDAY((byte)2,"非工作日"),

    ORDER_NOT_PAY((byte)0,"未支付"),
    ORDER_PAY_OFF((byte)1,"已支付"),
    ORDER_CANCEL((byte)2,"订单取消"),
    ORDER_READY_SENDOUT((byte)3,"待发货"),
    ORDER_SENDOUT((byte)4,"已发货"),
    ORDER_END((byte)5,"完成"),

    INVOICE_SENDOUT((byte)0,"未寄出"),
    INVOICE_NOT_SEND((byte)1,"已寄出");

    private Byte code;
    private String desc;

    OrderEnum(Byte code, String desc) {
        this.code = code;
        this.desc = desc;
    }

    public Byte getCode() {
        return code;
    }

    public void setCode(Byte code) {
        this.code = code;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

}

