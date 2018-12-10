package com.d1m.wechat.service.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d1m.wechat.dao.InterfaceConfigMapper;
import com.d1m.wechat.service.InterfaceConfigService;

@Service
public class InterfaceConfigServiceImpl implements InterfaceConfigService {

	@Autowired
	private InterfaceConfigMapper interfaceConfigMapper;
	@Override
	public List<Map<String, String>> selectItems() {
		return interfaceConfigMapper.selectItems();
	}

}
