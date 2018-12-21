package com.d1m.wechat.util;

import java.util.*;

public class I18nUtil {

    public static String getMessage(String key, Locale locale) {
        String value = AppContextUtils.getContext().getMessage(key, null, locale);
        if (Objects.equals(value, key) && key != null && key.contains("_")) {//尝试将key的_转为.再找一次
            value = AppContextUtils.getContext().getMessage(key.replaceAll("_", "."), null, locale);
        }
        return value == null ? key : value;
    }

    public static String getMessage(String key) {
        return Optional.ofNullable(LocaleContextUtils.getLocale())
                .map(locale -> getMessage(key, locale))
                .orElseGet(() -> getMessage(key, Locale.CHINA));
    }

    public static List<String> getMessage(List<String> keys, Locale locale) {
        List<String> values = new ArrayList<String>();
        for (String key : keys) {
            values.add(getMessage(key, locale));
        }
        return values;
    }

    public static String[] getMessage(String[] keys, Locale locale) {
        if (keys == null || keys.length == 0) {
            return null;
        }
        String[] values = new String[keys.length];
        for (int i = 0; i < values.length; i++) {
            values[i] = getMessage(keys[i], locale);
        }
        return values;
    }

}
