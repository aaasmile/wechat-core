package com.d1m.wechat.controller;

import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.util.Security;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.type.MapType;
import com.google.common.collect.Lists;
import io.swagger.annotations.Api;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by jone.wang on 2018/12/4.
 * Description:
 */
@SuppressWarnings("unchecked")
@RestController
@Profile({"dev", "uat"})
@Api(value = "测试接口", tags = "只存在dev和qa上")
@RequestMapping("/test")
@Slf4j
public class TestController {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

    @Autowired
    private ObjectMapper objectMapper;


    public static final String subscribe = "{\n" +
            "    \"ToUserName\": \"gh_cb4da662addc\",\n" +
            "    \"wechatId\": \"11\",\n" +
            "    \"FromUserName\": \"oNgDLwYYbDLbDBJwImMuhd6RL5AE\",\n" +
            "    \"CreateTime\": \"1523422781\",\n" +
            "    \"MsgType\": \"event\",\n" +
            "    \"Event\": \"subscribe\"\n" +
            "  }";

    public static final String scan = "{\n" +
            "    \"ToUserName\": \"gh_cb4da662addc\",\n" +
            "    \"wechatId\": \"11\",\n" +
            "    \"FromUserName\": \"oNgDLwSm9vyDdAhCz-yfyISNwGfM\",\n" +
            "    \"CreateTime\": \"1523604229\",\n" +
            "    \"MsgType\": \"event\",\n" +
            "    \"Event\": \"SCAN\",\n" +
            "    \"EventKey\": \"FMJ1\",\n" +
            "    \"Ticket\": \"gQEL8jwAAAAAAAAAAS5odHRwOi8vd2VpeGluLnFxLmNvbS9xLzAyQTJ3OTBwUzBmYjExMDAwME0wN0kAAgSCRdBaAwQAAAAA\"\n" +
            "  }";

    public static final String click = "{\n" +
            "    \"ToUserName\": \"gh_cb4da662addc\",\n" +
            "    \"wechatId\": \"11\",\n" +
            "    \"FromUserName\": \"oNgDLwdFG7pP92NhEAZKOFpoTGQ8\",\n" +
            "    \"CreateTime\": \"1523600899\",\n" +
            "    \"MsgType\": \"event\",\n" +
            "    \"Event\": \"CLICK\",\n" +
            "    \"EventKey\": \"31\"\n" +
            "  }";

    public static final String userGetCard = "{\n" +
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

    private String secret = "fca216d4ebae108ccb26efb1d0a96a36";

    @GetMapping("/member")
    public BaseResponse selectByMobile(@RequestParam String mobile) {
        final Member member = new Member();
        member.setMobile(mobile);
        final Member one = memberMapper.selectOne(member);
        final MemberDto memberDto = new MemberDto();
        memberDto.setId(1763304);
        final MemberDto dto = memberMapper.selectByOpenId("oJOHet8x0o1O23c3FENBoBsiCRKU", 6);
        return BaseResponse.builder()
                .data(dto)
                .msg("success")
                .resultCode(1)
                .build();
    }

    @GetMapping("/sendMessage")
    public BaseResponse sendMessage() {

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

        return BaseResponse.builder()
                .data(null)
                .msg("success")
                .resultCode(1)
                .build();
    }

    @PostMapping("/interface")
    public BaseResponse<String> encryptPayload(HttpServletRequest request) throws IOException {
        BufferedReader bufferedReader = null;
        StringBuilder stringBuilder = new StringBuilder();
        try {
            InputStream inputStream = request.getInputStream();
            if (inputStream != null) {
                bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                char[] charBuffer = new char[128];
                int bytesRead = -1;
                while ((bytesRead = bufferedReader.read(charBuffer)) > 0) {
                    stringBuilder.append(charBuffer, 0, bytesRead);
                }
            } else {
            }
        } finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
        }
        final String encryptBody = stringBuilder.toString();
        log.info("encrypt body: {}", encryptBody);

        final String rawBody = Security.decrypt(encryptBody, secret);

        log.info("raw body: {}", rawBody);
        return BaseResponse.builder()
                .resultCode(1)
                .msg("success")
                .data(rawBody)
                .build();
    }


}
