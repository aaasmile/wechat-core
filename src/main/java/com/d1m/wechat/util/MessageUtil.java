package com.d1m.wechat.util;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MessageUtil {

	private static Logger logger = LoggerFactory.getLogger(MessageUtil.class);

	private static Object LOCK = new Object();
	private static MessageUtil CONFIG = null;

	private static Map<String, Integer> map = new HashMap<String, Integer>();

	public static MessageUtil getInstance() {
		synchronized (LOCK) {
			if (CONFIG == null) {
				CONFIG = new MessageUtil();
			}
		}
		return (CONFIG);
	}

	private MessageUtil() {
		for (Message message : Message.values()) {
			map.put(message.name(), message.getCode());
		}
	}

	public Integer getResultCode(String name) {
		return map.get(name);
	}

}
