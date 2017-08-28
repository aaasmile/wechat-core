package com.d1m.wechat.mapper;

import java.util.List;

import com.d1m.wechat.dto.BusinessCategoryDto;
import com.d1m.wechat.model.BusinessCategory;
import com.d1m.wechat.util.MyMapper;

public interface BusinessCategoryMapper extends MyMapper<BusinessCategory> {

	List<BusinessCategoryDto> list();

	/*Integer countDuplicateName(
			@Param("id") Integer id, @Param("name") String name,
			@Param("parentId") Integer parentId, @Param("status") byte status);

	Page<BusinessCategoryDto> list(
			@Param("name") String name, @Param("parentId") Integer parentId,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);*/

}