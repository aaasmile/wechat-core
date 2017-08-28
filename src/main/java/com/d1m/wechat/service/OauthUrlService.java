package com.d1m.wechat.service;

import com.d1m.wechat.model.OauthUrl;

public interface OauthUrlService extends IService<OauthUrl> {

	OauthUrl getByShortUrl(String shortUrl);

	OauthUrl getByProcessClass(String processClass);

}
