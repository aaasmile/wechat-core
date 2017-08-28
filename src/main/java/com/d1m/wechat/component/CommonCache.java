package com.d1m.wechat.component;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Component;

/**
 * CommonCache
 *
 * @author f0rb on 2017-03-16.
 */
@Slf4j
@Component
public class CommonCache {
    /**
     * 缓存一个对象, 返回缓存时的时间, 如果该对象已被缓存, 则返回的时间一定是一个旧的, 可以据此判断指定对象是否已缓冲
     *
     * @param string 待缓存
     * @return 缓存日期
     */
    @Cacheable(value = "storeCache", key = "#string")
    public Date store(String string) {
        log.info("Caching : {}", string);
        try {
            Thread.sleep(1);
        } catch (InterruptedException e) {//ignore
        }
        return new Date();
    }

    @CacheEvict(value = "storeCache", key = "#string", beforeInvocation = true)
    //@Cacheable(value = "store", key = "#string")
    public void restore(String string) {
    }
}
