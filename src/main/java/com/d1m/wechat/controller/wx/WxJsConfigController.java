package com.d1m.wechat.controller.wx;

import java.util.Map;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.d1m.wechat.wechatclient.WechatClientDelegate;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

import com.d1m.wechat.model.OauthUrl;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.pamametermodel.JsConfigModel;
import com.d1m.wechat.service.OauthUrlService;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.WeiXinUtils;

@Controller
@RequestMapping("/jsconfig")
@Api("微信JS配置")
public class WxJsConfigController {

	@Resource
	private OauthUrlService oauthUrlService;

	@Resource
	private WechatService wechatService;

	private static Logger log = LoggerFactory
			.getLogger(WxJsConfigController.class);
	
	@ApiOperation(value="从微信端获取JS配置")
	@ApiResponse(code=200, message="JS配置")
	@RequestMapping(method = RequestMethod.POST)
	@ResponseBody
	public JSONObject getJsConfig(
			@ApiParam("JsConfigModel")
				@RequestBody(required = false) JsConfigModel jsConfigModel, String callback) {
		try {
			String shortUrl = jsConfigModel.getShortUrl();
			String url = jsConfigModel.getUrl();
			log.info("shortUrl : {}, url : {}, callback : {}", shortUrl, url, callback);
			if (StringUtils.isNotBlank(shortUrl) && StringUtils.isNotBlank(url)) {
				OauthUrl urlObj = oauthUrlService.getByShortUrl(shortUrl);
				log.info("urlObj : {}", urlObj);
				if (urlObj != null) {
					if(url.contains("#")){
						url = StringUtils.substring(url, 0,
								StringUtils.indexOf(url, "#"));
					}
					Map<String, Object> jsSignMap = WeiXinUtils.getJsSignMap(
                            WechatClientDelegate.getJsApiTicket(urlObj.getWechatId()), url);
					Wechat wechat = wechatService.getById(urlObj.getWechatId());
					jsSignMap.put("appId", wechat.getAppid());
					return new JSONObject(jsSignMap);
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return null;
	}
	
	@ApiOperation(value="获取JS配置")
	@ApiResponse(code=200, message="JS配置")
	@RequestMapping(method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getJsConfig(String shortUrl, String url, String callback, HttpServletResponse response) {
		try {
			log.info("shortUrl : {}, url : {}, callback : {}", shortUrl, url, callback);
			if (StringUtils.isNotBlank(shortUrl) && StringUtils.isNotBlank(url)) {
				OauthUrl urlObj = oauthUrlService.getByShortUrl(shortUrl);
				log.info("urlObj : {}", urlObj);
				if (urlObj != null) {
					if(url.contains("#")){
						url = StringUtils.substring(url, 0,
								StringUtils.indexOf(url, "#"));
					}
					Map<String, Object> jsSignMap = WeiXinUtils.getJsSignMap(WechatClientDelegate.getJsApiTicket(urlObj.getWechatId()), url);
					Wechat wechat = wechatService.getById(urlObj.getWechatId());
					jsSignMap.put("appId", wechat.getAppid());
					if(StringUtils.isBlank(callback)){
						return new JSONObject(jsSignMap);
					}else{
						response.setContentType("text/html;charset=UTF-8");
						response.setCharacterEncoding("UTF-8");
						response.getWriter().print(callback+"("+ JSON.toJSON(jsSignMap)+");");
						return null;
					}
				}
			}
		} catch (Exception e) {
			log.error(e.getMessage(),e);
		}
		return null;
	}
}
