package com.d1m.wechat.model.enums;

public enum EventForwardStatus {

    DELETED(2, "删除"),

    INUSED(1, "使用"),

    ;

    private Integer status;
    private String msg;
    EventForwardStatus(Integer status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
