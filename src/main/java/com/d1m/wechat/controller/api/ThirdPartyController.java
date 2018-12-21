package com.d1m.wechat.controller.api;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThirdPartyController {

	@RequestMapping("/api/menu/callback")
	public String callback() {
		
		return System.getProperty("wechat-core.text");
	}
}
