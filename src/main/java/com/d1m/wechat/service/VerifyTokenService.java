package com.d1m.wechat.service;

import com.d1m.wechat.model.VerifyToken;

public interface VerifyTokenService extends IService<VerifyToken> {

	public VerifyToken getVerifyTokenByCode(Integer wechatId, Integer memberId,
			String accept, String code);

	public VerifyToken getVerifyTokenByToken(Integer wechatId,
			Integer memberId, String accept, String token);

	public VerifyToken sendMobileCode(Integer wechatId, Integer memberId,
			String mobile);

	public boolean verify(Integer wechatId, Integer memberId, String accept,
			String code);

}
