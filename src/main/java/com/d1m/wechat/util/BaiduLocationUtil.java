package com.d1m.wechat.util;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.exception.WechatException;

public class BaiduLocationUtil {
	
	private static Logger log = LoggerFactory.getLogger(BaiduLocationUtil.class);

	public static Map<String, Double> getLatAndLngByAddress(String addr) {
		String address = "";
		String lat = "";
		String lng = "";
		try {
			address = URLEncoder.encode(addr, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
			throw new WechatException(Message.MAP_URL_ENCODING_FAIL);
		}
		String url = String
				.format("http://api.map.baidu.com/geocoder/v2/?"
					+ "ak=DEce9781adb6bb42507fff0ba4228ad6&output=json&address=%s",
					address);
		
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
	
	public static Map<String, String> getAddressByLatAndLng(String lat, String lng){
		String location = "";
		String url = String
				.format("http://api.map.baidu.com/geocoder/v2/?"
					+ "ak=DEce9781adb6bb42507fff0ba4228ad6&output=json&location=%s,%s",
					lat, lng);
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

}
