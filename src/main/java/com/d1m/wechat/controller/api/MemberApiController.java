package com.d1m.wechat.controller.api;

import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.UserInfo;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.UserService;
import com.d1m.wechat.util.SessionCacheUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Map;

@RestController
@RequestMapping("/api/member")
@Api(value = "会员Cookie API", tags = "会员Cookie接口")
public class MemberApiController extends ApiController {

    private Logger log = LoggerFactory.getLogger(MemberApiController.class);

    @Autowired
    private MemberService memberService;
    @Autowired
    private UserService userService;

    @ApiOperation(value = "获取当前Cookie", tags = "媒体接口")
    @ApiResponse(code = 200, message = "返回结果：wechatId#id")
    @RequestMapping(value = "/get/{cookie}", method = RequestMethod.GET)
    public String getMember(
            @ApiParam("Cookie")
            @PathVariable String cookie, HttpSession session,
            HttpServletRequest request, HttpServletResponse response) {
        String result = null;
        try {
            Map<String, Object> mapMember = SessionCacheUtil.getMember(cookie);
            if (mapMember != null) {
                result = mapMember.get("wechatId") + "#" + mapMember.get("id");
            }
        } catch (Exception e) {
            log.error(e.getMessage());
        }
        return result;
    }

    @RequestMapping(value = "/getOpenid", method = RequestMethod.POST, produces = MediaType.TEXT_PLAIN_VALUE)
    @ResponseBody
    public String searchUnionId(@RequestBody UserInfo userInfo) {
        String unionid = userInfo.getUnionid();
        log.debug("userInfo>>" + userInfo.toString());
        User user = userService.login(userInfo.getUsername(), userInfo.getPassword());
        if (user == null || user.getId() == null) {
            return "account or password does not exist!";
        }

        MemberDto query = new MemberDto();
        query.setUnionId(unionid);
        MemberDto memberDto = memberService.searchMember(query);
        if (memberDto == null) {
            return "member does not exist!";
        }
        return memberDto.getOpenId();
    }
}
