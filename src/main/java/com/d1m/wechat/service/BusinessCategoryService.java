package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.BusinessCategoryDto;
import com.d1m.wechat.model.BusinessCategory;

public interface BusinessCategoryService  extends IService<BusinessCategory>{

	List<BusinessCategoryDto> list();

	/*public BusinessCategory create(String name,
			Integer parentId) throws WechatException;

	public BusinessCategory update(Integer id, String name)
			throws WechatException;

	public Page<BusinessCategoryDto> list(String name,
			Integer parentId, String sortName, String sortDir,
			Integer pageSize, Integer pageNum);*/
}
