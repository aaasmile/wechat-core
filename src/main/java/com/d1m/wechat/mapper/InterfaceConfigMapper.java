package com.d1m.wechat.mapper;


import com.d1m.wechat.dto.InterfaceConfigDto;

import com.d1m.wechat.model.InterfaceConfig;
import com.d1m.wechat.model.enums.InterfaceStatus;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

public interface InterfaceConfigMapper extends MyMapper<InterfaceConfig>{

    Page<InterfaceConfigDto> selectItems(Map<String, String> query);

    InterfaceConfigDto get(String id);

	InterfaceConfigDto getSecret(String id);


    int checkRepeatById(
            @Param("id")String id,
            @Param("brand")String brand,
            @Param("name")String name
            );

    /**
     * 更新状态为启用和停用
     * @param id
     * @param status
     * @param updatedAt
     * @return
     */
    int updateStatusById(@Param("id") String id, @Param("status") InterfaceStatus status, @Param("updatedAt") String  updatedAt);

}