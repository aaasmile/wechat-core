package com.d1m.wechat.service;

import com.d1m.wechat.dto.InterfaceConfigBrandDto;
import com.d1m.wechat.dto.InterfaceConfigDto;
import com.d1m.wechat.model.InterfaceConfig;
import com.d1m.wechat.model.InterfaceConfigBrand;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface InterfaceConfigService {
	Page<InterfaceConfigDto> selectItems(Map<String, String> query);

	String create(InterfaceConfig interfaceConfig);

	int update(InterfaceConfig interfaceConfig);

	int delete(String id);

	InterfaceConfigDto get(String id);

	List<InterfaceConfigBrandDto> listBrand();

	Map<String, String> createBrand(InterfaceConfigBrand interfaceConfigBrand);

	int updateBrand(InterfaceConfigBrand interfaceConfigBrand);

	int deleteBrand(String id);
}
