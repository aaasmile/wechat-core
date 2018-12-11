package com.d1m.wechat.mapper;

import java.util.Map;

import com.d1m.wechat.dto.InterfaceConfigDto;
import com.d1m.wechat.model.InterfaceConfig;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;
import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceConfigMapper extends MyMapper<InterfaceConfig>{

	Page<InterfaceConfigDto> selectItems(Map<String, String> query);

	InterfaceConfigDto get(String id);
}
