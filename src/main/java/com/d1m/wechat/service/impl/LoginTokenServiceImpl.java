package com.d1m.wechat.service.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.LoginTokenMapper;
import com.d1m.wechat.model.LoginToken;
import com.d1m.wechat.model.User;
import com.d1m.wechat.service.LoginTokenService;

@Service
public class LoginTokenServiceImpl extends BaseService<LoginToken> implements
		LoginTokenService {

	private static Logger log = LoggerFactory
			.getLogger(LoginTokenServiceImpl.class);

	@Autowired
	private LoginTokenMapper loginTokenMapper;

	@Override
	public Mapper<LoginToken> getGenericMapper() {
		return loginTokenMapper;
	}

	@Override
	public LoginToken getByToken(String token) {
		LoginToken loginToken = new LoginToken();
		loginToken.setToken(token);
		return loginTokenMapper.selectOne(loginToken);
	}

	@Override
	public List<LoginToken> getActiveTokens(User user) {
		return loginTokenMapper.getActiveTokens(user.getId());
	}

}
