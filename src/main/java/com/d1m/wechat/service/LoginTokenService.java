package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.model.LoginToken;
import com.d1m.wechat.model.User;

public interface LoginTokenService extends IService<LoginToken> {

	LoginToken getByToken(String token);

	List<LoginToken> getActiveTokens(User user);

}
