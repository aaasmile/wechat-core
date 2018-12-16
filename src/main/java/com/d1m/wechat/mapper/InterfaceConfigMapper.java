package com.d1m.wechat.mapper;

import com.d1m.wechat.dto.InterfaceConfigDto;
import com.d1m.wechat.model.InterfaceConfig;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

import java.util.Map;

public interface InterfaceConfigMapper extends MyMapper<InterfaceConfig>{

    Page<InterfaceConfigDto> selectItems(Map<String, String> query);

    InterfaceConfigDto get(String id);

	InterfaceConfigDto getSecret(String id);
}