package com.d1m.wechat.oauth.impl;

import java.io.IOException;
import java.util.Map;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.d1m.wechat.client.model.WxUser;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.oauth.IOauth;
import com.d1m.wechat.service.MemberProfileService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.FileUploadConfigUtil;
import com.d1m.wechat.util.SessionCacheUtil;

@Component
public class HolaOfflineActivityIOauthImpl implements IOauth{
	
	private Logger log = LoggerFactory.getLogger(HolaOfflineActivityIOauthImpl.class);
	
	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberProfileService memberProfileService;

	public static void addCookie(String ip, HttpServletRequest request,
			HttpServletResponse response, Member member, int maxAge) {
		Map<String, String> tokens = SessionCacheUtil.getTokens(member.getId());
		if (tokens != null) {
			SessionCacheUtil.removeMember(member.getId());
		}
		String token = SessionCacheUtil.addMember(member, ip);
		Cookie cookie = new Cookie(Constants.HOLATOKEN, token);
		cookie.setMaxAge(maxAge);
		cookie.setPath("/");
		response.addCookie(cookie);
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

	@Override
	public void execute(HttpServletRequest request,
			HttpServletResponse response, WxUser wxUser,
			Map<String, Object> params) {
		try {
			log.info("params : {}", params);
			Integer wechatId = (Integer) params.get("wechatId");
			Member member = memberService.getMemberByOpenId(wechatId, wxUser.getOpenid());
			
			String ip = getReallyIp(request);
			addCookie(ip, request, response, member, 60 * 60 * 24 * 30);
			SessionCacheUtil.addMember(member, ip);

			FileUploadConfigUtil config = FileUploadConfigUtil.getInstance();
			String redirect = config.getValue(null, "offline_activity_front_url");
			log.info("redirectUrl : {}", redirect);
			response.sendRedirect(redirect);
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		}
		
	}

}
