package com.d1m.wechat.thirdparty;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.web.client.RestTemplate;

public class MKAccessTokenUtil implements AccessToken {
	
	private static final Logger log = LoggerFactory.getLogger(MKAccessTokenUtil.class);

	public static RestTemplate restTemplate() {
		RestTemplate restTemplate = new RestTemplate();
		restTemplate.setRequestFactory(simpleClientHttpRequestFactory());
		return restTemplate;
	}
	
	public static SimpleClientHttpRequestFactory simpleClientHttpRequestFactory() {
		SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
		factory.setConnectTimeout(5 * 1000);
		factory.setReadTimeout(5 * 1000);
		return factory;
	}
	
	@Override
	public String getAccessToken(String url) {
		String str = restTemplate().getForObject(url, String.class);
		try {
			JSONObject json = (JSONObject) JSON.parse(str);
			if(json != null) {
				return json.getString("access_token");
			}
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}
}
