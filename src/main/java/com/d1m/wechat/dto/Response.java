package com.d1m.wechat.dto;

/**
 * @program: wechat-core
 * @Date: 2018/12/25 0:11
 * @Author: Liu weilin
 * @Description:
 */
public class Response {
    private String errcode;
    private String errmsg;

    public String getErrcode() {
        return errcode;
    }

    public void setErrcode(String errcode) {
        this.errcode = errcode;
    }

    public String getErrmsg() {
        return errmsg;
    }

    public void setErrmsg(String errmsg) {
        this.errmsg = errmsg;
    }
}
