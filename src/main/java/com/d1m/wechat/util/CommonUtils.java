package com.d1m.wechat.util;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.function.Function;
import java.util.function.Predicate;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.model.enums.RabbitmqMethod;
import com.d1m.wechat.model.enums.RabbitmqTable;
import com.d1m.wechat.service.ConversationService;

public class CommonUtils {

	private static final Logger log = LoggerFactory.getLogger(CommonUtils.class);
	
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

	/**
	 * lambda集合去重判断
	 *
	 * @param keyExtractor key
	 * @param <T>          object
	 */
	public static <T> Predicate<T> distinctByKey(Function<? super T, Object> keyExtractor) {
		Map<Object, Boolean> map = new ConcurrentHashMap<>(100);
		return t -> map.putIfAbsent(keyExtractor.apply(t), Boolean.TRUE) == null;
	}
	
	public static void send2SocialWechatCoreApi(Integer wechatId, MemberDto member, Integer newid, String newtype, ConversationService conversationService) {
		if(newid == null) {
			log.error("newid is null!");
		}
		Map<String, String> wechatMessage = new HashMap<String, String>();
		member.MemberToMap(wechatMessage);
		wechatMessage.put("wechatId", wechatId.toString());
		wechatMessage.put("FromUserName", member.getOpenId());
		if("dcrm".equals(newtype)) {
			wechatMessage.put("dcrmImageTextDetailId", newid.toString());
			conversationService.send2SocialWechatCoreApi(RabbitmqTable.DCRM_IMAGE_TEXT, RabbitmqMethod.SEND_DCRM_IMAGE_TEXT, wechatMessage);
		} else {
			wechatMessage.put("materialImageTextDetailId", newid.toString());
			conversationService.send2SocialWechatCoreApi(RabbitmqTable.WECHAT_IMAGE_TEXT, RabbitmqMethod.SEND_WECHAT_IMAGE_TEXT, wechatMessage);
		}
	}
}
