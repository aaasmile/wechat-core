package com.d1m.wechat.controller;

import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.util.WXPayUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.amqp.core.AmqpTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Profile;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by jone.wang on 2018/12/4.
 * Description:
 */
@RestController
@Profile({"dev", "uat"})
@RequestMapping("/test")
public class TestController {

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private AmqpTemplate amqpTemplate;

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
        Map<String,String> map = new HashMap<>();
        map.put("name","zhang san");
       amqpTemplate.convertAndSend("INTERFACE_EXCHANGE","event.message", map);
        return BaseResponse.builder()
                .data(null)
                .msg("success")
                .resultCode(1)
                .build();
    }


}
