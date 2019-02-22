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

/**
 * Created by jone.wang on 2019/2/21.
 * Description:
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = CoreApplication.class)
@Slf4j
public class InterfaceRabbitMQListenerTest {

    static {
        ConsulProperties consulProperties = new ConsulProperties();
        consulProperties.onStartup();
    }

    private String subscribe = "{\n" +
            "    \"ToUserName\": \"gh_cb4da662addc\",\n" +
            "    \"wechatId\": \"11\",\n" +
            "    \"FromUserName\": \"oNgDLwYYbDLbDBJwImMuhd6RL5AE\",\n" +
            "    \"CreateTime\": \"1523422781\",\n" +
            "    \"MsgType\": \"event\",\n" +
            "    \"Event\": \"subscribe\"\n" +
            "  }";

    private String scan = "{\n" +
            "    \"ToUserName\": \"gh_cb4da662addc\",\n" +
            "    \"wechatId\": \"11\",\n" +
            "    \"FromUserName\": \"oNgDLwSm9vyDdAhCz-yfyISNwGfM\",\n" +
            "    \"CreateTime\": \"1523604229\",\n" +
            "    \"MsgType\": \"event\",\n" +
            "    \"Event\": \"SCAN\",\n" +
            "    \"EventKey\": \"FMJ1\",\n" +
            "    \"Ticket\": \"gQEL8jwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyQTJ3OTBwUzBmYjExMDAwME0wN0kAAgSCRdBaAwQAAAAA\"\n" +
            "  }";

    private String click = "{\n" +
            "    \"ToUserName\": \"gh_cb4da662addc\",\n" +
            "    \"wechatId\": \"11\",\n" +
            "    \"FromUserName\": \"oNgDLwdFG7pP92NhEAZKOFpoTGQ8\",\n" +
            "    \"CreateTime\": \"1523600899\",\n" +
            "    \"MsgType\": \"event\",\n" +
            "    \"Event\": \"CLICK\",\n" +
            "    \"EventKey\": \"31\"\n" +
            "  }";

    private String userGetCard = "{\n" +
            "    \"ToUserName\": \" gh_fc0a06a20993 \",\n" +
            "    \"wechatId\": \"11\",\n" +
            "    \"FromUserName\": \" oZI8Fj040-be6rlDohc6gkoPOQTQ \",\n" +
            "    \"CreateTime\": \"1472551036\",\n" +
            "    \"MsgType\": \" event \",\n" +
            "    \"Event\": \" user_get_card \",\n" +
            "    \"CardId\": \" pZI8Fjwsy5fVPRBeD78J4RmqVvBc \",\n" +
            "    \"IsGiveByFriend\": \"0\",\n" +
            "    \"UserCardCode\": \" 226009850808 \",\n" +
            "    \"FriendUserName\": \"  \",\n" +
            "    \"OuterId\": \"0\",\n" +
            "    \"OldUserCardCode\": \"  \",\n" +
            "    \"OuterStr\": \" 12b \",\n" +
            "    \"IsRestoreMemberCard\": \"0\",\n" +
            "    \"IsRecommendByFriend\": \"0\",\n" +
            "    \"UnionId\": \"o6_bmasdasdsad6_2sgVt7hMZOPfL\"\n" +
            "  }";

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
                    amqpTemplate.convertAndSend("INTERFACE_EXCHANGE", "event." + map.get("Event"), map);
                }
            } catch (IOException e1) {
                log.error("Error", e1);
            }
        });

    }
}