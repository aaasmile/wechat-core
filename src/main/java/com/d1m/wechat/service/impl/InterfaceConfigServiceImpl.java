package com.d1m.wechat.service.impl;

import com.d1m.wechat.common.Constant;
import com.d1m.wechat.dto.InterfaceConfigBrandDto;
import com.d1m.wechat.dto.InterfaceConfigDto;
import com.d1m.wechat.exception.BusinessException;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.InterfaceConfigBrandMapper;
import com.d1m.wechat.mapper.InterfaceConfigMapper;
import com.d1m.wechat.mapper.MenuMapper;
import com.d1m.wechat.model.EventForward;
import com.d1m.wechat.model.InterfaceConfig;
import com.d1m.wechat.model.InterfaceConfigBrand;
import com.d1m.wechat.model.Menu;
import com.d1m.wechat.model.enums.InterfaceStatus;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.service.EventService;
import com.d1m.wechat.service.InterfaceConfigService;
import com.d1m.wechat.util.MD5;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import org.apache.commons.collections.CollectionUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
public class InterfaceConfigServiceImpl implements InterfaceConfigService {
    private Logger logger = Logger.getLogger(getClass());

    @Autowired
    private InterfaceConfigMapper interfaceConfigMapper;

    @Autowired
    private InterfaceConfigBrandMapper interfaceConfigBrandMapper;

    @Autowired
    private MenuMapper menuMapper;

	@Autowired
	private EventService eventService;

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
    @CacheEvict(value = Constant.Cache.THIRD_PARTY_INTERFACE, allEntries = true)
    public int update(InterfaceConfig interfaceConfig) {
        return interfaceConfigMapper.updateByPrimaryKeySelective(interfaceConfig);
    }

    @Override
    @CacheEvict(value = Constant.Cache.THIRD_PARTY_INTERFACE, allEntries = true)
    public int delete(String id) throws WechatException {
        Menu menu = new Menu();
        menu.setMenuKey(Integer.valueOf(id));
        if (menuMapper.selectCount(menu) > 0)
            throw new WechatException(Message.INTERFACECONFIG_IN_USED, Message.INTERFACECONFIG_IN_USED.getName());
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
    public Map<String, String> createBrand(InterfaceConfigBrand interfaceConfigBrand) throws WechatException {
        List<InterfaceConfigBrand> select = interfaceConfigBrandMapper.select(interfaceConfigBrand);
        if (CollectionUtils.isNotEmpty(select))
            throw new WechatException(Message.INTERFACECONFIG_BRAND_EXIST, Message.INTERFACECONFIG_BRAND_EXIST.getName());
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
    @CacheEvict(value = Constant.Cache.THIRD_PARTY_INTERFACE, allEntries = true)
    public int updateBrand(InterfaceConfigBrand interfaceConfigBrand) {
        return interfaceConfigBrandMapper.updateByPrimaryKeySelective(interfaceConfigBrand);
    }

    @Override
    @CacheEvict(value = Constant.Cache.THIRD_PARTY_INTERFACE, allEntries = true)
    public int deleteBrand(String id) throws WechatException {
        InterfaceConfig interfaceConfig = new InterfaceConfig();
        interfaceConfig.setBrand(id);
        if (interfaceConfigMapper.selectCount(interfaceConfig) > 0)
            throw new WechatException(Message.INTERFACECONFIG_BRAND_IN_USED, Message.INTERFACECONFIG_BRAND_IN_USED.getName());
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

        return interfaceConfigMapper.checkRepeatById(id, brand, name);
    }

    @Override
    @Cacheable(value = Constant.Cache.THIRD_PARTY_INTERFACE, key = "#eventCode")
    public List<InterfaceConfigDto> findInterfaceConfigDtoByWxEventCode(String eventCode) {
        return Optional
                .ofNullable(eventCode)
                .map(interfaceConfigMapper::findInterfaceConfigDtoByWxEventCode)
                .orElseThrow(() -> new BusinessException(Message.INTERFACECONFIG_GET_FAIL));
    }

	@Override
	public List<InterfaceConfigDto> getByEventForward(String id) {
		List<InterfaceConfig> interfaceConfigs = interfaceConfigMapper.select(new InterfaceConfig(id));
		if(interfaceConfigs == null || interfaceConfigs.size() == 0) {
			return null;
		}

		List<InterfaceConfigDto> interfaceConfigDtos = new ArrayList<>();

		List<EventForward> eventForwards = eventService.getForwardByThirdPartyId(Integer.parseInt(id));
		if(eventForwards == null || eventForwards.size() == 0) {
			interfaceConfigs.forEach( interfaceConfig -> {
				interfaceConfigDtos.add(new InterfaceConfigDto(interfaceConfig.getId(), interfaceConfig.getName(), interfaceConfig.getName()));
			});
			return interfaceConfigDtos;
		}

		List<String> interfaceIds = eventForwards.stream().map(EventForward::getInterfaceId).collect(Collectors.toList());
		List<InterfaceConfig> newInterfaceConfigs = interfaceConfigs.stream().filter((InterfaceConfig i) -> !interfaceIds.contains(i.getId())).collect(Collectors.toList());
		newInterfaceConfigs.forEach( interfaceConfig -> {
			interfaceConfigDtos.add(new InterfaceConfigDto(interfaceConfig.getId(), interfaceConfig.getName(), interfaceConfig.getName()));
		});
		return interfaceConfigDtos;
	}


    /**
     * 接口启用和停用接口
     *  @param status 状态 0 停用，1 启用
     * @param id
     */
    public void enableOrDisable(InterfaceStatus status, String id) {
        try {
            int t = interfaceConfigMapper.updateStatusById(id, status, DateUtil.formatYYYYMMDDHHMM(new Date()));
        } catch (Exception e) {
            logger.error(e.getMessage());
        }

    }


    /**
     * 检查第三方接口是否存在
     *
     * @param id
     * @return
     */
    public InterfaceConfig checkIsExist(String id) {
        InterfaceConfig interfaceConfig = new InterfaceConfig();
        interfaceConfig.setId(id);
        return interfaceConfigMapper.selectByPrimaryKey(id);
    }

}
