package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.CustomerServiceConfigDto;
import com.d1m.wechat.model.CustomerServiceConfig;
import com.d1m.wechat.util.MyMapper;

public interface CustomerServiceConfigMapper extends
		MyMapper<CustomerServiceConfig> {

	List<CustomerServiceConfigDto> search(@Param("wechatId") Integer wechatId,
			@Param("group") String group);

}