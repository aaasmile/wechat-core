package com.d1m.wechat.configure;

import com.d1m.wechat.common.Constant;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cache.CacheManager;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer;

import java.util.HashMap;
import java.util.Map;

/**
 * RedisConfigure
 *
 * @author f0rb on 2017-04-19.
 */
@RefreshScope
@Configuration
public class RedisConfigure {
    @Value("${redis.default.expiration:300}")
    private Long redisDefaultExpiration;

    public @Bean RedisTemplate<Object, Object> redisTemplate(RedisConnectionFactory connectionFactory) {
        RedisTemplate<Object, Object> template = new RedisTemplate<>();
        template.setConnectionFactory(connectionFactory);
        template.setDefaultSerializer(new GenericJackson2JsonRedisSerializer());
        return template;
    }

    public @Bean CacheManager cacheManager(@Qualifier("redisTemplate") RedisTemplate<Object, Object> redisOperations) {
        RedisCacheManager redisCacheManager = new RedisCacheManager(redisOperations);
        redisCacheManager.setUsePrefix(true);
        redisCacheManager.setDefaultExpiration(redisDefaultExpiration);

        Map<String, Long> expiresMap = new HashMap<>();
        expiresMap.put(Constant.Cache.storeCache, 1000L);
        expiresMap.put(Constant.Cache.memberSession, 60 * 60 * 24 * 30L);
        expiresMap.put(Constant.Cache.memberToken, 60 * 60 * 24 * 30L);
        expiresMap.put(Constant.Cache.tokenMember, 60 * 60 * 24 * 30L);
        redisCacheManager.setExpires(expiresMap);

        return redisCacheManager;
    }

}
