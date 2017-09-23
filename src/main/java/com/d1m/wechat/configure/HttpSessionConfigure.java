package com.d1m.wechat.configure;

import com.d1m.wechat.model.Config;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.boot.autoconfigure.data.redis.RedisAutoConfiguration;
import org.springframework.boot.autoconfigure.data.redis.RedisProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisSentinelConfiguration;
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory;
import org.springframework.session.data.redis.config.annotation.web.http.EnableRedisHttpSession;

import javax.annotation.Resource;

/**
 * SessionConfigure
 *
 * @author f0rb on 2017-07-13.
 */
@SuppressWarnings("SpringJavaAutowiringInspection")
@AutoConfigureAfter(RedisAutoConfiguration.class)
@Configuration
@EnableRedisHttpSession
public class HttpSessionConfigure {
    @Resource
    private RedisProperties redisProperties;

    @Bean
    public LettuceConnectionFactory connectionFactory() {
        LettuceConnectionFactory lettuceConnectionFactory;
        Config config = new Config();
        //sentinel
        if (redisProperties.getSentinel() != null) {
            RedisProperties.Sentinel sentinel = redisProperties.getSentinel();
            RedisSentinelConfiguration sentinelServersConfig = new RedisSentinelConfiguration().master(sentinel.getMaster());
            for (String hostPort : sentinel.getNodes().split(",")) {
                int colonIndex = hostPort.indexOf(':');
                String host = hostPort.substring(0, colonIndex);
                int port = Integer.parseInt(hostPort.substring(colonIndex + 1));
                sentinelServersConfig.sentinel(host, port);
            }
            lettuceConnectionFactory = new LettuceConnectionFactory(sentinelServersConfig);
        } else { //single server
            lettuceConnectionFactory = new LettuceConnectionFactory(redisProperties.getHost(), redisProperties.getPort());
        }
        lettuceConnectionFactory.setDatabase(redisProperties.getDatabase());
        if (redisProperties.getPassword() != null) {
            lettuceConnectionFactory.setPassword(redisProperties.getPassword());
        }
        return lettuceConnectionFactory;
    }
}
