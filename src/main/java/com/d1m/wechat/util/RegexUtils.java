package com.d1m.wechat.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class RegexUtils {

	public static final Pattern ELEVENMOBILE = Pattern
			.compile("^(13[0-9]|14[0-9]|15[0-9]|17[0-9]|18[0-9])\\d{8}$");

	public static boolean match(Pattern pattern, String text) {
		Matcher m = pattern.matcher(text);
		return m.matches();
	}

}
