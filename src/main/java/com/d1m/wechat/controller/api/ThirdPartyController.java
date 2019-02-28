package com.d1m.wechat.controller.api;

import com.d1m.wechat.model.CustomRequestBody;
import com.d1m.wechat.util.Security;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Map;

@RestController
public class ThirdPartyController {

	private Logger log = LoggerFactory.getLogger(ThirdPartyController.class);
	private static ObjectMapper om = new ObjectMapper();

	@Autowired
	private ConsulClient consulClient;

	@RequestMapping(value = "/api/menu/callback")
	public List<CustomRequestBody> callback(@RequestBody String encryptedData) throws Exception {
		log.info("encryptedData..." + encryptedData);
		Response<GetValue> response = consulClient.getKVValue("configuration/application/secret");
		String secret = response.getValue().getDecodedValue();
		log.info("secret..." + secret);
		String data = Security.decrypt(encryptedData, secret);
		log.info("data..." + data);
		Gson gson = new Gson();
		Map<String, Object> wechatMessage = gson.fromJson(data, Map.class);
		String fromUserName = (String) wechatMessage.get("FromUserName");

		Response<GetValue> messageR = consulClient.getKVValue("configuration/application/message");
		String message = messageR.getValue().getDecodedValue();
		log.info(message);
		List<CustomRequestBody> callbackList = om.readValue(message, List.class);
		for(CustomRequestBody customRequestBody: callbackList) {
			customRequestBody.setTouser(fromUserName);
		}
		return callbackList;
	}

	public static void main(String[] args) {
		String encryptedData = "oWjsd+bk5/xTr+tmA4xMtg==";
		String secret = "aes_key_d1m_2018";
		String data = Security.decrypt(encryptedData, secret);
		System.out.println(data);
	}
}
