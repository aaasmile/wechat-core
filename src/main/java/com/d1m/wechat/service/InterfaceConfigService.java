package com.d1m.wechat.service;

import com.d1m.wechat.dto.InterfaceConfigBrandDto;
import com.d1m.wechat.dto.InterfaceConfigDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.InterfaceConfig;
import com.d1m.wechat.model.InterfaceConfigBrand;
import com.d1m.wechat.model.enums.InterfaceStatus;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface InterfaceConfigService {
    Page<InterfaceConfigDto> selectItems(Map<String, String> query);

    String create(InterfaceConfig interfaceConfig);

    int update(InterfaceConfig interfaceConfig);

    int delete(String id) throws WechatException;

    InterfaceConfigDto get(String id);

    List<InterfaceConfigBrandDto> listBrand();

    //Map<String, String> createBrand(InterfaceConfigBrand interfaceConfigBrand) throws WechatException;
     void createBrand(InterfaceConfigBrand interfaceConfigBrand);

    int updateBrand(InterfaceConfigBrand interfaceConfigBrand);

    int deleteBrand(String brand) throws WechatException;

    InterfaceConfigDto getSecret(String id);

    //檢查name是否重復
    int checkRepeat(InterfaceConfig ifcf);

    int checkRepeatById(String id, String brand, String name);

    List<InterfaceConfigDto> findInterfaceConfigDtoByWxEventCode(String eventCode);

    String currentTenant();

    List<InterfaceConfigDto> getByEventForward(String id);

    /**
     * 接口启用和停用接口
     *
     * @param status
     * @param id
     */
    void enableOrDisable(InterfaceStatus status, String id);

    /**
     * 检查第三方接口是否存在
     *
     * @param id
     * @return
     */
    InterfaceConfig checkIsExist(String id);

    int findByIdAndDeleted(String brand);


}
