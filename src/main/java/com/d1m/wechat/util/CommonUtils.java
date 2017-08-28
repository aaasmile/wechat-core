package com.d1m.wechat.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;

public class CommonUtils {

	public static String urlEncodeUTF8(String source) {
		String result = source;
		try {
			result = java.net.URLEncoder.encode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

	public static String urlDecode(String source) {
		String result = source;
		try {
			result = URLDecoder.decode(source, "utf-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		return result;
	}

}
