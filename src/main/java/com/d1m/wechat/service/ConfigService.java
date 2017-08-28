package com.d1m.wechat.service;

import com.alibaba.fastjson.JSONArray;
import com.d1m.wechat.dto.ConfigDto;
import com.d1m.wechat.model.Config;

import java.util.List;

public interface ConfigService extends IService<Config> {
	public Config getConfig(Integer wechatId, String group, String key);

	public ConfigDto getConfigDto(Integer wechatId, String group, String key);

	public List<ConfigDto> getConfigDtoList(Integer wechatId, String group);
	
	public String getConfigValue(Integer wechatId, String group, String key);

	public void update(JSONArray jsonArray);
}
