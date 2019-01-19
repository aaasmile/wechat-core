package com.d1m.wechat.web;

import com.d1m.wechat.anno.Jackson;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.lang.reflect.Type;
import java.util.Objects;

/**
 * Created by jone.wang on 2019/1/19.
 * Description:
 */
public class AnnotationMappingJackson2HttpMessageConverter extends MappingJackson2HttpMessageConverter {

    public AnnotationMappingJackson2HttpMessageConverter(ObjectMapper objectMapper) {
        super(objectMapper);
    }

    @Override
    public boolean canRead(Type type, Class<?> contextClass, MediaType mediaType) {
        if (Objects.nonNull(contextClass)) {
            return contextClass.isAnnotationPresent(Jackson.class) && super.canRead(type, contextClass, mediaType);
        } else {
            return super.canRead(type, null, mediaType);
        }
    }

    @Override
    public boolean canRead(Class<?> clazz, MediaType mediaType) {
        if (Objects.nonNull(clazz)) {
            return clazz.isAnnotationPresent(Jackson.class) && super.canRead(clazz, mediaType);
        } else {
            return super.canRead(null, mediaType);
        }
    }


    @Override
    public boolean canWrite(Class<?> clazz, MediaType mediaType) {
        if (Objects.nonNull(clazz)) {
            return clazz.isAnnotationPresent(Jackson.class) && super.canWrite(clazz, mediaType);
        }
        return super.canWrite(null, mediaType);
    }

    @Override
    public boolean canWrite(Type type, Class<?> clazz, MediaType mediaType) {
        if (Objects.nonNull(clazz)) {
            return clazz.isAnnotationPresent(Jackson.class) && super.canWrite(type, clazz, mediaType);
        } else {
            return super.canWrite(type, null, mediaType);
        }
    }
}
