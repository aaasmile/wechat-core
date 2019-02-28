package com.d1m.wechat.web;

import com.alibaba.fastjson.serializer.SerializerFeature;
import com.alibaba.fastjson.support.spring.FastJsonHttpMessageConverter;
import com.d1m.wechat.anno.Jackson;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.common.collect.Lists;
import org.springframework.http.HttpInputMessage;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.MediaType;
import org.springframework.http.converter.GenericHttpMessageConverter;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Created by jone.wang on 2019/1/19.
 * Description: 兼容fastjson和jackson，通过注解@Jackson判断使用序列化工具
 */
public class JsonHttpMessageConverterStrategy implements GenericHttpMessageConverter {

    private FastJsonHttpMessageConverter fastJsonHttpMessageConverter;

    private MappingJackson2HttpMessageConverter mappingJackson2HttpMessageConverter;

    @SuppressWarnings("deprecation")
    public JsonHttpMessageConverterStrategy(ObjectMapper objectMapper) {
        final ArrayList<MediaType> mediaTypes = Lists.newArrayList(MediaType.APPLICATION_JSON,
                new MediaType("application", "*+json"), MediaType.TEXT_HTML);
        mappingJackson2HttpMessageConverter = new MappingJackson2HttpMessageConverter(objectMapper);
        mappingJackson2HttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        fastJsonHttpMessageConverter = new FastJsonHttpMessageConverter();
        fastJsonHttpMessageConverter.setSupportedMediaTypes(mediaTypes);
        fastJsonHttpMessageConverter.setFeatures(SerializerFeature.QuoteFieldNames, SerializerFeature.WriteDateUseDateFormat);

    }

    @Override
    public boolean canRead(Type type, Class contextClass, MediaType mediaType) {
        return mappingJackson2HttpMessageConverter.canRead(type, contextClass, mediaType);
    }

    @Override
    public Object read(Type type, Class contextClass, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        if (Objects.nonNull(contextClass) && contextClass.isAnnotationPresent(Jackson.class)) {
            return mappingJackson2HttpMessageConverter.read(type, contextClass, inputMessage);
        } else {
            return fastJsonHttpMessageConverter.read(type, contextClass, inputMessage);
        }
    }

    @Override
    public boolean canWrite(Type type, Class clazz, MediaType mediaType) {
        return mappingJackson2HttpMessageConverter.canWrite(type, clazz, mediaType);
    }

    @Override
    public void write(Object o, Type type, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (Objects.nonNull(o) && o.getClass().isAnnotationPresent(Jackson.class)) {
            mappingJackson2HttpMessageConverter.write(o, type, contentType, outputMessage);
        } else {
            fastJsonHttpMessageConverter.write(o, type, contentType, outputMessage);
        }
    }

    @Override
    public boolean canRead(Class clazz, MediaType mediaType) {
        return mappingJackson2HttpMessageConverter.canRead(clazz, mediaType);
    }

    @Override
    public boolean canWrite(Class clazz, MediaType mediaType) {
        return mappingJackson2HttpMessageConverter.canWrite(clazz, mediaType);
    }

    @Override
    public List<MediaType> getSupportedMediaTypes() {
        return mappingJackson2HttpMessageConverter.getSupportedMediaTypes();
    }

    @Override
    public Object read(Class clazz, HttpInputMessage inputMessage) throws IOException, HttpMessageNotReadableException {
        if (Objects.nonNull(clazz) && clazz.isAnnotationPresent(Jackson.class)) {
            return mappingJackson2HttpMessageConverter.read(clazz, inputMessage);
        } else {
            return fastJsonHttpMessageConverter.read(clazz, inputMessage);
        }
    }

    @Override
    public void write(Object o, MediaType contentType, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        if (Objects.nonNull(o) && o.getClass().isAnnotationPresent(Jackson.class)) {
            mappingJackson2HttpMessageConverter.write(o, contentType, outputMessage);
        } else {
            fastJsonHttpMessageConverter.write(o, contentType, outputMessage);
        }
    }
}
