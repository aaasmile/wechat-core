package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.MaterialImageType;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface MaterialImageTypeMapper extends MyMapper<MaterialImageType> {

	Page<MaterialImageType> search(@Param("wechatId") Integer wechatId,
			@Param("name") String name, @Param("sortName") String sortName,
			@Param("sortDir") String sortDir);

}