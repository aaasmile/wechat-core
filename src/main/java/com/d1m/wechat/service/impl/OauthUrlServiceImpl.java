package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.OauthUrlMapper;
import com.d1m.wechat.model.OauthUrl;
import com.d1m.wechat.service.OauthUrlService;

@Service
public class OauthUrlServiceImpl extends BaseService<OauthUrl> implements
		OauthUrlService {

	@Autowired
	private OauthUrlMapper oauthUrlMapper;

	@Override
	public Mapper<OauthUrl> getGenericMapper() {
		return oauthUrlMapper;
	}

	@Override
	public OauthUrl getByShortUrl(String shortUrl) {
		OauthUrl ou = new OauthUrl();
		ou.setShortUrl(shortUrl);
		return oauthUrlMapper.selectOne(ou);
	}

	@Override
	public OauthUrl getByProcessClass(String processClass) {
		OauthUrl ou = new OauthUrl();
		ou.setProcessClass(processClass);
		return oauthUrlMapper.selectOne(ou);
	}

}
