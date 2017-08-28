package com.d1m.wechat.oauth.impl;

import java.io.IOException;
import java.util.Date;
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
import com.d1m.wechat.util.CommonUtils;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.SessionCacheUtil;

@Component
public class HolaRegisterIOauthImpl implements IOauth {

	private Logger log = LoggerFactory.getLogger(HolaRegisterIOauthImpl.class);

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
                        HttpServletResponse response, WxUser wuser,
                        Map<String, Object> params) {
		try {
			log.info("params : {}", params);
			Integer wechatId = (Integer) params.get("wechatId");
			String registerUrl = (String) params.get("registerUrl");
			String homeUrl = (String) params.get("homeUrl");
			String baseUrl = (String) params.get("baseUrl");
			String needBind = (String) params.get("needBind");
			if(StringUtils.isBlank(needBind)){
				needBind = "true";
			}

			String redirectUrl = (String) params.get("redirectUrl");
			if(StringUtils.isNotBlank(redirectUrl)&&!redirectUrl.startsWith("http")){
				redirectUrl = baseUrl+redirectUrl;
			}
			Member member = memberService.getMemberByOpenId(wechatId,
					wuser.getOpenid());
			if(member==null){
				member = new Member();
				member.setActivity((byte) 5);
				member.setBatchsendMonth(0);
				member.setIsSubscribe(false);
				member.setOpenId(wuser.getOpenid());
				member.setWechatId(wechatId);
				member.setCreatedAt(new Date());
				memberService.save(member);
			}
			String redirect = null;
			if("true".equalsIgnoreCase(needBind)){
				if (member != null&&StringUtils.isNotBlank(member.getMobile())) {
					// redirect home page
					redirect = getRedirectUrl(redirectUrl,homeUrl);
				} else {
					// redirect register page
					redirect = registerUrl;
				}
			}else{
				redirect = getRedirectUrl(redirectUrl,homeUrl);
			}

			String ip = getReallyIp(request);
			addCookie(ip, request, response, member, 60 * 60 * 24 * 30);
			SessionCacheUtil.addMember(member, ip);

			log.info("redirectUrl : {}", redirect);
			response.sendRedirect(redirect);
		} catch (IOException e) {
			log.error(e.getLocalizedMessage());
		}
	}

	private String getRedirectUrl(String redirectUrl, String defaultUrl) {
		if (StringUtils.isNotBlank(redirectUrl)
				&& !StringUtils.contains(redirectUrl, "#!/login")) {
			return CommonUtils.urlDecode(redirectUrl);
		}
		return defaultUrl;
	}

}
