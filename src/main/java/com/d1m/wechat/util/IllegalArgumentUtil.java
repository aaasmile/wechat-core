package com.d1m.wechat.util;


import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.exception.WechatException;

public class IllegalArgumentUtil {

	public static String notBlank(String key, JSONObject json, Message message)
			throws WechatException {
		if (!json.containsKey(key)) {
			throw new WechatException(message);
		}
		return json.getString(key);
	}

	public static Object notBlank(Object value, Message message)
			throws WechatException {
		if (value == null) {
			throw new WechatException(message);
		}
		return value;
	}

	public static String notBlank(String value, Message message)
			throws WechatException {
		if (StringUtils.isBlank(value)) {
			throw new WechatException(message);
		}
		return value;
	}

	public static void equal(String value1, String value2, Message message)
			throws WechatException {
		if (!StringUtils.equals(value1, value2)) {
			throw new WechatException(message);
		}
	}

	
	//判断，返回布尔值  
	public static boolean isPhoneNumber(String input){  
	    String regex="1([\\d]{10})|((\\+[0-9]{2,4})?\\(?[0-9]+\\)?-?)?[0-9]{7,8}";  
	    return Pattern.matches(regex, input);  
	}  
	
}
