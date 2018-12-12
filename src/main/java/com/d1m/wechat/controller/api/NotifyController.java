package com.d1m.wechat.controller.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class NotifyController {

	private Logger log = LoggerFactory.getLogger(NotifyController.class);
	
	@RequestMapping(value = "/api/testNotify", method = RequestMethod.POST)
	public String testNotify(@RequestBody String xml) {
		log.info("NotifyController...testNotify..." + xml);
		return xml;
	}
}
