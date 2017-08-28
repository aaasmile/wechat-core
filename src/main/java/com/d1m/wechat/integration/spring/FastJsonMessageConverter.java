package com.d1m.wechat.integration.spring;

/**
 * FastJsonMessageConverter
 *
 * @author f0rb on 2017-03-15.
 */


import java.io.IOException;
import java.nio.charset.Charset;

import com.alibaba.fastjson.JSON;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageHeaders;
import org.springframework.messaging.converter.AbstractMessageConverter;
import org.springframework.messaging.converter.MessageConversionException;
import org.springframework.util.MimeType;

public class FastJsonMessageConverter extends AbstractMessageConverter {

    public static final String DEFAULT_CHARSET = "UTF-8";

    private volatile String defaultCharset = DEFAULT_CHARSET;

    public FastJsonMessageConverter() {
        super(new MimeType("application", "json", Charset.forName("UTF-8")));
    }

    @Override
    protected boolean supports(Class<?> clazz) {
        return true;
    }

    @Override
    protected Object convertFromInternal(Message<?> message, Class<?> targetClass, Object conversionHint) {
        Object payload = message.getPayload();
        // Note: in the view case, calling withType instead of forType for compatibility with Jackson <2.5
        if (payload instanceof byte[]) {
            return JSON.parseObject((byte[]) payload, targetClass);
        } else {
            return JSON.parseObject(payload.toString(), targetClass);
        }
    }

    @Override
    protected Object convertToInternal(Object payload, MessageHeaders headers, Object conversionHint) {
        try {
            payload = JSON.toJSONString(payload);
            if (byte[].class == getSerializedPayloadClass()) {
                payload = ((String) payload).getBytes(DEFAULT_CHARSET);

            }
        } catch (IOException ex) {
            throw new MessageConversionException("Could not write JSON: " + ex.getMessage(), ex);
        }
        return payload;
    }


}