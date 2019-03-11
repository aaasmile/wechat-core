package com.d1m.wechat.oauth.impl;

import com.d1m.wechat.wechatclient.WechatCrmRestK8sService;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Resource;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.d1m.wechat.client.model.WxUser;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.OauthUrl;
import com.d1m.wechat.oauth.IOauth;
import com.d1m.wechat.service.ConfigService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.OauthUrlService;
import com.d1m.wechat.util.Base64Util;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.SessionCacheUtil;
import com.d1m.wechat.util.WXPayUtil;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.wechatclient.WechatCrmRestService;

@Component
public class GetLacosteOpenIDOauthImpl implements IOauth {
	private Logger log = LoggerFactory
			.getLogger(GetLacosteOpenIDOauthImpl.class);

	private static final String SYSTEM_ERROR = "-1";

	private static final String NOT_BIND = "0";

	private static final String BIND = "1";

	private static final String MERGE_CARD = "2";

	private static final String NEED_BIND = "1";

	private static final String TO_MEMBER_CENTER_IF_BIND = "1";

	@Resource
	private MemberService memberService;

	@Resource
	private ConfigService configService;

	@Autowired
	private WechatCrmRestService wechatCrmRestService;

	@Autowired
	private WechatCrmRestK8sService wechatCrmRestK8sService;
	
	@Autowired
	private OauthUrlService oauthUrlService;

	@Autowired
	private WechatCrmRestK8sService wechatCrmRestK8sService;

