package com.d1m.wechat.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.alibaba.fastjson.JSON;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.CustomerServiceConfigDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.CustomerServiceConfigMapper;
import com.d1m.wechat.model.CustomerServiceConfig;
import com.d1m.wechat.pamametermodel.CustomerServiceConfigModel;
import com.d1m.wechat.service.CustomerServiceConfigService;
import com.d1m.wechat.util.Message;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

@Slf4j
@Service
public class CustomerServiceConfigServiceImpl extends
		BaseService<CustomerServiceConfig> implements
		CustomerServiceConfigService {

	public static final String QUICK_REPLY = "QUICK_REPLY";

	@Autowired
	private CustomerServiceConfigMapper customerServiceConfigMapper;

	public void setCustomerServiceConfigMapper(
			CustomerServiceConfigMapper customerServiceConfigMapper) {
		this.customerServiceConfigMapper = customerServiceConfigMapper;
	}

	@Override
	public Mapper<CustomerServiceConfig> getGenericMapper() {
		return customerServiceConfigMapper;
	}

	private CustomerServiceConfig get(Integer wechatId, Integer id) {
		CustomerServiceConfig record = new CustomerServiceConfig();
		record.setWechatId(wechatId);
		record.setId(id);
		return customerServiceConfigMapper.selectOne(record);
	}

	@Override
    public boolean updateGroup(Integer wechatId,
                               List<CustomerServiceConfigModel> list) {
		if (list == null || list.isEmpty()) {
			throw new WechatException(Message.CUSTOMER_SERVICE_CONFIG_NOT_BLANK);
		}
        for (CustomerServiceConfigModel model : list) {
            update(wechatId, model);
        }

        return true;
	}

	private void checkKeyRepeat(List<CustomerServiceConfigModel> list) {
		List<String> keys = new ArrayList<String>();
		String key = null;
		for (CustomerServiceConfigModel customerServiceConfigModel : list) {
			key = customerServiceConfigModel.getKey();
			if (StringUtils.isBlank(key)) {
				continue;
			}
			if (keys.contains(key)) {
				throw new WechatException(
						Message.CUSTOMER_SERVICE_CONFIG_KEY_REPEAT);
			}
			keys.add(key);
		}
	}

	@Override
	public List<CustomerServiceConfigDto> search(Integer wechatId, String group) {
		return customerServiceConfigMapper.search(wechatId, group);
	}

	@Override
	public boolean delete(Integer wechatId, Integer id) {
		CustomerServiceConfig customerServiceConfig = get(wechatId, id);
		notBlank(customerServiceConfig,
				Message.CUSTOMER_SERVICE_CONFIG_NOT_EXIST);
		return customerServiceConfigMapper.delete(customerServiceConfig) > 0;
	}

    @Override
    public int add(CustomerServiceConfigModel customerServiceConfigModel, Integer wechatId) {
        if (customerServiceConfigModel == null) {
            customerServiceConfigModel = new CustomerServiceConfigModel();
        }
        CustomerServiceConfig csc = new CustomerServiceConfig();
        csc.setWechatId(wechatId);
        csc.setCfgGroup(customerServiceConfigModel.getGroup());
        csc.setCfgGroupLabel(customerServiceConfigModel.getGroupLabel());
        csc.setCfgKey(customerServiceConfigModel.getKey());
        csc.setCfgKeyLabel(customerServiceConfigModel.getKeyLabel());
        csc.setCfgValue(customerServiceConfigModel.getValue());
        customerServiceConfigMapper.insertUseGeneratedKeys(csc);
        return csc.getId();
    }

    @Override
    public boolean update(Integer wechatId, CustomerServiceConfigModel model) {
        CustomerServiceConfig csc = new CustomerServiceConfig();
        csc.setWechatId(wechatId);
        csc.setCfgGroup(model.getGroup());
        csc.setCfgGroupLabel(model.getGroupLabel());
        csc.setCfgKey(model.getKey());
        csc.setCfgKeyLabel(model.getKeyLabel());
        csc.setCfgValue(model.getValue());

        if (model.getId() == null) {
            CustomerServiceConfig query = new CustomerServiceConfig();
            query.setCfgGroup(model.getGroup());
            query.setCfgKey(model.getKey());
            query.setWechatId(wechatId);
            List<CustomerServiceConfig> cscList = customerServiceConfigMapper.select(query);
            if (!cscList.isEmpty()) {
                model.setId(cscList.get(0).getId());
                if (cscList.size() > 1) {
                    log.warn("发现重复记录! 查询条件: {}", JSON.toJSONString(query));
                }
            }
        }

        if (model.getId() == null) {
            customerServiceConfigMapper.insert(csc);
        } else {
            csc.setId(model.getId());
            customerServiceConfigMapper.updateByPrimaryKeySelective(csc);
        }
        return true;
    }
}
