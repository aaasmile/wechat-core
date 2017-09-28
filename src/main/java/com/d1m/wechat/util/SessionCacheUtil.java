package com.d1m.wechat.util;

import com.d1m.wechat.model.Member;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.cache.Cache;
import org.springframework.cache.CacheManager;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

@Component
public class SessionCacheUtil {

    private static Cache memberCache;
    private static Cache token2MemberCache;
    private static Cache member2TokenCache;

    @Resource
    public void setCacheManager(CacheManager cacheManager) {
         memberCache = cacheManager.getCache("member-session");
         token2MemberCache = cacheManager.getCache("token-member");
         member2TokenCache = cacheManager.getCache("member-token");
    }

	@SuppressWarnings("unchecked")
	public static String addMember(Member member, String realm) {
		Integer id = member.getId();
		if (id == null) {
			throw new IllegalArgumentException("member id expected");
		}
        Cache.ValueWrapper elem = member2TokenCache.get(id);
		Map<String, String> tokens;
		if (elem == null) {
			tokens = new HashMap<>();
		} else {
			tokens = (Map<String, String>) elem.get();
		}
        String token = tokens.computeIfAbsent(realm, k -> DigestUtils.md5Hex(member.getOpenId() + "@" + System.currentTimeMillis()));
		Map<String,Object> data = new HashMap<String,Object>();
		data.put("id",id);
		data.put("wechatId",member.getWechatId());
		data.put("openId",member.getOpenId());

		token2MemberCache.put(token, id);
		memberCache.put(id, data);
		member2TokenCache.put(id, tokens);
		return token;
	}

	public static Map<String,Object> getMember(Object token) {
        Cache.ValueWrapper elem = token2MemberCache.get(token);
		if (elem == null) {
			return null;
		}
		elem = memberCache.get(elem.get());
		if (elem == null) {
			token2MemberCache.evict(token);
			return null;
		}
		return (Map<String,Object>) elem.get();
	}

	public static String getToken(Integer id, String realm) {
		Map<String, String> tokens = getTokens(id);
		return tokens != null ? tokens.get(realm) : null;
	}

	@SuppressWarnings("unchecked")
	public static Map<String, String> getTokens(Integer id) {
        Cache.ValueWrapper elem = member2TokenCache.get(id);
		if (elem == null)
			return null;
		return (Map<String, String>) elem.get();
	}

	public static void removeMember(Integer id) {
		if (id == null) {
			return;
		}
		memberCache.evict(id);
		Map<String, String> tokens = getTokens(id);
		if (tokens == null) {
			return;
		}
		for (String token : tokens.values()) {
			token2MemberCache.evict(token);
		}
		member2TokenCache.evict(id);
	}

}
