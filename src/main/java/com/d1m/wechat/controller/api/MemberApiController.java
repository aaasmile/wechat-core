package com.d1m.wechat.controller.api;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.d1m.wechat.util.SessionCacheUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.d1m.wechat.model.Member;

@RestController
@RequestMapping("/api/member")
public class MemberApiController extends ApiController {

	private Logger log = LoggerFactory.getLogger(MemberApiController.class);

	@RequestMapping(value = "/get/{cookie}", method = RequestMethod.GET)
	public String getMember(@PathVariable String cookie, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		String result = null;
		try {
			Member member = SessionCacheUtil.getMember(cookie);
			if(member!=null){
				result = member.getWechatId() + "#" + member.getId();
			}
		} catch (Exception e) {
			log.error(e.getMessage());
		}
		return result;
	}
}
