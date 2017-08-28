package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.User;
import com.d1m.wechat.model.UserWechat;
import com.d1m.wechat.util.MyMapper;

public interface UserWechatMapper extends MyMapper<UserWechat> {
	
	List<UserWechat> search(@Param("userId")Integer userId,
			@Param("wechatId")Integer wechatId, @Param("companyId") Integer companyId);

    List<User> listWechatUsers(@Param("wechatId")Integer wechatId);

}
