package com.d1m.wechat.oauth.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.Map;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import cn.d1m.wechat.client.model.WxUser;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.oauth.IOauth;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.util.Base64Util;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.MD5;
import com.d1m.wechat.util.SessionCacheUtil;

/**
 * Created by d1m on 2016/9/20. 通用回调传openID接口
 */
@Component
public class GetPhiOpenIDOauthImpl implements IOauth {
	private Logger log = LoggerFactory.getLogger(GetPhiOpenIDOauthImpl.class);

	@Resource
	private MemberService memberService;

	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, WxUser wuser,
			Map<String, Object> params) {
		try {
			log.info("params : {}", params);
			Integer wechatId = (Integer) params.get("wechatId");
			String redirectUrl = (String) params.get("redirectUrl");

				Member member = memberService.getMemberByOpenId(wechatId, wuser.getOpenid());
				if (member == null) {
					member = new Member();
					member.setActivity((byte) 5);
					member.setBatchsendMonth(0);
					member.setIsSubscribe(false);
					member.setOpenId(wuser.getOpenid());
					member.setWechatId(wechatId);
					member.setCreatedAt(new Date());
					member.setSex((byte) 0);
					member.setUnionId(wuser.getUnionid());
					member.setNickname(wuser.getNickname());
					member.setHeadImgUrl(wuser.getHeadimgurl());
					member.setLocalHeadImgUrl(wuser.getHeadimgurl());
					memberService.save(member);
				}

				log.info("callbackUrl : {}", redirectUrl);
				if (StringUtils.isNotBlank(redirectUrl)) {
					String ip = getReallyIp(request);
					addCookie(ip, request, response, member, 60 * 60 * 24 * 30);
					SessionCacheUtil.addMember(member, ip);

					log.info("callbackUrl OK : {}", redirectUrl);
					response.sendRedirect(redirectUrl);
				} else {
					JSONObject json = new JSONObject();
					json.put("resultCode", "60001");
					json.put("msg", "NO_CALLBACK_URL");
					PrintWriter out = response.getWriter();
					out.print(json);
					out.flush();
					out.close();
				}
		} catch (Exception e) {
			log.error("GetOpenIDOauth error:", e);
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

	protected String getReallyIp(HttpServletRequest request) {
		String ip = request.getHeader("x-forwarded-for");
		if (StringUtils.isBlank(ip)
				|| StringUtils.equalsIgnoreCase("unknown", ip)) {
			ip = request.getHeader("Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getHeader("WL-Proxy-Client-IP");
		}
		if (StringUtils.isBlank(ip) || "unknown".equalsIgnoreCase(ip)) {
			ip = request.getRemoteAddr();
		}
		return ip;
	}

	public static void addCookie(String ip, HttpServletRequest request,
			HttpServletResponse response, Member member, int maxAge) {
		Map<String, String> tokens = SessionCacheUtil.getTokens(member.getId());
		if (tokens != null) {
			SessionCacheUtil.removeMember(member.getId());
		}
		String token = SessionCacheUtil.addMember(member, ip);
		Cookie cookie = new Cookie(Constants.TOKEN, token);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
	}

	private String addPathParam(String url, String key, String value) {
		if (StringUtils.isBlank(url) || StringUtils.isBlank(key)
				|| StringUtils.isEmpty(value)) {
			return url;
		}
		String str = "";
		if (!url.contains("/")) {// 编码后的URL不可能存在/
			// 编码化url处理 %3F->? %3D->=
			if (url.contains("%3F") && !url.contains("%3D")) {
				str = "";
			} else if (url.contains("%3F") && url.contains("%3D")) {
				str = "%26";
			} else {
				str = "%3F";
			}
		} else {
			// 非编码化url处理
			if (url.contains("?") && !url.contains("=")) {// http://xxx.cn?
				str = "";
			} else if (url.contains("?") && url.contains("=")) {// http://xxx.cn?a=b
				str = "&";
			} else {
				str = "?";
			}
		}
		return url += (str + key + "=" + value);
	}
}
