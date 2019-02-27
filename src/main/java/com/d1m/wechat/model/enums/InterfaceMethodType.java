package com.d1m.wechat.model.enums;

public enum InterfaceMethodType {
    POST(0,"POST"),
    GET(1,"GET"),
            ;

    private Integer code;
    private String message;
    InterfaceMethodType(Integer code, String message){
        this.code = code;
        this.message = message;
    }
}
