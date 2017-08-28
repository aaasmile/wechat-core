package com.d1m.wechat.controller.api;

import lombok.extern.slf4j.Slf4j;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.support.spring.FastJsonJsonView;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.util.SessionCacheUtil;

/**
 * Created on 16/11/25.
 */
@Slf4j
public class ApiController extends BaseController {

	@Autowired
	protected MemberService memberService;

	@ExceptionHandler(WechatException.class)
	public ModelAndView handleWechatException(WechatException e) {
		JSONObject json = new JSONObject();
		json.put("errcode", e.getMessageInfo().getCode());
		json.put("errmsg", e.getMessageInfo().getName());
		log.error(json.toJSONString(), e);
		return new ModelAndView(new FastJsonJsonView(), json);
	}

	protected Member getMember(String cookie) {
		log.info("cookie : {}", cookie);
		if (StringUtils.isNotBlank(cookie)) {
			Member member = SessionCacheUtil.getMember(cookie);
			log.info("token member : {}", (member != null ? member.getId()
					: null));
			if (member != null) {
				log.info("wechatId : {}, openId : {}, memberId : {}",
						member.getWechatId(), member.getOpenId(),
						member.getId());
				member = memberService.getMember(member.getWechatId(),
						member.getId());
				return member;
			}
		}
		throw new WechatException(Message.MEMBER_NOT_LOGIN);
	}

}
