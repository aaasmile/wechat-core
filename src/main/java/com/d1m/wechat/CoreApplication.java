package com.d1m.wechat;

import cn.afterturn.easypoi.handler.inter.IExcelI18nHandler;
import com.d1m.wechat.util.*;
import com.d1m.wechat.wechatclient.ConsulProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.cloud.client.SpringCloudApplication;
import org.springframework.cloud.netflix.feign.EnableFeignClients;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ImportResource;
import org.springframework.context.annotation.PropertySource;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

/**
 * ManageApplication
 *
 * @author f0rb on 2017-05-18.
 */
@SpringCloudApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
@ImportResource(locations = {"classpath:applicationContext.xml", "classpath:wechat-servlet.xml"})
@PropertySource({"classpath:fileupload.properties", "classpath:xxl-job-executor.properties"})
@EnableCaching(proxyTargetClass = true)
@ServletComponentScan("com.d1m")
@ComponentScan(basePackages = "com.d1m")
@EnableFeignClients
public class CoreApplication {

    public static void main(String[] args) {
        ConsulProperties consulProperties = new ConsulProperties();
        consulProperties.onStartup();
        SpringApplication.run(CoreApplication.class, args);
    }

    @Bean
    public AppContextUtils appContextUtils() {
        return new AppContextUtils();
    }

    @Bean
    public SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(5 * 1000);
        factory.setReadTimeout(5 * 1000);
        return factory;
    }

    @Bean
    public RestTemplate restTemplate() {
        RestTemplate restTemplate = new RestTemplate();
        restTemplate.setRequestFactory(simpleClientHttpRequestFactory());
        return restTemplate;
    }

    @Bean
    public IExcelI18nHandler iExcelI18nHandler() {
        return new IExcelI18nHandlerImpl();
    }

    @Bean
    public MemberExcelDateHandler memberExcelDateHandler() {
        return new MemberExcelDateHandler();
    }

    @Bean
    DistributedLock distributedLock(RedisTemplate redisTemplate) {
        return new RedisDistributedLock(redisTemplate);
    }

}
