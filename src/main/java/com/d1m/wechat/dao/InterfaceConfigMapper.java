package com.d1m.wechat.dao;

import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Repository;

@Repository
public interface InterfaceConfigMapper {

	public List<Map<String, String>> selectItems();
}
