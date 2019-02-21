package com.d1m.wechat.model.enums;

public enum InterfaceStatus {
    NOT_EFFECT(0,"NOT_EFFECT"), // 启用
    EFFECT_OF(1,"EFFECT_OF"),   //停用
    ;
    private Integer code;
    private String message;
    InterfaceStatus(Integer code, String message){
        this.code = code;
        this.message = message;
    }

}
