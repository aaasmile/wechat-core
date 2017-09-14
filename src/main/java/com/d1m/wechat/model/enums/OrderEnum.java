package com.d1m.wechat.model.enums;

/**
 * Created by Owen Jia on 2017/6/14.
 */
public enum OrderEnum {
    COMPANY(1,"公司"),
    PERSONAL(2,"个人的"),

    ORDINARY_VALUEADD_TAX_INVOICE(3,"普通增值税发票"),

    WORKDAY(4,"工作日"),
    NOT_WORKDAY(5,"非工作日"),

    ORDER_NOT_PAY(9,"未支付"),
    ORDER_PAY_OFF(10,"已支付"),
    ORDER_SENDOUT(13,"已发货"),
    ORDER_END(12,"完成"),
    ORDER_CANCEL(11,"订单取消"),

    INVOICE_SENDOUT(15,"未寄出"),
    INVOICE_NOT_SEND(16,"已寄出");

    private Integer code;
    private String desc;

    OrderEnum(Integer code, String desc) {
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

    public Integer getCodeByName(OrderEnum name){
        if(name == null) return null;
        for(OrderEnum t: OrderEnum.values()){
            if(t == name) return t.getCode();
        }
        return null;
    }
}
