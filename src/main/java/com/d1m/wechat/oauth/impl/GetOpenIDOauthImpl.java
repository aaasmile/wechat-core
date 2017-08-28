package com.d1m.wechat.oauth.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.d1m.wechat.client.model.WxUser;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.oauth.IOauth;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.util.Base64Util;
import com.d1m.wechat.util.MD5;

/**
 * Created by d1m on 2016/9/20.
 * 通用回调传openID接口
 */
@Component
public class GetOpenIDOauthImpl implements IOauth {
    private Logger log = LoggerFactory.getLogger(GetOpenIDOauthImpl.class);
    private static String defaultToken = "D1MOAUTHTOKEN";
    private static String defaultSignKey = "D1MSIGNKEY";
    private static String defaultDataParam = "data";
    private static String defaultSignParam = "sign";

    @Resource
    private MemberService memberService;

    @Override
    public void execute(HttpServletRequest request, HttpServletResponse response, WxUser wuser, Map<String, Object> params) {
        try {
            log.info("params : {}", params);
            Integer wechatId = (Integer) params.get("wechatId");
            String token = (String) params.get("token");
            String redirectUrl = (String) params.get("redirectUrl");

            String signKey = (String) params.get("signKey");
            String vToken = (String) params.get("vToken");

            // 默认Token及Sign
            if(StringUtils.isBlank(vToken)){
                vToken = defaultToken;
            }
            if(StringUtils.isBlank(signKey)){
                signKey = defaultSignKey;
            }

            // 回调参数名
            String dataParam = (String) params.get("dataParam");
            if(StringUtils.isBlank(dataParam)){
                dataParam = defaultDataParam;
            }
            String signParam = (String) params.get("signParam");
            if(StringUtils.isBlank(signParam)){
                signParam = defaultSignParam;
            }

            // 来源参数
            String source = (String) params.get("source");
            String keyword = (String) params.get("keyword");

            if(!vToken.equals(token)){
                JSONObject json = new JSONObject();
                json.put("resultCode", "60002");
                json.put("msg", "TOKEN_INVALID");
                PrintWriter out = response.getWriter();
                out.print(json);
                out.flush();
                out.close();
            }else{
                Member member = memberService.getMemberByOpenId(wechatId, wuser.getOpenid());
                if(member==null){
                    member = new Member();
                    member.setActivity((byte) 5);
                    member.setBatchsendMonth(0);
                    member.setIsSubscribe(false);
                    member.setOpenId(wuser.getOpenid());
                    member.setWechatId(wechatId);
                    member.setCreatedAt(new Date());
                    member.setSource(source);
                    member.setKeyword(keyword);
                    member.setSex((byte)0);
                    member.setUnionId(wuser.getUnionid());
                    member.setNickname(wuser.getNickname());
                    member.setHeadImgUrl(wuser.getHeadimgurl());
                    member.setLocalHeadImgUrl(wuser.getHeadimgurl());
                    memberService.save(member);
                }

                log.info("callbackUrl : {}", redirectUrl);
                if(StringUtils.isNotBlank(redirectUrl)){
                    JSONObject json = new JSONObject();
                    json.put("open_id",wuser.getOpenid());
                    json.put("nickname",wuser.getNickname());
                    json.put("union_id",wuser.getUnionid());
                    json.put("head_img_url",wuser.getHeadimgurl());
                    String data = Base64Util.getBase64(json.toJSONString());
                    redirectUrl = addPathParam(redirectUrl,dataParam, URLEncoder.encode(data,"UTF-8"));
                    String sign = MD5.MD5Encode(data+signKey);
                    redirectUrl = addPathParam(redirectUrl,signParam,sign);
                    log.info("callbackUrl OK : {}", redirectUrl);
                    response.sendRedirect(redirectUrl);
                }else{
                    JSONObject json = new JSONObject();
                    json.put("resultCode", "60001");
                    json.put("msg", "NO_CALLBACK_URL");
                    PrintWriter out = response.getWriter();
                    out.print(json);
                    out.flush();
                    out.close();
                }
            }
        } catch (Exception e) {
            log.error("GetOpenIDOauth error:",e);
            JSONObject json = new JSONObject();
            json.put("resultCode", "0");
            json.put("msg", "SYSTEM_ERROR");
            PrintWriter out = null;
            try {
                out = response.getWriter();
                out.print(json);
                out.flush();
            } catch (IOException e1) {
                log.error("", e1);
            } finally {
                if (out != null) {
                    out.close();
                }
            }
        }
    }

    private String addPathParam(String url,String key,String value){
        if(StringUtils.isBlank(url) || StringUtils.isBlank(key) || StringUtils.isEmpty(value)){
            return url;
        }
        String str = "";
        if(!url.contains("/")){//编码后的URL不可能存在/
            //编码化url处理 %3F->? %3D->=
            if(url.contains("%3F") && !url.contains("%3D")){
                str = "";
            }else if(url.contains("%3F") && url.contains("%3D")){
                str = "%26";
            }else{
                str = "%3F";
            }
        }else{
            //非编码化url处理
            if(url.contains("?") && !url.contains("=")){//http://xxx.cn?
                str = "";
            }else if(url.contains("?") && url.contains("=")){//http://xxx.cn?a=b
                str = "&";
            }else{
                str = "?";
            }
        }
        return url += (str + key + "=" + value);
    }
}
