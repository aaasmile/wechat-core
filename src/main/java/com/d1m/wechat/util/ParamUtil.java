package com.d1m.wechat.util;

import org.dom4j.Element;

import java.math.BigDecimal;

public class ParamUtil {

	public static BigDecimal getBigDecimal(Object value, BigDecimal defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			return new BigDecimal(value.toString());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static Boolean getBoolean(Object value, Boolean defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		return Boolean.valueOf(value.toString());
	}

	public static Double getDouble(Object value, Double defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			return Double.parseDouble(value.toString().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static Float getFloat(Object value, Float defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			return Float.parseFloat(value.toString().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static Byte getByte(Object value, Byte defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			return Byte.parseByte(value.toString().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static Integer getInt(Object value, Integer defaultValue) {
		if (value == null)
			return defaultValue;
		try {
			return Integer.parseInt(value.toString().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static Long getLong(Object value, Long defaultValue) {
		if (value == null) {
			return defaultValue;
		}
		try {
			return Long.parseLong(value.toString().trim());
		} catch (NumberFormatException e) {
			e.printStackTrace();
			return defaultValue;
		}
	}

	public static String getElementContent(Element root, String key) {
		return root.element(key) != null ? root.element(key).getTextTrim() : null;
	}

}
