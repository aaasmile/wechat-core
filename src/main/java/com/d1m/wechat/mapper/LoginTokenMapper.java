package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.LoginToken;
import com.d1m.wechat.util.MyMapper;

public interface LoginTokenMapper extends MyMapper<LoginToken> {

	List<LoginToken> getActiveTokens(@Param("userId") Integer userId);

}