	private String getStatus(Integer wechatId, Integer memberId) {
		try {
			log.info("getStatus...");
			return wechatCrmRestService.getMemberStatus(wechatId, memberId);
		} catch (Exception e) {
			log.info("getStatus k8s...");
			return wechatCrmRestK8sService.getMemberStatus(wechatId, memberId);
		}
	}
	private String getLevels(Integer wechatId, Integer memberId) {
		try {
			log.info("getLevels...");
			return wechatCrmRestService.getMemberLevels(wechatId, memberId);
		} catch (Exception e) {
			log.info("getLevels k8s...");
			return wechatCrmRestK8sService.getMemberLevels(wechatId, memberId);
		}
	}
	
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
			String campaign = sourceJson.getString("campaign");

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
			} else {
				log.info("member unionid : {}.", member.getUnionId());
				if (StringUtils.isBlank(member.getUnionId())) {
					WxUser user = WechatClientDelegate.getUser(wechatId,
							member.getOpenId());
					log.info("user unionid : {}.", user.getUnionid());
					member.setUnionId(user.getUnionid());
					memberService.updateNotNull(member);
					log.info("update unionid.");
				}
			}

			log.info("callbackUrl : {}", redirectUrl);
			if (StringUtils.isNotBlank(redirectUrl)) {
				log.info("判断redirectUrl 是否为空");
				String ip = getReallyIp(request);
				addCookie(ip, request, response, member, 60 * 60 * 24 * 30);
				SessionCacheUtil.addMember(member, ip);
				
				if (StringUtils.equals(needBind, NEED_BIND)) {
					log.info("判断needBind 参数是否为1");
					String status = null;
					try {
						status = getStatus(wechatId, member.getId());
						if (StringUtils.isBlank(status)) {
							log.error("status get null.");
							status = SYSTEM_ERROR;
						}
					} catch (Exception e) {
						log.error("error : {}.", e.getMessage());
						status = SYSTEM_ERROR;
					}
					log.info("status : {}.", status);
					log.info("判断用户member表status字段" + status);
					if (StringUtils.equals(status, SYSTEM_ERROR)) {
						log.info("如果返回-1则跳转到系统繁忙页");
						log.info("get status runsa api error.");
						redirectUrl = configService.getConfigValue(wechatId,
								"LACOSTE_CRM", "SERVER_BUSY_URL");
						log.info("get status server busy callbackUrl OK : {}",
								redirectUrl);
					} else if (StringUtils.equals(status, NOT_BIND)) {
						log.info("如果返回0，则跳转到用户注册页，");
						redirectUrl = configService.getConfigValue(wechatId,
								"LACOSTE_CRM", "LACOSTE_MEMBER_REGISTER_URL");
						if (StringUtils.isNotBlank(campaign)
								&& !StringUtils.contains(redirectUrl, campaign)) {
							log.info("如果返回0,如果campaign不为空，则带上campaign参数");
							redirectUrl += ("?campaign=" + campaign);
						}
					} else if (StringUtils.equals(status, MERGE_CARD)) {
						log.info("如果返回2，则跳转到用户注册页，带上参数mergeCardTips=1，如果campaign不为空，则带上campaign参数");
						redirectUrl = configService.getConfigValue(wechatId,
								"LACOSTE_CRM", "LACOSTE_MEMBER_REGISTER_URL");
						redirectUrl += "?mergeCardTips=1";
						if (StringUtils.isNotBlank(campaign)
								&& !StringUtils.contains(redirectUrl, campaign)) {
							log.info("如果返回2,如果campaign不为空，则带上campaign参数");
							redirectUrl += ("&campaign=" + campaign);
						}
					} else if (StringUtils.equals(status, BIND)) {
						log.info("如果返回1");
						if (StringUtils.equals(toMemberCenterIfBind,
								TO_MEMBER_CENTER_IF_BIND)) {
							log.info("如果返回1，且toMemberCenterIfBind为1");
							String val = configService.getConfigValue(wechatId,
									"LACOSTE_CRM", "LACOSTE_MEMBER_CENTER_URL");
							JSONObject json = JSONObject.parseObject(val);
							String levels = getLevels(wechatId, member.getId());
							log.info(
									"wechatId : {}, memberId : {}, levels : {}.",
									wechatId, member.getId(), levels);
							if (StringUtils.isNotBlank(levels)) {
								log.info("如果返回1，且toMemberCenterIfBind为1,且用户等级不为空" + levels);
								if (StringUtils.equals(levels, SYSTEM_ERROR)) {
									log.info("如果返回1，且toMemberCenterIfBind为1,且获取用户等级时，系统异常");
									log.info("runsa api error.");
									redirectUrl = configService.getConfigValue(
											wechatId, "LACOSTE_CRM",
											"SERVER_BUSY_URL");
									log.info("server busy callbackUrl OK : {}",
											redirectUrl);
								} else {
									log.info("如果返回1，且toMemberCenterIfBind为1,且则根据会员等级覆盖redirectUrl替换为为对应等级的会员中心页面");
									String url = getRedirectUrl(levels, json);
									log.info("url : {}.", url);
									if (StringUtils.isNotBlank(url)) {
										redirectUrl = url;
									}
								}
							}
						}
						if (StringUtils.isNotBlank(campaign)
								&& !StringUtils.contains(redirectUrl, campaign)) {
							log.info("如果返回1，campaign不为空，则跳转到redirectUrl并带上campaign参数");
							redirectUrl += ("?campaign=" + campaign);
							
							Map<String, String> respMap = new HashMap<String, String>();
				            try {
				            	WxUser wxuser = WechatClientDelegate.getUser(member.getWechatId(), wuser.getOpenid());
				            	JSONObject memberJson = new JSONObject();
				            	 memberJson.put("open_id", wuser.getOpenid());
				                 memberJson.put("nickname", wxuser.getNickname());
				                 memberJson.put("union_id", wxuser.getUnionid());
				                 memberJson.put("head_img_url", wxuser.getHeadimgurl());
				                 memberJson.put("country", wxuser.getCountry());
				                 memberJson.put("subscribe", wxuser.getSubscribe());
				                 memberJson.put("phone", member.getMobile());
				                 memberJson.put("pmcode", member.getPmcode());
				                 memberJson.put("names", member.getName());
				                 log.info("bind memberJson>>" + memberJson.toString());
				                 String data = Base64Util.getBase64(memberJson.toJSONString());
				                 String sign = WXPayUtil.MD5(data + "D1M");
				                 
				                 respMap.put("data", data);
				                 respMap.put("sign", sign);
				                 log.info("respJson>>" + respMap.toString());
				                 String symbol = "?";
				                 if(campaign.indexOf("?") >=0 ) {
				                	 symbol = "&";
				                 }
				                 if(campaign.indexOf("http") >= 0) {
				                	log.info("如果返回1，campaign不为空，如果campaign参数带http参数，则直接跳转到campaign页");
				                    response.sendRedirect(campaign + symbol + "data=" + data + "&sign=" + sign);
				                    return;
				                 }
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							} 
				           
						}
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