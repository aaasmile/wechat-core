package com.d1m.wechat.util;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;

/**
 * HttpUtils
 *
 * @author f0rb on 2017-02-13.
 */
public class HttpUtils {

    public static String getIp(HttpServletRequest request) {
        String ip = request.getHeader("x-forwarded-for");
        if (StringUtils.isBlank(ip) || StringUtils.equalsIgnoreCase("unknown", ip)) {
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


    public static Cookie createCookie(HttpServletRequest request, String domain, String name, String value) {
        return createCookie(request, domain, name, value, 0);
    }

    public static Cookie createCookie(HttpServletRequest request, String domain, String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        // http proxy
        String host = request.getHeader("x-forwarded-host");
        if (host == null) {
            host = request.getServerName();
        }
        if (host.toLowerCase().contains(domain)) { // 非IP
            cookie.setDomain("." + domain);
        }
        cookie.setPath("/");
        return cookie;
    }

    public static Cookie createCookie(String name, String value, int maxAge) {
        Cookie cookie = new Cookie(name, value);
        cookie.setMaxAge(maxAge);
        cookie.setPath("/");
        return cookie;
    }

}
