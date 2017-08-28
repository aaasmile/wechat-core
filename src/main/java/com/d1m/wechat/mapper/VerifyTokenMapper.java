package com.d1m.wechat.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.VerifyToken;
import com.d1m.wechat.util.MyMapper;

public interface VerifyTokenMapper extends MyMapper<VerifyToken> {

	Integer countSend(@Param("wechatId") Integer wechatId,
			@Param("memberId") Integer memberId,
			@Param("mobile") String mobile, @Param("startAt") Date startAt,
			@Param("endAt") Date endAt);

}