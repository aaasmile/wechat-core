package com.d1m.wechat.component;

import java.util.Date;
import javax.annotation.Resource;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * WxNotifyCache
 * <p>
 * 微信重复消息判断优化
 *
 * @author f0rb on 2017-02-27.
 */
@Slf4j
@Component
public class CommonRepeatChecker {

    @Resource
    private CommonCache commonCache;

    public boolean checkRepeat(String query) {
        Date t1 = new Date();
        Date t2 = commonCache.store(query);
        boolean isRepeat = t1.getTime() > t2.getTime();
        log.info("[{}]:{}", isRepeat ? "Cached" : "Uncached", query);
        return isRepeat;
    }

    public boolean checkIfExistsInSeconds(String query, Integer seconds) {
        long t1 = System.currentTimeMillis();
        long t2 = commonCache.store(query).getTime();

        boolean isCached = t1 > t2;
        log.info("[{}]:{}", isCached ? "Cached" : "Uncached", query);
        if (isCached) {
            boolean isExpired = t1 > t2 + seconds * 1000;// 缓存时间超过了seconds秒

            if (isExpired) {// 清理并重新缓存
                log.info("[Expired]:{}", query);
                commonCache.restore(query);
                commonCache.store(query);
                return false;
            }
        }
        return isCached;
    }

}
