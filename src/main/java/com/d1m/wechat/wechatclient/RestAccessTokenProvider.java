package com.d1m.wechat.wechatclient;

import cn.d1m.wechat.client.core.AccessTokenProvider;
import cn.d1m.wechat.client.util.HashUtil;
import com.d1m.common.rest.RestResponse;
import com.d1m.wechat.util.AppContextUtils;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * RestAccessTokenProvider
 *
 * @author f0rb on 2017-04-07.
 */
@Slf4j
public class RestAccessTokenProvider implements AccessTokenProvider {
    @Getter
    protected final String appid;
    protected final String secret;
    protected final int hash;
    /** 单元测试使用 */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();
    @Getter
    protected String accessToken;
    private WechatTokenRestService wechatTokenRestService;

    public RestAccessTokenProvider(String appid, String secret) {
        this.wechatTokenRestService = AppContextUtils.getBean(WechatTokenRestService.class);
        this.appid = appid;
        this.secret = secret;
        this.hash = HashUtil.hash(appid, secret);
        this.accessToken = getAccessTokenFromWechat();
    }

    @Override
    public int hashCode() {
        return hash;
    }

    @Override
    public final String readAccessToken() {
        try {
            log.info("[{}]等待读锁", appid);
            lock.readLock().lock();
            log.info("[{}]拿到读锁", appid);
            return getAccessToken();
        } finally {
            lock.readLock().unlock();
        }
    }

    @Override
    public final boolean refreshAccessToken() {
        log.info("[{}]争抢写锁:", appid);
        if (!lock.writeLock().tryLock()) {
            log.info("[{}]抢锁失败", appid);
            return false;
        }
        try {
            log.info("[{}]抢到写锁", appid);
            accessToken = getAccessTokenFromWechat();
            return true;
        } catch (Exception e) {
            log.error("AccessToken刷新失败!", e);
            return false;
        } finally {
            lock.writeLock().unlock();
        }
    }

    @Override
    public String getJsApiTicket() {
        RestResponse<String> restResponse = wechatTokenRestService.getJsApiTicket(appid, secret);
        return restResponse.getData();
    }

    @Override
    public String getCardApiTicket() {
        RestResponse<String> restResponse = wechatTokenRestService.getCardApiTicket(appid, secret);
        return restResponse.getData();
    }

    @Override
    public void refreshJsApiTicket() {
    }

    @Override
    public void refreshCardApiTicket() {
    }

    @Override
    public void release() {
    }

    /**
     * 实际获取微信access_token的方法
     *
     * @return 微信返回的access_token
     * @throws RuntimeException 请求access_token出错时, 通过异常传递微信返回的错误信息
     */
    protected String getAccessTokenFromWechat() {
        RestResponse<String> restResponse = wechatTokenRestService.refreshAccessToken(appid, secret);
        return restResponse.getData();
    }

}
