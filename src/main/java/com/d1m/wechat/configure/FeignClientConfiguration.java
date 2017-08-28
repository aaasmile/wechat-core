package com.d1m.wechat.configure;

import feign.Feign;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Scope;

/**
 * FeignClientConfiguration
 *
 * @author stoney.liu on 2017-07-04.
 */
public class FeignClientConfiguration {

    @Bean
    @Scope("prototype") // turn off Histrix
    public Feign.Builder feignBuilder() {
        return Feign.builder();
    }
}
