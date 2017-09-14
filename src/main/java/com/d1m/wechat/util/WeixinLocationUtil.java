package com.d1m.wechat.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.exception.WechatException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.net.URL;
import java.net.URLConnection;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

public class WeixinLocationUtil {

	private static Logger log = LoggerFactory
			.getLogger(WeixinLocationUtil.class);

	public static Map<String, Double> getWxLatAndLngByBaiduLocation(String lat,
			String lng) {

		notBlank(lat, Message.BUSINESS_LAT_NOT_BLANK);
		notBlank(lng, Message.BUSINESS_LNG_NOT_BLANK);

		FileUploadConfigUtil config = FileUploadConfigUtil.getInstance();
		String WexinKey = config.getValue("weixin_map_key");
		try {
			lat = URLEncoder.encode(lat, "UTF-8");
			lng = URLEncoder.encode(lng, "UTF-8");
			WexinKey = URLEncoder.encode(WexinKey, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			log.error(e.getMessage());
			throw new WechatException(Message.MAP_URL_ENCODING_FAIL);
		}
		String url = String.format(
				"http://apis.map.qq.com/ws/coord/v1/translate?"
						+ "locations=%s,%s&type=3&key=%s", lat, lng, WexinKey);
		String result = sendGet(url, "UTF-8");
		JSONObject json = JSONObject.parseObject(result);
		Integer status = json.getInteger("status");
		Map<String, Double> map = new HashMap<String, Double>();
		if (status == 0) {
			String locations = json.getString("locations");
			JSONArray locationArray = JSONObject.parseArray(locations);
			JSONObject locationJson = locationArray.getJSONObject(0);
			lat = locationJson.getString("lat");
			lng = locationJson.getString("lng");

			map.put("wxlat", new Double(lat));
			map.put("wxlng", new Double(lng));
		} else {
			map = null;
		}

		return map;
	}

	public static String sendGet(String url, String encoding) {
		String result = "";
		BufferedReader in = null;
		try {
			URL reqUrl = new URL(url);
			URLConnection connect = reqUrl.openConnection();
			connect.setRequestProperty("Accept", "application/json");
			connect.setRequestProperty("Content-Type", "application/json");
			connect.setRequestProperty("Accept-Charset", "utf-8");
			connect.connect();
			in = new BufferedReader(new InputStreamReader(
					connect.getInputStream(), encoding));
			String line;
			while ((line = in.readLine()) != null) {
				result += line;
			}

		} catch (Exception e) {
			System.out.println("发送GET请求异常！" + e);
            e.printStackTrace();
		} finally {
            try {
                if (in != null) {
                    in.close();
                }
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
		return result;

	}

}
