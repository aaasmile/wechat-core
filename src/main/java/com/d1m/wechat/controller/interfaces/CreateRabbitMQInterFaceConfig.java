package com.d1m.wechat.controller.interfaces;

import com.d1m.wechat.util.Constants;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

/**
 * @program: wechat-core
 * @Date: 2019/2/20 11:14
 * @Author: Liu weilin
 * @Description: 创建事件转发队列
 */
@Component
public class CreateRabbitMQInterFaceConfig {

    /**
     * 创建队列
     * @return
     */
    @Bean
    public Queue queueMessage1() {
        return new Queue(Constants.INTERFACE_QUEUE);
    }

    /**
     * 交换机
     * @return
     */
    @Bean
    public TopicExchange exchange() {
        return new TopicExchange(Constants.INTERFACE_EXCHANGE);
    }

    /**
     * 建立队列和交换机之间的绑定关系
     * @param exchange
     * @return
     */
    @Bean
    public Binding bindingExchangeMessage(Queue queueMessage1, TopicExchange exchange) {

        return BindingBuilder.bind(queueMessage1).to(exchange).with("event.#");
    }


}
