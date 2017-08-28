package com.d1m.wechat.mapper;

import com.d1m.wechat.dto.ConfigDto;
import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.Config;
import com.d1m.wechat.util.MyMapper;

import java.util.List;

public interface ConfigMapper extends MyMapper<Config> {
	Config getConfig(@Param("wechatId") Integer wechatId, 
			@Param("group") String group,
			@Param("key") String key);

	ConfigDto getConfigDto(@Param("wechatId") Integer wechatId,
						   @Param("group") String group,
						   @Param("key") String key);

	List<ConfigDto> getConfigDtoList(@Param("wechatId") Integer wechatId,
						  @Param("group") String group);
}