package com.d1m.wechat.controller;

import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.util.Security;
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
        Map<String, String> map = new HashMap<>();
        map.put("name", "zhang san");
        map.put("wechatId", "11");
        amqpTemplate.convertAndSend("INTERFACE_EXCHANGE", "event.message", map);
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
