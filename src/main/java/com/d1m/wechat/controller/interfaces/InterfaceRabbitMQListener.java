package com.d1m.wechat.controller.interfaces;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Headers;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import java.util.Map;

/**
 * @program: wechat-core
 * @Date: 2019/2/20 13:36
 * @Author: Liu weilin
 * @Description: 监听/接收事件消息
 */
@Component
public class InterfaceRabbitMQListener {
    private static final Logger logger = LoggerFactory.getLogger(InterfaceRabbitMQListener.class);
    private static final String INTERFACE_QUEUE_V1 = "INTERFACE_QUEUE_V1";

    //@RabbitHandler
    @RabbitListener(queues = INTERFACE_QUEUE_V1)
    public void process(@Payload Map<String, String> message, @Headers Map receivedRoutingKey) {
        try {
            logger.info("message:{}", message);
            logger.info("routingkey :{}", receivedRoutingKey);
        } catch (Exception e) {
            e.getMessage();
        }


    }


}
