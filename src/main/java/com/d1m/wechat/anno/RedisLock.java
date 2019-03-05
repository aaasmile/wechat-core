package com.d1m.wechat.anno;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.util.concurrent.TimeUnit;

/**
 * Created by jone.wang on 2019/3/1.
 * Description:
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.METHOD})
public @interface RedisLock {

    /**
     * @return key
     */
    String key();

    /**
     * @return 过期时间，毫秒值
     */
    long expire() default 60000;

    /**
     * @return 重试次数
     */
    int retryTimes() default Integer.MAX_VALUE;

    /**
     * @return 获取锁重试间隔时间
     */
    long sleep() default 500;

    /**
     * @return 重试时间的单位
     */
    TimeUnit timeUnit() default TimeUnit.MILLISECONDS;

}
