package com.d1m.wechat.util;

import net.sf.ehcache.Cache;
import net.sf.ehcache.CacheManager;

public class CacheUtils {

	public static Cache addCache(Cache cache) {
		getManager().addCache(cache);
		return cache;
	}

	public static Cache addCache(String name) {
		return addCache(new Cache(name, 0, false, false, 259200, 259200));
	}

	public static Cache addCache(String name, int maxElementsInMemory,
			boolean overflowToDisk, boolean external, long ttl, long tti) {
		return addCache(new Cache(name, maxElementsInMemory, overflowToDisk,
				external, ttl, tti));
	}

	public static Cache getCache(String name) {
		return getManager().getCache(name);
	}

	public static Cache getNewsCache(String name) {
		Cache cache = getManager().getCache(name);
		if (cache == null) {
			getManager().addCache(name);
		}

		return getManager().getCache(name);
	}

	public static CacheManager getManager() {
		return CacheManager.getInstance();
	}

	{
		CacheManager.create();
	}

}
