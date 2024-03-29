package com.d1m.wechat.model.enums;

public enum InterfaceStatus {
    EFFECT_OF(0,"EFFECT_OF"),   //停用
    NOT_EFFECT(1,"NOT_EFFECT"), // 启用
    ;
    private Integer code;
    private String message;
    InterfaceStatus(Integer code, String message){
        this.code = code;
        this.message = message;
    }

}
