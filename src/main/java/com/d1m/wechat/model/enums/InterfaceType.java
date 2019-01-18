package com.d1m.wechat.model.enums;

public enum InterfaceType {

    TAKE_INITIATIVE_PUSH(0,"TAKE_INITIATIVE_PUSH"),
    THIRD_PARTY_PULL(1,"THIRD_PARTY_PULL"),
            ;

    private Integer code;
    private String message;
    InterfaceType(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
