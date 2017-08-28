package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface MaterialMapper extends MyMapper<Material> {

	MaterialDto getImageText(@Param("wechatId") Integer wechatId,
			@Param("id") Integer id);

	Page<MaterialDto> searchImage(@Param("wechatId") Integer wechatId,
			@Param("materialImageTypeId") Integer materialImageTypeId,
			@Param("query") String query, @Param("pushed") Boolean pushed,
			@Param("materialType") Integer materialType);

	Page<MaterialDto> searchImageText(@Param("wechatId") Integer wechatId,
			@Param("query") String query, @Param("pushed") Boolean pushed);

}