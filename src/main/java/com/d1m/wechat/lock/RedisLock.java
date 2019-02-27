package com.d1m.wechat.lock;

import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;

@Slf4j
public class RedisLock {

    private StringRedisTemplate redisTemplate;

    private String lockKey;

    /**
     * 锁超时时间，防止线程在入锁以后，无限的执行等待
     */
    private int expireMsecs = 60 * 1000;

    /**
     * 锁等待时间，防止线程饥饿
     */
    private int timeoutMsecs = 10 * 1000;

    private volatile boolean locked = false;

    private static final int DEFAULT_ACQUIRY_RESOLUTION_MILLIS = 100;

    public RedisLock(StringRedisTemplate redisTemplate, String lockKey) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey + "_lock";
    }

    public RedisLock(StringRedisTemplate redisTemplate, String lockKey, int expireMsecs) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
        this.expireMsecs = expireMsecs;
    }

    public RedisLock(StringRedisTemplate redisTemplate, String lockKey, int expireMsecs, int timeoutMsecs) {
        this.redisTemplate = redisTemplate;
        this.lockKey = lockKey;
        this.expireMsecs = expireMsecs;
        this.timeoutMsecs = timeoutMsecs;
    }

    public String getLockKey() {
        return lockKey;
    }

    private boolean setNx(final String key, final String value) {
        return redisTemplate.opsForValue().setIfAbsent(key, value);
    }

    private String get(final String key) {
        return redisTemplate.opsForValue().get(key);
    }

    private String getSet(final String key, final String value) {
        return redisTemplate.opsForValue().getAndSet(key, value);
    }

    public synchronized boolean lock() throws InterruptedException {
        int timeout = timeoutMsecs;
        while (timeout >= 0) {
            long expires = System.currentTimeMillis() + expireMsecs + 1;
            String expiresStr = String.valueOf(expires);

            if(this.setNx(lockKey, expiresStr)) {
                locked = true;
                return true;
            }

            String currentValue = this.get(lockKey);

            if (currentValue != null && Long.parseLong(currentValue) < System.currentTimeMillis()) {

                String oldValue = this.getSet(lockKey, expiresStr);

                if (oldValue != null && oldValue.equals(currentValue)) {
                    locked = true;
                    return true;
                }
            }

            timeout -= DEFAULT_ACQUIRY_RESOLUTION_MILLIS;

            Thread.sleep(DEFAULT_ACQUIRY_RESOLUTION_MILLIS);
        }
        return false;
    }

    public synchronized void unlock() {
        if(locked) {
            redisTemplate.delete(lockKey);
            locked = false;
        }
    }


}
