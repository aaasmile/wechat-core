package com.d1m.wechat.util;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;

import org.apache.commons.lang.StringUtils;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.EntityBuilder;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.entity.ContentType;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;


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
	public static void main(String[] args) throws Exception {
		String message = "{\"touser\":\"o9agruGB39hYlPcoVK-HhoSYQ0sI\",\"template_id\":\"WK6Hna9pxzMWv20FAWo4Z_Zi7eDnvNX6kpca_DMOHCQ\",\"url\":\"https://michaelkors-wechat.cn/Home/Home/coupon/index/openid/oOkShjgR4OlPBiwDhJdAEyS7oa_k.html\",\"data\":{\"first\":{\"value\":\"亲爱的杨林芳，恭喜您成功升级为KORS普通会员。感谢>您的选择，我们将全力为您打造时尚、优雅的JET SET体验。\\n您的会员升级信息如下：\",\"color\":\"#000000\"},\"keyword1\":{\"value\":\"CN11552869\",\"color\":\"#baa36f\"},\"keyword2\":{\"value\":\"2018年10月16日\\n敬请点击下方“详情”，查看您升级后的会员尊享礼遇。\",\"color\":\"#baa36f\"},\"remark\":{\"value\":\"如您有任何疑问，欢迎点击下方对话框咨询在线时尚顾问，我们将在每天>早9点至晚10点竭诚为您提供服务，助您随时随地闪耀登场。\",\"color\":\"#000000\"}}}";
		message = message.replaceAll("\"", "\\\\\"");
		System.out.println(message);
//		ContentType TEXT_PLAIN = ContentType.create("text/plain", Consts.UTF_8);
		CloseableHttpClient httpclient = HttpClients.createDefault();
		HttpPost httpPost = new HttpPost("http://mk.wechat.d1m.cn/social_wechat_api/message/template/send?appid=3E3ACFCA6131467D&appsecret=D74D118C7050A98EB75FDEB53809FE35CB6FA58234D834DD");
		HttpEntity entity = EntityBuilder.create().setText(message)
				.setContentEncoding("UTF-8").setContentType(ContentType.APPLICATION_JSON).build();
		httpPost.setEntity(entity);
		CloseableHttpResponse response2 = httpclient.execute(httpPost);
		try {
		    System.out.println(response2.getStatusLine());
		    HttpEntity entity2 = response2.getEntity();
		    EntityUtils.consume(entity2);
		} finally {
		    response2.close();
		}
	}
}
