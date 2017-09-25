package com.d1m.wechat.controller.wx;

import java.io.IOException;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.d1m.wechat.client.model.WxUser;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.model.OauthUrlLog;
import com.d1m.wechat.service.OauthUrlLogService;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.d1m.wechat.component.FileUploadConfig;
import com.d1m.wechat.model.OauthUrl;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.oauth.IOauth;
import com.d1m.wechat.service.OauthUrlService;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.AppContextUtils;
import com.d1m.wechat.util.Base64Util;
import com.d1m.wechat.util.HttpRequestProxy;

/**
 * 微信网页授权跳转和回调地址
 * 
 * @author d1m
 */
@Controller
@RequestMapping("/oauth")
public class WxOauthController {
	private static Logger log = LoggerFactory
			.getLogger(WxOauthController.class);
	private static String authUrl = "https://open.weixin.qq.com/connect/oauth2/authorize?appid=%s&redirect_uri=%s&response_type=code&scope=%s&state=%s#wechat_redirect";
	private static String tokenUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=%s&secret=%s&code=%s&grant_type=authorization_code";
	private static String userinfoUrl = "https://api.weixin.qq.com/sns/userinfo?access_token=%s&openid=%s&lang=zh_CN";
	@Autowired
	private OauthUrlService oauthUrlService;
	@Autowired
	private WechatService wechatService;
	@Autowired
	private OauthUrlLogService oauthUrlLogService;

