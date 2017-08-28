package com.d1m.wechat.util;

import java.util.HashMap;
import java.util.Map;

import net.sf.ehcache.Cache;
import net.sf.ehcache.Element;

import org.apache.commons.codec.digest.DigestUtils;

import com.d1m.wechat.model.Member;

public class SessionCacheUtil {

	private static Cache memberCache = getCache("member-session");

	private static Cache token2MemberCache = getCache("token-member");

	private static Cache member2TokenCache = getCache("member-token");

	@SuppressWarnings("unchecked")
	public static String addMember(Member member, String realm) {
		Integer id = member.getId();
		if (id == null) {
			throw new IllegalArgumentException("member id expected");
		}
		Element elem = member2TokenCache.get(id);
		Map<String, String> tokens = null;
		if (elem == null) {
			tokens = new HashMap<String, String>();
		} else {
			tokens = (Map<String, String>) elem.getObjectValue();
		}
		String token = tokens.get(realm);
		if (token == null) {
			token = DigestUtils.md5Hex(member.getOpenId() + "@"
					+ System.currentTimeMillis());
			tokens.put(realm, token);
		}
		Member m = new Member();
		m.setId(id);
		m.setOpenId(member.getOpenId());
		m.setWechatId(member.getWechatId());
		token2MemberCache.put(new Element(token, id));
		memberCache.put(new Element(id, m));
		member2TokenCache.put(new Element(id, tokens));
		return token;
	}

	private static Cache getCache(String name) {
		Cache cache = CacheUtils.getCache(name);
		if (cache == null) {
			cache = CacheUtils.addCache(name);
		}
		return cache;
	}

	public static Member getMember(Object token) {
		Element elem = token2MemberCache.get(token);
		if (elem == null) {
			return null;
		}
		elem = memberCache.get(elem.getObjectValue());
		if (elem == null) {
			token2MemberCache.remove(token);
			return null;
		}
		return (Member) elem.getObjectValue();
	}

	public static String getToken(Integer id, String realm) {
		Map<String, String> tokens = getTokens(id);
		return tokens != null ? tokens.get(realm) : null;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getTokens(Integer id) {
		Element elem = member2TokenCache.get(id);
		if (elem == null)
			return null;
		return (Map<String, String>) elem.getObjectValue();
	}

	public static void removeMember(Integer id) {
		if (id == null) {
			return;
		}
		memberCache.remove(id);
		Map<String, String> tokens = getTokens(id);
		if (tokens == null) {
			return;
		}
		for (String token : tokens.values()) {
			token2MemberCache.remove(token);
		}
		member2TokenCache.remove(id);
	}

}
