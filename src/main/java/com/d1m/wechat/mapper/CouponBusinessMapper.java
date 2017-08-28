package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.CouponSettingBusinessDto;
import com.d1m.wechat.model.CouponBusiness;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface CouponBusinessMapper extends MyMapper<CouponBusiness> {

	Page<CouponSettingBusinessDto> search(@Param("wechatId") Integer wechatId,
			@Param("id") Integer id, @Param("activityId") Integer activityId,
			@Param("type") Byte type, @Param("sortName") String sortName,
			@Param("sortDir") String sortDir);

}