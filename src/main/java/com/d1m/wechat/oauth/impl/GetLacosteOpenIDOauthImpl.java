package com.d1m.wechat.oauth.impl;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

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
import com.d1m.wechat.model.enums.MemberStatus;
import com.d1m.wechat.oauth.IOauth;
import com.d1m.wechat.service.ConfigService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.SessionCacheUtil;

@Component
public class GetLacosteOpenIDOauthImpl implements IOauth {
	private Logger log = LoggerFactory
			.getLogger(GetLacosteOpenIDOauthImpl.class);

	@Resource
	private MemberService memberService;

	@Resource
	private ConfigService configService;

	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, WxUser wuser,
			Map<String, Object> params) {
		try {
			log.info("params : {}", params);
			Integer wechatId = (Integer) params.get("wechatId");
			String redirectUrl = (String) params.get("redirectUrl");
			String source = (String) params.get("source");
			JSONObject sourceJson = new JSONObject();
			if (StringUtils.isNotBlank(source)) {
				String[] arr = StringUtils.split(source, "\\,");
				for (String str : arr) {
					String[] subArr = StringUtils.split(str, "\\_");
					sourceJson.put(subArr[0], subArr[1]);
				}
			}

			String needBind = sourceJson.getString("needBind");
			String toMemberCenterIfBind = sourceJson
					.getString("toMemberCenterIfBind");

			Member member = memberService.getMemberByOpenId(wechatId,
					wuser.getOpenid());
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

				if (StringUtils.equals(needBind, "1")) {
					if (member.getStatus() != null
							&& member.getStatus().equals(
									MemberStatus.BIND.getValue())) {
						if (StringUtils.equals(toMemberCenterIfBind, "1")) {
							String val = configService.getConfigValue(wechatId,
									"LACOSTE_CRM", "LACOSTE_MEMBER_CENTER_URL");
							JSONObject json = JSONObject.parseObject(val);
							String url = getRedirectUrl(member.getLevels(),
									json);
							log.info("url : {}.", url);
							if (StringUtils.isNotBlank(url)) {
								redirectUrl = url;
							}
						}
					} else {
						redirectUrl = configService.getConfigValue(wechatId,
								"LACOSTE_CRM", "LACOSTE_MEMBER_REGISTER_URL");
					}
				}

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
			log.error("GetLacosteOpenIDOauth error:", e);
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

	private String getRedirectUrl(String levels, JSONObject json) {
		for (Entry<String, Object> entry : json.entrySet()) {
			if (StringUtils.indexOf(levels, entry.getKey()) != -1) {
				return (String) entry.getValue();
			}
		}
		return null;
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

}
