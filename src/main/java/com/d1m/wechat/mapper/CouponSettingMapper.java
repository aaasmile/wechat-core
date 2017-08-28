package com.d1m.wechat.mapper;

import java.util.Date;
import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.CouponSettingDto;
import com.d1m.wechat.model.CouponSetting;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface CouponSettingMapper extends MyMapper<CouponSetting> {

	CouponSetting getByGrno(@Param("grno") String grno);

	Page<CouponSettingDto> search(@Param("wechatId") Integer wechatId,
			@Param("query") String query, @Param("channel") String channel,
			@Param("endAt") Date endAt, @Param("sortName") String sortName,
			@Param("sortDir") String sortDir);

	CouponSettingDto get(@Param("wechatId") Integer wechatId,
			@Param("id") Integer id);

	int countCoupon(@Param("wechatId") Integer wechatId,
			@Param("start") Date start, @Param("end") Date end,
			@Param("list") List<Integer> list);

}
