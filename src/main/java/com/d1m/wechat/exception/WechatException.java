package com.d1m.wechat.exception;

import com.d1m.wechat.util.Message;

public class WechatException extends RuntimeException {

    private Message message;
    private Integer code;

    /**
     *
     */
    private static final long serialVersionUID = -2164207829730030776L;

    public WechatException(Message message) {
        super(message.name());
        this.message = message;
    }

    public WechatException(Message message, String detail) {
        super(message.name() + ":" + detail);
        this.message = message;
    }

    public Message getMessageInfo() {
        return message;
    }

    public WechatException(Integer code, String message) {
        super(message);
        this.code = code;
    }

}