	/**
	 * 短地址转发地址
	 * 
	 * @param shortUrl
	 * @param request
	 * @param response
	 */
	@RequestMapping(value = "u/{shortUrl}", method = RequestMethod.GET)
	public void shortUrl(@PathVariable String shortUrl,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			log.info("shortUrl : {}", shortUrl);
			String userAgent = request.getHeader("user-agent");
			log.info("user-agent : {}", userAgent);
			OauthUrl urlObj = oauthUrlService.getByShortUrl(shortUrl);
			if (urlObj != null) {
				String redirectUrl = request.getParameter("redirectUrl");
				String token = request.getParameter("token");
				String source = request.getParameter("source");
				log.info("token : {}", token);
				log.info("redirectUrl : {}", redirectUrl);
				log.info("source : {}", source);
				JSONObject obj = new JSONObject();
				if(StringUtils.isNotBlank(token)){
					obj.put("token",token);
				}
				if(StringUtils.isNotBlank(source)){
					obj.put("source",source);
				}
				if(StringUtils.isNotBlank(redirectUrl)){
					obj.put("redirectUrl",redirectUrl);
				}
				String bs = Base64Util.getBase64(obj.toJSONString());

				// 获取微信配置
				Wechat wechat = wechatService.getById(urlObj.getWechatId());
				String oauthRedirectUrl = FileUploadConfig.getValue(wechat.getId(), "oauth_redirect_url")+"?bs="+URLEncoder.encode(bs, "UTF-8");
				String encodeOauthRedirectUrl= URLEncoder.encode(oauthRedirectUrl,"UTF-8");
				log.info("oauthRedirectUrl : {},{} ,bs:{}",oauthRedirectUrl,encodeOauthRedirectUrl,bs);
				String url = String.format(authUrl, wechat.getAppid(),
						encodeOauthRedirectUrl, urlObj.getScope(), urlObj.getId());
				log.info("authUrl : {}", url);
				// 重定向
				response.sendRedirect(url);
			} else {
				JSONObject json = new JSONObject();
				json.put("resultCode", "-1");
				json.put("msg", "Url Unavailable.");
				PrintWriter out = response.getWriter();
				out.print(json);
				out.flush();
				out.close();
			}
		} catch (IOException e) {
			log.error(e.getMessage());
		}
	}

	@SuppressWarnings("unchecked")
	@RequestMapping(value = "callback", method = RequestMethod.GET)
	public void callback(HttpServletRequest request,
			HttpServletResponse response) {
		try {
			// 获取CODE,STATE参数
			String code = request.getParameter("code");
			String state = request.getParameter("state");
			String bs = request.getParameter("bs");
			log.info("callback, {} , {}, {}, {} ,{}", code, state, bs);
			if (code != null) {
				OauthUrl urlObj = oauthUrlService.selectByKey(Integer
						.parseInt(state));
				// 获取微信配置
				Wechat wechat = wechatService.getById(urlObj.getWechatId());
				String url = String.format(tokenUrl, wechat.getAppid(),
						wechat.getAppscret(), code);

				// 增加缓存机制，如果是重复callback，则返回上次请求的数据
				String resp = doGet(url);
				log.info("response-1, {}", resp);
				JSONObject obj = JSON.parseObject(resp);
				String errcode = obj.getString("errcode");
				if (errcode != null) {
					JSONObject json = new JSONObject();
					json.put("resultCode", errcode);
					json.put("msg", obj.getString("errmsg"));
					PrintWriter out = response.getWriter();
					out.print(json);
					out.flush();
					out.close();
				} else {
					WxUser wuser = new WxUser();
					wuser.setOpenid(obj.getString("openid"));
					wuser.setUnionid(obj.getString("unionid"));

					String accessToken = obj.getString("access_token");
					// String refreshToken = obj.getString("refresh_token");

					// 非静默授权可以获取用户信息
					if ("snsapi_userinfo".equalsIgnoreCase(urlObj.getScope())) {
						url = String.format(userinfoUrl, accessToken,
								wuser.getOpenid());

						resp = HttpRequestProxy.doGet(url, "UTF-8");
						log.info("response-2, {}", resp);
						obj = JSON.parseObject(resp);
						wuser.setOpenid(obj.getString("openid"));
						wuser.setUnionid(obj.getString("unionid"));
						wuser.setNickname(obj.getString("nickname"));
						wuser.setSex(obj.getString("sex"));
						wuser.setProvince(obj.getString("province"));
						wuser.setCity(obj.getString("city"));
						wuser.setCountry(obj.getString("country"));
						wuser.setHeadimgurl(obj.getString("headimgurl"));
					}

					Map<String, Object> params = new HashMap<String, Object>();
					String strParams = urlObj.getParams();
					if (StringUtils.isNotBlank(strParams)) {
						params = (Map<String, Object>) JSON.parse(strParams);
					}
					params.put("short_url", urlObj.getShortUrl());
					params.put("wechatId", urlObj.getWechatId());
					JSONObject jo = null;
					if(StringUtils.isNotBlank(bs)){
						bs = Base64Util.getFromBase64(bs);
						log.info("response-bs, {}", bs);
						jo = JSON.parseObject(bs);
						params.put("redirectUrl", jo.getString("redirectUrl"));
						params.put("token", jo.getString("token"));
						params.put("source", jo.getString("source"));
					}
					Class<?> clazz = Class.forName(urlObj.getProcessClass());
					// 统一使用spring容器管理，方便注入
					Object object = AppContextUtils.getBean(clazz);
					if (object == null) {
						object = clazz.newInstance();
					}
					if (object instanceof IOauth) {
						// 异步批量插入访问日志
						try {
							OauthUrlLog oauthUrlLog = new OauthUrlLog();
							oauthUrlLog.setCreatedAt(new Date());
							oauthUrlLog.setOauthUrlId(urlObj.getId());
							oauthUrlLog.setOpenId(wuser.getOpenid());
							oauthUrlLog.setRedirectUrl(jo.getString("redirectUrl"));
							oauthUrlLog.setSource(jo.getString("source"));
							oauthUrlLog.setWechatId(wechat.getId());
							oauthUrlLogService.batchInsertLog(oauthUrlLog);
						} catch (Exception e){
							log.error("batch insert oauth log error!",e);
						}
						// 执行oauth实现类
						((IOauth) object).execute(request, response, wuser, params);
					} else {
						log.error("Bean must implements IOauth!");
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
	}

	/**
	 * 可缓存的URL请求方法
	 * @param url
	 * @return
	 */
	@Cacheable(value = "storeCache", key = "#url")
	public String doGet(String url){
		return HttpRequestProxy.doGet(url, "UTF-8");
	}
}
