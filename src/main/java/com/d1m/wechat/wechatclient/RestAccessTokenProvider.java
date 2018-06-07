package com.d1m.wechat.wechatclient;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import cn.d1m.wechat.client.core.AccessTokenProvider;
import cn.d1m.wechat.client.util.HashUtil;

import com.d1m.common.rest.RestResponse;
import com.d1m.wechat.thirdparty.AccessToken;
import com.d1m.wechat.util.AppContextUtils;

/**
 * RestAccessTokenProvider
 *
 * @author f0rb on 2017-04-07.
 */
public class RestAccessTokenProvider implements AccessTokenProvider {
	
	private static final Logger log = LoggerFactory.getLogger(RestAccessTokenProvider.class);
			
    protected final String appid;
    protected final String secret;
    protected final int hash;
    /** 单元测试使用 */
    private final ReadWriteLock lock = new ReentrantReadWriteLock();

    protected String accessToken;
    private WechatTokenRestService wechatTokenRestService;
    private WechatTokenRestServiceV1 wechatTokenRestServiceV1;
    private boolean updateToken;

    public RestAccessTokenProvider(String appid, String secret) {
    	String wechatTokenServerV1 = System.getProperty("wechat-token-server-v1");
    	this.updateToken = wechatTokenServerV1 != null && wechatTokenServerV1.indexOf("appid") > 0;
        this.wechatTokenRestService = AppContextUtils.getBean(WechatTokenRestService.class);
        this.wechatTokenRestServiceV1 = AppContextUtils.getBean(WechatTokenRestServiceV1.class);
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
    	RestResponse<String> restResponse = null;
    	if(updateToken) {
    		restResponse = wechatTokenRestServiceV1.getJsApiTicket(appid, secret);
    	} else {
    		restResponse = wechatTokenRestService.getJsApiTicket(appid, secret);
    	}
    	if(restResponse == null) {
    		return null;
    	}
        return restResponse.getData();
    }

    @Override
    public String getCardApiTicket() {
    	RestResponse<String> restResponse = null;
    	if(updateToken) {
    		restResponse = wechatTokenRestServiceV1.getCardApiTicket(appid, secret);
    	} else {
    		restResponse = wechatTokenRestService.getCardApiTicket(appid, secret);
    	}
    	if(restResponse == null) {
    		return null;
    	}
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
    	log.info("getAccessTokenFromWechat appid>>" + appid);
    	if(StringUtils.isNotEmpty(appid) && appid.indexOf("com.d1m") > -1 && StringUtils.isNotEmpty(secret)) {
    		try {
				Class className = Class.forName(appid);
				AccessToken accessToken = (AccessToken) className.newInstance();
				return accessToken.getAccessToken(secret);
			} catch (Exception e) {
				log.error(e.getMessage(), e);
			}
    		return null;
    	} else {
    		RestResponse<String> restResponse = null;
    		if(updateToken) {
    			restResponse = wechatTokenRestServiceV1.refreshAccessToken(appid, secret);
    		} else {
    			restResponse = wechatTokenRestService.refreshAccessToken(appid, secret);
    		}
    		if(restResponse == null) {
    			return null;
    		}
	        if (!restResponse.isSuccess()) {
	            throw new RuntimeException("AccessToken获取失败[appid=" + appid + ",secret=" + secret + "]:" + restResponse.getInfo());
	        }
	        return restResponse.getData();
    	}
    }

	public String getAccessToken() {
		return accessToken;
	}

	public void setAccessToken(String accessToken) {
		this.accessToken = accessToken;
	}

	public String getAppid() {
		return appid;
	}
}
