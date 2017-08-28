package com.d1m.wechat.service;

import com.d1m.wechat.dto.MaterialImageTextDetailDto;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.github.pagehelper.Page;

public interface MaterialImageTextDetailService extends
		IService<MaterialImageTextDetail> {

	Page<MaterialImageTextDetailDto> search(Integer wechatId,
			Integer materialId, String query, String sortName, String sortDir,
			Integer pageNum, Integer pageSize, boolean queryCount);

}
