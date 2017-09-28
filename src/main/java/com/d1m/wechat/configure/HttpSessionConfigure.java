package com.d1m.wechat.configure;

import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

/**
 * 启用RedisSession
 * @author Stoney.Liu on 2017-07-20.
 */
@Configuration
@EnableRedisHttpSession
public class HttpSessionConfigure {

}