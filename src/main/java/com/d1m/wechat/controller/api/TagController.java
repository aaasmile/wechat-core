package com.d1m.wechat.controller.api;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.d1m.wechat.model.Response;
import com.d1m.wechat.model.Tag;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.UserInfo;
import com.d1m.wechat.service.MemberMemberTagService;
import com.d1m.wechat.service.UserService;

@RestController
public class TagController {

	private static final Logger log = LoggerFactory.getLogger(TagController.class);
	@Autowired
	private MemberMemberTagService memberMemberTagService;
	@Autowired
	private UserService userService;
	
	/**
	 * 
	   {
			"username": "3E3ACFCA6131467D",
			"password": "D74D118C7050A98EB75FDEB53809FE35CB6FA58234D834DD",
			"wechatId": ""
			"tags": [{"openid":"","memberTagName":"","wechatId":""},
			{"openid":"","memberTagName":"", "wechatId":""}]
		}

	 * @return Response
	 */
	@RequestMapping("/api/tag/memberTag/createOrUpdate")
	@ResponseBody
	public Response memberTagCreate(@RequestBody UserInfo userInfo) {
		try {
			log.debug("userInfo>>" + userInfo.toString());
			User user = userService.login(userInfo.getUsername(), userInfo.getPassword());
			if(user == null || user.getId() == null) {
				return Response.fail("","account or password does not exist!");
			}
			
			List<Tag> tags = userInfo.getTags();
			if(tags == null || tags.isEmpty()) {
				return Response.fail("", "tags is null!");
			} 
			else if(tags.size() > 100) {
				return Response.fail("", "tags size is more than 1000!");
			}

			List<Tag> insert = new ArrayList<Tag>();
			for(Tag tag: tags) {
				tag.setWechatId(Integer.valueOf(userInfo.getWechatId()));
				insert.add(tag);
			}
		
			memberMemberTagService.insertOrUpdateList(insert);
	
			return Response.successful("", "successful!");
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return Response.fail(userInfo.getTags(), e.getMessage());
		}
	}
}
