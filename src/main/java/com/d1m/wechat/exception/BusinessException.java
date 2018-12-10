package com.d1m.wechat.exception;

import com.d1m.wechat.util.Message;

/**
 * @program: wechat-core
 * @Date: 2018/11/30 15:55
 * @Author: Liu weilin
 * @Description:
 */
public class BusinessException extends WechatException{
    private static final long serialVersionUID = 3713215932159891402L;

    public BusinessException(Message ed, String... params) {
        super(ed.getCode(), ed.withParams((Object[])params));
    }

}
