package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.ActivityQrcodeDto;
import com.d1m.wechat.model.ActivityQrcode;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface ActivityQrcodeMapper extends MyMapper<ActivityQrcode> {

	Page<ActivityQrcodeDto> search(@Param("wechatId") Integer wechatId,
			@Param("activityId") Integer activityId,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

}