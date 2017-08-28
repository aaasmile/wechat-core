package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.QrcodeTypeDto;
import com.d1m.wechat.model.QrcodeType;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface QrcodeTypeMapper extends MyMapper<QrcodeType> {

	Integer countDuplicateName(@Param("wechatId") Integer wechatId,
			@Param("id") Integer id, @Param("name") String name,
			@Param("parentId") Integer parentId, @Param("status") byte status);

	Page<QrcodeTypeDto> list(@Param("wechatId") Integer wechatId,
			@Param("name") String name, @Param("parentId") Integer parentId,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

}