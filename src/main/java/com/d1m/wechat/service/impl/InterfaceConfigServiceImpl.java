package com.d1m.wechat.service.impl;

import java.util.*;

import com.d1m.wechat.dto.InterfaceConfigBrandDto;
import com.d1m.wechat.dto.InterfaceConfigDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.InterfaceConfigBrandMapper;
import com.d1m.wechat.mapper.MenuMapper;
import com.d1m.wechat.model.InterfaceConfig;
import com.d1m.wechat.model.InterfaceConfigBrand;
import com.d1m.wechat.model.Menu;
import com.d1m.wechat.util.MD5;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.d1m.wechat.mapper.InterfaceConfigMapper;
import com.d1m.wechat.service.InterfaceConfigService;

@Service
public class InterfaceConfigServiceImpl implements InterfaceConfigService {

	@Autowired
	private InterfaceConfigMapper interfaceConfigMapper;

	@Autowired
	private InterfaceConfigBrandMapper interfaceConfigBrandMapper;

	@Autowired
	private MenuMapper menuMapper;

	@Override
	public Page<InterfaceConfigDto> selectItems(Map<String, String> query) {
		return interfaceConfigMapper.selectItems(query);

	}

	@Override
	public String create(InterfaceConfig interfaceConfig) {

		String id = UUID.randomUUID().toString().replaceAll("-", "");
		interfaceConfig.setId(id);
		interfaceConfigMapper.insertSelective(interfaceConfig);
		return id;

	}

	@Override
	public int update(InterfaceConfig interfaceConfig) {
		return interfaceConfigMapper.updateByPrimaryKeySelective(interfaceConfig);
	}

	@Override
	public int delete(String id) throws WechatException {
		Menu menu = new Menu();
		menu.setMenuKey(Integer.valueOf(id));
		if (menuMapper.selectCount(menu) > 0) throw new WechatException(Message.INTERFACECONFIG_IN_USED, Message.INTERFACECONFIG_IN_USED.getName());
		InterfaceConfig interfaceConfig = new InterfaceConfig();
		interfaceConfig.setId(id);
		interfaceConfig.setDeleted(true);
		return interfaceConfigMapper.updateByPrimaryKeySelective(interfaceConfig);
	}

	@Override
	public InterfaceConfigDto get(String id) {
		return interfaceConfigMapper.get(id);
	}

	@Override
	public List<InterfaceConfigBrandDto> listBrand() {
		return interfaceConfigBrandMapper.listBrand();
	}

	@Override
	public Map<String, String> createBrand(InterfaceConfigBrand interfaceConfigBrand) throws WechatException{
		List<InterfaceConfigBrand> select = interfaceConfigBrandMapper.select(interfaceConfigBrand);
		if (CollectionUtils.isNotEmpty(select)) throw new WechatException(Message.INTERFACECONFIG_BRAND_EXIST, Message.INTERFACECONFIG_BRAND_EXIST.getName());
		String key = UUID.randomUUID().toString().replaceAll("-", "");
		String secret = MD5.MD5Encode(key + interfaceConfigBrand.getName());
		interfaceConfigBrand.setKey(key);
		interfaceConfigBrand.setSecret(secret);
		interfaceConfigBrandMapper.insertSelective(interfaceConfigBrand);
		Map<String, String> result = new HashMap<>();
		result.put("key", key);
		result.put("secret", secret);
		return result;
	}

	@Override
	public int updateBrand(InterfaceConfigBrand interfaceConfigBrand) {
		return interfaceConfigBrandMapper.updateByPrimaryKeySelective(interfaceConfigBrand);
	}

	@Override
	public int deleteBrand(String id) throws WechatException{
		InterfaceConfig interfaceConfig = new InterfaceConfig();
		interfaceConfig.setBrand(id);
		if (interfaceConfigMapper.selectCount(interfaceConfig) > 0) throw new WechatException(Message.INTERFACECONFIG_BRAND_IN_USED, Message.INTERFACECONFIG_BRAND_IN_USED.getName());
		InterfaceConfigBrand interfaceConfigBrand = new InterfaceConfigBrand();
		interfaceConfigBrand.setId(Long.valueOf(id));
		interfaceConfigBrand.setDeleted(true);
		return interfaceConfigBrandMapper.updateByPrimaryKeySelective(interfaceConfigBrand);
	}

	@Override
	public InterfaceConfigDto getSecret(String id) {
		return interfaceConfigMapper.getSecret(id);
	}
    //檢查name重復
    @Override
    public int checkRepeat(InterfaceConfig ifcf) {
        return interfaceConfigMapper.selectCount(ifcf);
    }

	@Override
	public int checkRepeatById(String id, String brand, String name) {

		return interfaceConfigMapper.checkRepeatById(id,brand,name);
	}


}
