package com.d1m.wechat.util;

/**
 * Created by jone.wang on 2019/2/28.
 * Description:
 */
public interface DistributedLock {
    long TIMEOUT_MILLIS = 30000;

    int RETRY_TIMES = Integer.MAX_VALUE;

    long SLEEP_MILLIS = 500;

    default boolean lock(String key) {
        return lock(key, TIMEOUT_MILLIS, RETRY_TIMES, SLEEP_MILLIS);
    }


    default boolean lock(String key, int retryTimes) {
        return lock(key, TIMEOUT_MILLIS, retryTimes, SLEEP_MILLIS);
    }


    default boolean lock(String key, int retryTimes, long sleepMillis) {
        return lock(key, TIMEOUT_MILLIS, retryTimes, sleepMillis);
    }

    default boolean lock(String key, long expire) {
        return lock(key, expire, RETRY_TIMES, SLEEP_MILLIS);
    }


    default boolean lock(String key, long expire, int retryTimes) {
        return lock(key, expire, retryTimes, SLEEP_MILLIS);
    }

    boolean lock(String key, long expire, int retryTimes, long sleepMillis);

    boolean releaseLock(String key);
}
