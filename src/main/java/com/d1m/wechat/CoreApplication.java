package com.d1m.wechat;

import com.d1m.wechat.service.engine.EngineContext;
import com.d1m.wechat.util.AppContextUtils;
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

/**
 * ManageApplication
 *
 * @author f0rb on 2017-05-18.
 */
@SpringCloudApplication
@EnableAutoConfiguration
@EnableConfigurationProperties
@ImportResource(locations = {"classpath:applicationContext.xml", "classpath:wechat-servlet.xml"})
@PropertySource({"classpath:fileupload.properties","classpath:xxl-job-executor.properties"})
@EnableCaching(proxyTargetClass = true)
@ServletComponentScan("com.d1m")
@ComponentScan(basePackages = "com.d1m")
@EnableFeignClients
public class CoreApplication {

    public static void main(String[] args) {
        SpringApplication.run(CoreApplication.class, args);
    }

    @Bean
    public AppContextUtils appContextUtils() {
        return new AppContextUtils();
    }

    @Bean
    public EngineContext taskContext() {
        return new EngineContext();
    }
}
