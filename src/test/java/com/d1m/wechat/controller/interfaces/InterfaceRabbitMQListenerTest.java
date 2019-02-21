package com.d1m.wechat.controller.interfaces;

import com.d1m.wechat.CoreApplication;
import com.d1m.wechat.wechatclient.ConsulProperties;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jone.wang on 2019/2/21.
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
public class InterfaceRabbitMQListenerTest {

    static {
        ConsulProperties consulProperties = new ConsulProperties();
        consulProperties.onStartup();
    }

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Test
    public void process() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhang san");
        map.put("wechatId", "11");
        map.put("age", "15");
        map.put("sex", "unknown");
        for (int i = 1; i < 5; i++) {
            amqpTemplate.convertAndSend("INTERFACE_EXCHANGE", "event.subscribe", map);
        }
    }
}