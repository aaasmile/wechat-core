package com.d1m.wechat.controller.api;

import com.d1m.wechat.model.CustomRequestBody;
import com.d1m.wechat.util.Security;
import com.ecwid.consul.v1.ConsulClient;
import com.ecwid.consul.v1.Response;
import com.ecwid.consul.v1.kv.model.GetValue;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.json.JSONArray;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.ArrayList;
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
//		JsonArray array = new JsonArray();
		JSONArray array = new JSONArray(message);
		List<CustomRequestBody> callbackList = new ArrayList<>();
		for(int i = 0; i < array.length(); i++) {
			System.out.println(array.getString(0));
			CustomRequestBody customRequestBody = om.readValue(array.getString(0), CustomRequestBody.class);
			customRequestBody.setTouser(fromUserName);
			callbackList.add(customRequestBody);
		}
		return callbackList;
	}

	public static void main(String[] args) throws Exception {
//		String encryptedData = "oWjsd+bk5/xTr+tmA4xMtg==";
//		String secret = "aes_key_d1m_2018";
//		String data = Security.decrypt(encryptedData, secret);
//		System.out.println(data);

		String message = "[{\n" +
				"    \"touser\":\"oNgDLwf6k5rzWuuR3jn15clrgsr4\",\n" +
				"    \"msgtype\":\"text\",\n" +
				"    \"text\":\n" +
				"    {\n" +
				"         \"content\":\"Hello World\"\n" +
				"    }\n" +
				"},{\n" +
				"    \"touser\":\"oNgDLwf6k5rzWuuR3jn15clrgsr4\",\n" +
				"    \"msgtype\":\"news\",\n" +
				"    \"news\":{\n" +
				"        \"articles\": [\n" +
				"         {\n" +
				"             \"title\":\"Bad Day\",\n" +
				"             \"description\":\"Is Really A Happy Day\",\n" +
				"             \"url\":\"https://baijiahao.baidu.com/s?id=1626675424750475217&wfr=spider&for=pc\",\n" +
				"             \"picurl\":\"http://dev.wechat.d1m.cn/attached/dev/image/material/201902/27174130_27dc4aff4ad743f1_02b6fb705e67b4fdaf2fdcb4c5d202f2.jpg\"\n" +
				"         }\n" +
				"         ]\n" +
				"    }\n" +
				"}]";
		System.out.println(message);
//		JsonArray array = new JsonArray();
		JSONArray array = new JSONArray(message);
		List<CustomRequestBody> callbackList = new ArrayList<>();
		for(int i = 0; i < array.length(); i++) {
			System.out.println(array.getJSONObject(0).toString());
			CustomRequestBody customRequestBody = om.readValue(array.getJSONObject(0).toString(), CustomRequestBody.class);
			customRequestBody.setTouser("nihao");
			callbackList.add(customRequestBody);
			System.out.println(customRequestBody.toString());
		}
		System.out.println(callbackList.toArray());
	}
}
