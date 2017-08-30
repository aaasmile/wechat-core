package com.d1m.wechat.wechatclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.d1m.wechat.configure.FeignClientConfiguration;

@FeignClient(name = "wechat-crm", configuration = FeignClientConfiguration.class)
public interface WechatCrmRestService {

	@RequestMapping(value = "/api/member/{wechatId}/{memberId}/levels", method = RequestMethod.GET)
	String getMemberLevels(@PathVariable("Integer wechatId") Integer wechatId,
			@PathVariable("Integer memberId") Integer memberId);

}
