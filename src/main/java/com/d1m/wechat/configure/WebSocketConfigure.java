package com.d1m.wechat.configure;

import java.util.List;
import javax.annotation.Resource;

import com.alibaba.fastjson.support.spring.FastjsonSockJsMessageCodec;
import org.springframework.context.annotation.Configuration;
import org.springframework.messaging.converter.ByteArrayMessageConverter;
import org.springframework.messaging.converter.MarshallingMessageConverter;
import org.springframework.messaging.converter.MessageConverter;
import org.springframework.messaging.converter.StringMessageConverter;
import org.springframework.messaging.simp.config.MessageBrokerRegistry;
import org.springframework.scheduling.TaskScheduler;
import org.springframework.web.socket.config.annotation.AbstractWebSocketMessageBrokerConfigurer;
import org.springframework.web.socket.config.annotation.EnableWebSocketMessageBroker;
import org.springframework.web.socket.config.annotation.StompEndpointRegistry;

import com.d1m.wechat.customservice.controller.CustomServiceController;
import com.d1m.wechat.integration.spring.FastJsonMessageConverter;

@Configuration
@EnableWebSocketMessageBroker
public class WebSocketConfigure extends AbstractWebSocketMessageBrokerConfigurer {

    @Resource
    private TaskScheduler defaultSockJsTaskScheduler;

    @Override
    public void configureMessageBroker(MessageBrokerRegistry registry) {
        registry.enableSimpleBroker("/topic")
                .setTaskScheduler(defaultSockJsTaskScheduler);
        registry.setApplicationDestinationPrefixes("/custom-service");
    }

    @Override
    public void registerStompEndpoints(StompEndpointRegistry registry) {
        registry.addEndpoint("/websocket/custom-service")
                .withSockJS().setMessageCodec(new FastjsonSockJsMessageCodec());
    }

    @Override
    public boolean configureMessageConverters(List<MessageConverter> messageConverters) {
        messageConverters.add(new StringMessageConverter());
        messageConverters.add(new ByteArrayMessageConverter());
        messageConverters.add(new FastJsonMessageConverter());
        messageConverters.add(new MarshallingMessageConverter());
        return true;
    }

    //@Bean
    public CustomServiceController configCustomServiceController() {
        return new CustomServiceController();
    }
}