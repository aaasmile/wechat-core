package com.d1m.wechat.controller.interfaces;

import com.d1m.wechat.CoreApplication;
import com.d1m.wechat.wechatclient.ConsulProperties;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.google.common.collect.Lists;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static com.d1m.wechat.controller.TestController.*;

/**
 * Created by jone.wang on 2019/2/21.
 * Description:
 */
@SuppressWarnings("Duplicates")
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
@Slf4j
public class InterfaceRabbitMQListenerTest {

    static {
        ConsulProperties consulProperties = new ConsulProperties();
        consulProperties.onStartup();
    }


    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void process() {

        final MapType mapType = objectMapper.getTypeFactory().constructMapType(HashMap.class, String.class, String.class);

        Lists.newArrayList(subscribe, scan, click, userGetCard).forEach(e -> {
            try {
                final Map<String, String> map = objectMapper.readValue(e, mapType);
                for (int i = 1; i < 5; i++) {
                    amqpTemplate.convertAndSend("wechat.notify.event", "event." + map.get("Event"), map);
                }
            } catch (IOException e1) {
                log.error("Error", e1);
            }
        });

    }
}