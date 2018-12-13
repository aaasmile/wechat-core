package com.d1m.wechat.mapper;

import com.d1m.wechat.dto.InterfaceConfigBrandDto;
import com.d1m.wechat.model.InterfaceConfigBrand;
import com.d1m.wechat.util.MyMapper;

import java.util.List;

/**
 * Auth: Jo.Ho
 * Date: 2018/12/9
 */
public interface InterfaceConfigBrandMapper extends MyMapper<InterfaceConfigBrand> {

    List<InterfaceConfigBrandDto> listBrand();
}
