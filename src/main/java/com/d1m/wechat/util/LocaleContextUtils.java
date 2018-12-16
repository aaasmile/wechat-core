package com.d1m.wechat.util;

import java.util.Locale;

/**
 * Created by jone.wang on 2018/12/5.
 * Description:
 */
public class LocaleContextUtils {

    private static final ThreadLocal<Locale> localeThreadLocal = new ThreadLocal<>();

    private LocaleContextUtils() {
        throw new UnsupportedOperationException("Utils class can not be create!");
    }

    public static void setLocale(Locale locale) {
        localeThreadLocal.set(locale);
    }

    public static Locale getLocale() {
        return localeThreadLocal.get();
    }
}
