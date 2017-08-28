package com.d1m.wechat.oauth.impl;

import cn.d1m.wechat.client.model.WxUser;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.oauth.IOauth;
import com.d1m.wechat.service.MemberService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;
import java.util.Map;

/**
 * 微店 微信静默授权参数处理
 * Created by Owen Jia on 2017/6/9.
 */
@Component
public class PopupIOauthImpl implements IOauth{
    Logger log = LoggerFactory.getLogger(PopupIOauthImpl.class);
    @Autowired
    private MemberService memberService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, WxUser wxUser, Map<String, Object> params) {
        String baseUrl = (String) params.get("baseUrl");
        String targetUrl = (String) params.get("targetUrl");
        String shortUrl = (String) params.get("short_url");
        try {
            Integer wechatId = (Integer) params.get("wechatId");
            Member member = memberService.getMemberByOpenId(wechatId, wxUser.getOpenid());
            if(member == null){
                member = new Member();
                member.setOpenId(wxUser.getOpenid());
                member.setActivity((byte) 5);
                member.setBatchsendMonth(0);
                member.setIsSubscribe(false);
                member.setWechatId(wechatId);
                member.setCreatedAt(new Date());
                memberService.save(member);
            }

            log.info("[popup] parms;" + params.toString() + ",wxUser:" + wxUser.toString() + "member:" + member.toString());
            this.addCookie(response, wechatId, member.getId(), wxUser.getOpenid());
            response.sendRedirect(baseUrl + targetUrl);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addCookie(HttpServletResponse response, Integer wechatId, Integer memberId, String openid){
        Cookie cookieMember = new Cookie("memberId", memberId+"");
        cookieMember.setPath("/");
        response.addCookie(cookieMember);

        Cookie cookieWechat = new Cookie("wechatId", wechatId+"");
        cookieWechat.setPath("/");
        response.addCookie(cookieWechat);

        Cookie cookieOpenid = new Cookie("openId", openid+"");
        cookieOpenid.setPath("/");
        response.addCookie(cookieOpenid);
    }
}
