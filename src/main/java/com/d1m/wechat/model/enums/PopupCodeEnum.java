package com.d1m.wechat.model.enums;

import org.apache.commons.lang.StringUtils;

/**
 * Created by Owen Jia on 2017/6/14.
 */
public enum PopupCodeEnum {

    SYSTEM_ERROR(0, "系统异常"),
    SUCCESS(1, "操作成功"),
    NO_PERMISSION(403, "无权限"),
    ILLEGAL_REQUEST(105, "非法请求"),
    INVALID_REQUEST_PARAMS(2,"请求参数不合法"),
    /**
     * Order module,80000
     */
    POPUP_OUT_STOCK(8001,"库存不够"),
    POPUP_ORDER_SMS_NOTIFY(8002,"订单短信通知"),
    POPUP_ORDER_SMS_NOT_NOTIFY(8003,"订单不需要短信通知"),
    ORDER_HAS_PAY(8004,"订单已经支付"),
    PAY_PARAM_INVALID(8005,"支付参数不合法"),
    PAY_SYSTEM_ERROR(8006,"支付系统异常"),
    PAY_SYSTEM_PARAMS_INVALID(8007,"支付返回参数不合法"),
    ORDER_NOT_EXSIST(8008,"订单不存在"),
    PAY_CONFIG_ERROR(8009,"后台配置错误"),

    SMS_CONTENT_INVALID(8010,"短信发送内容不合法"),
    SMS_IDS_NOT_EMPTY(8011,"订单号不能为空"),
    SMS_SEND_FAILURE(8012,"短信发送不成功"),
    PAY_NOTIFY_ERROR(8013,"支付异步调用异常"),
    GOODS_HAS_ORDER(8014,"该商品已经存在订单了");

    private Integer code;
    private String desc;

    PopupCodeEnum(Integer code, String desc) {
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

    /**
     * 根据desc查找enum
     * @param desc
     * @return
     */
    public static PopupCodeEnum getCodeByDesc(String desc){
        if(StringUtils.isEmpty(desc)) return null;
        for(PopupCodeEnum t : PopupCodeEnum.values()){

            if(desc.equals(t.toString())){
                return t;
            }
        }
        return null;
    }
}
