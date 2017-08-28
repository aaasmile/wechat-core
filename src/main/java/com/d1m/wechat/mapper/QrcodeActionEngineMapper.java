package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.QrcodeActionEngineDto;
import com.d1m.wechat.model.QrcodeActionEngine;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface QrcodeActionEngineMapper extends MyMapper<QrcodeActionEngine> {

	Page<QrcodeActionEngineDto> search(@Param("wechatId") Integer wechatId,
			@Param("qrcodeId") Integer qrcodeId,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

}