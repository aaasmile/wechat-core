package com.d1m.wechat.wechatclient;

import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import com.d1m.wechat.configure.FeignClientConfiguration;
import com.d1m.wechat.model.CustomRequestBody;

@FeignClient(
        name = "social-wechat-api",
        configuration = FeignClientConfiguration.class
)
public interface CustomService {
	@RequestMapping(value = "/custom/sender/{wechatId}", method = RequestMethod.POST)
	public String sender(@RequestBody CustomRequestBody customRequestBody, @PathVariable Integer wechatId);
}
