package com.d1m.wechat.service;

import com.d1m.wechat.model.MaterialImageType;
import com.github.pagehelper.Page;

public interface MaterialImageTypeService extends IService<MaterialImageType> {

	Page<MaterialImageType> search(Integer wechatId, String name,
			String sortName, String sortDir, Integer pageNum, Integer pageSize,
			boolean queryCount);

}
