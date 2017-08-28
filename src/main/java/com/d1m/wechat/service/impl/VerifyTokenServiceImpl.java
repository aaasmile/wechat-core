package com.d1m.wechat.service.impl;

import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.VerifyTokenMapper;
import com.d1m.wechat.model.VerifyToken;
import com.d1m.wechat.service.VerifyTokenService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.FileUploadConfigUtil;
import com.d1m.wechat.util.IllegalArgumentUtil;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.util.ParamUtil;
import com.d1m.wechat.util.Rand;
import com.d1m.wechat.util.RegexUtils;

@Service
public class VerifyTokenServiceImpl extends BaseService<VerifyToken> implements
		VerifyTokenService {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Autowired
	private VerifyTokenMapper verifyTokenMapper;

	@Override
	public Mapper<VerifyToken> getGenericMapper() {
		return verifyTokenMapper;
	}

	private boolean isExpired(VerifyToken verifyToken) {
		return verifyToken != null
				&& verifyToken.getExpiredOn().getTime() < System
						.currentTimeMillis();
	}

	private Date getExpiredOn() {
		return new Date(System.currentTimeMillis() + 1000 * 60 * 30);
	}

	@Override
	public VerifyToken getVerifyTokenByCode(Integer wechatId, Integer memberId,
			String accept, String code) {
		VerifyToken verifyToken = new VerifyToken();
		verifyToken.setWechatId(wechatId);
		verifyToken.setMemberId(memberId);
		verifyToken.setCode(code);
		verifyToken.setAccept(accept);
		List<VerifyToken> list = verifyTokenMapper.select(verifyToken);
		return !list.isEmpty() ? list.get(0) : null;
	}

	@Override
	public VerifyToken getVerifyTokenByToken(Integer wechatId,
			Integer memberId, String accept, String token) {
		VerifyToken verifyToken = new VerifyToken();
		verifyToken.setToken(token);
		verifyToken.setWechatId(wechatId);
		verifyToken.setMemberId(memberId);
		verifyToken.setAccept(accept);
		return verifyTokenMapper.selectOne(verifyToken);
	}

	@Override
	public VerifyToken sendMobileCode(Integer wechatId, Integer memberId,
			String mobile) {
		logger.info("mobile : {}", mobile);
		IllegalArgumentUtil.notBlank(mobile,
				Message.VERIFY_TOKEN_MOBILE_NOT_BLANK);
		if (!RegexUtils.match(RegexUtils.ELEVENMOBILE, mobile)) {
			throw new WechatException(Message.MOBILE_INVALID);
		}
		Date current = new Date();
		Date startAt = DateUtil.getDateBegin(current);
		Date endAt = DateUtil.getDateEnd(current);
		Integer count = verifyTokenMapper.countSend(wechatId, memberId, mobile,
				startAt, endAt);
		FileUploadConfigUtil configUtil = FileUploadConfigUtil.getInstance();
		Integer oneDayMobileSendCount = ParamUtil.getInt(
				configUtil.getValue(wechatId,"one_day_mobile_send_count"), 0);
		if (count >= oneDayMobileSendCount) {
			throw new WechatException(Message.VERIFY_TOKEN_MOBILE_SEND_OVER_MAX);
		}

		VerifyToken record = new VerifyToken();
		record.setWechatId(wechatId);
		record.setMemberId(memberId);
		record.setAccept(mobile);
		List<VerifyToken> verifyTokens = verifyTokenMapper.select(record);
		for (VerifyToken verifyToken : verifyTokens) {
			if (!isExpired(verifyToken)) {
				verifyToken.setVerified(true);
				verifyToken.setExpiredOn(current);
				verifyTokenMapper.updateByPrimaryKeySelective(verifyToken);
			}
		}
		VerifyToken verifyToken = new VerifyToken();
		verifyToken.setCode(Rand.randomInt(100000, 999999) + "");
		verifyToken.setCreatedAt(current);
		verifyToken.setAccept(mobile);
		verifyToken.setVerified(false);
		verifyToken.setExpiredOn(getExpiredOn());
		verifyToken.setToken(Rand.randomString(64));
		verifyToken.setWechatId(wechatId);
		verifyToken.setMemberId(memberId);
		verifyTokenMapper.insert(verifyToken);
		return verifyToken;
	}

	@Override
	public boolean verify(Integer wechatId, Integer memberId, String accept,
			String code) {
		VerifyToken verifyToken = getVerifyTokenByCode(wechatId, memberId,
				accept, code);
		IllegalArgumentUtil.notBlank(verifyToken,
				Message.VERIFY_TOKEN_NOT_EXIST);
		if (isExpired(verifyToken)) {
			throw new WechatException(Message.VERIFY_TOKEN_IS_EXPIRED);
		}
		if (verifyToken.getVerified() != null && verifyToken.getVerified()) {
			throw new WechatException(Message.VERIFY_TOKEN_IS_VERIFYED);
		}
		verifyToken.setVerified(true);
		verifyTokenMapper.updateByPrimaryKeySelective(verifyToken);
		return true;
	}

}
