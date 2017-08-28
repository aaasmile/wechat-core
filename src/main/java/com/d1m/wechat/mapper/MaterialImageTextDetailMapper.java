package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.MaterialImageTextDetailDto;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface MaterialImageTextDetailMapper extends
		MyMapper<MaterialImageTextDetail> {

	Page<MaterialImageTextDetailDto> search(
			@Param("wechatId") Integer wechatId,
			@Param("materialId") Integer materialId,
			@Param("query") String query, @Param("sortName") String sortName,
			@Param("sortDir") String sortDir);

}