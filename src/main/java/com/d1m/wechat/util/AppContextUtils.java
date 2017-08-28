package com.d1m.wechat.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

public class AppContextUtils implements ApplicationContextAware {

	private static ApplicationContext ctx;

	protected static Logger log = LoggerFactory
			.getLogger(AppContextUtils.class);

	public static void destroy() {
		ctx = null;
	}

	public static <T> T getBean(Class<T> clz) {
		return getContext().getBean(clz);
	}

	public static <T> T getBean(String name, Class<T> clz) {
		return getContext().getBean(name, clz);
	}

	public static ApplicationContext getContext() {
		return ctx;
	}

	@Override
	public void setApplicationContext(ApplicationContext applicationContext)
			throws BeansException {
		ctx = applicationContext;
	}
}
