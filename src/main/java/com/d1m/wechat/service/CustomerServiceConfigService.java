package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.CustomerServiceConfigDto;
import com.d1m.wechat.model.CustomerServiceConfig;
import com.d1m.wechat.pamametermodel.CustomerServiceConfigModel;

public interface CustomerServiceConfigService extends
		IService<CustomerServiceConfig> {

	public List<CustomerServiceConfigDto> search(Integer wechatId, String group);

	boolean updateGroup(Integer wechatId, List<CustomerServiceConfigModel> list);

	boolean update(Integer wechatId, CustomerServiceConfigModel model);

	boolean delete(Integer wechatId, Integer id);

	int add(CustomerServiceConfigModel customerServiceConfigModel, Integer wechatId);
}
