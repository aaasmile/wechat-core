package com.d1m.wechat.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.d1m.wechat.service.ConfigService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.exception.WechatException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class BaiduLocationUtil {
	
	private static Logger log = LoggerFactory.getLogger(BaiduLocationUtil.class);

	@Autowired
	private ConfigService configService;

	public Map<String, Double> getLatAndLngByAddress(Integer wechatId, String addr) {
		String address = "";
		String lat = "";
		String lng = "";
		try {
			address = URLEncoder.encode(addr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
			throw new WechatException(Message.MAP_URL_ENCODING_FAIL);
		}

		String apiKey = configService.getConfigValue(wechatId, "LBS",
				"BAIDU_GEO_SERVICE_KEY");
		String url = String
				.format("http://api.map.baidu.com/geocoder/v2/?"
								+ "ak=%s&output=json&address=%s",
						apiKey, address);
		
		String result = HttpRequestProxy.doGet(url, new HashMap(), "UTF-8");
		JSONObject json = JSONObject.parseObject(result);
		Integer status = json.getInteger("status");
		Map<String, Double> map = new HashMap<String, Double>();
		if(status == 0){
			JSONObject resultJson = json.getJSONObject("result");
			JSONObject locationJson = resultJson.getJSONObject("location");
			lat = locationJson.getString("lat");
			lng = locationJson.getString("lng");
			
			map.put("lat", new Double(lat));
			map.put("lng", new Double(lng));
		}else{
			map = null;
		}
		
		return map;
	}
	
	public Map<String, String> getAddressByLatAndLng(Integer wechatId,String lat, String lng){
		String location = "";
		String apiKey = configService.getConfigValue(wechatId, "LBS",
				"BAIDU_GEO_SERVICE_KEY");
		String url = String
				.format("http://api.map.baidu.com/geocoder/v2/?"
								+ "ak=%s&output=json&location=%s,%s",
						apiKey, lat, lng);
		String result = HttpRequestProxy.doGet(url, new HashMap(), "UTF-8");
		JSONObject json = JSONObject.parseObject(result);
		Integer status = json.getInteger("status");
		Map<String, String> map = new HashMap<String, String>();
		if(status == 0){
			JSONObject resultJson = json.getJSONObject("result");
			JSONObject addressJson = resultJson.getJSONObject("addressComponent");
			String country = addressJson.getString("country");
			String province = addressJson.getString("province");
			String city = addressJson.getString("city");
			String district = addressJson.getString("district");
			
			map.put("country", country);
			map.put("province", province);
			map.put("city", city);
			map.put("district", district);
		}else{
			map = null;
		}
		
		return map;
	}

	public JSONObject transforLatAndLng(Integer wechatId, String coordinate){

		String apiKey = configService.getConfigValue(wechatId, "LBS",
				"QQ_GEO_SERVICE_KEY");

		String strUrl = String
				.format("http://apis.map.qq.com/ws/coord/v1/translate?locations=%s&type=3&key=%s",
						coordinate, apiKey);
		URLConnection conn = null;
		try {
			URL url = new URL(strUrl);
			conn = url.openConnection();
			StringBuilder result = new StringBuilder();
			BufferedReader rd = new BufferedReader(new InputStreamReader(conn.getInputStream()));
			String line;
			while ((line = rd.readLine()) != null) {
				result.append(line);
			}
			rd.close();
			JSONObject json = JSONObject.parseObject( result.toString());
			log.info(strUrl);
			log.info(JSONObject.toJSONString(json));
			return json;
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
}
