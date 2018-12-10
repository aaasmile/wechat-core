package com.d1m.wechat.mapper;

import com.d1m.wechat.dto.InterfaceConfigBrandDto;
import com.d1m.wechat.model.InterfaceConfigBrand;
import org.apache.ibatis.annotations.Select;
import tk.mybatis.mapper.common.BaseMapper;

import java.util.List;

/**
 * Auth: Jo.Ho
 * Date: 2018/12/9
 */
public interface InterfaceConfigBrandMapper extends BaseMapper<InterfaceConfigBrand>{

	List<InterfaceConfigBrandDto> listBrand();
}
