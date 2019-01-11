package com.d1m.wechat.controller.api;

import com.d1m.wechat.model.CustomRequestBody;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ThirdPartyController {

	private Logger log = LoggerFactory.getLogger(ThirdPartyController.class);
	private static ObjectMapper om = new ObjectMapper();

	@Autowired
	private ConsulClient consulClient;

	@RequestMapping("/api/menu/callback")
	public CustomRequestBody callback() throws Exception {
		Response<GetValue> response = consulClient.getKVValue("configuration/application/message");
		String str = response.getValue().getDecodedValue();
		log.info(str);
		CustomRequestBody customRequestBody = om.readValue(str, CustomRequestBody.class);
		return customRequestBody;
	}
}
