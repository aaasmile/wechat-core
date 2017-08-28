package com.d1m.wechat.service;

import com.d1m.wechat.model.MaterialTextDetail;

public interface MaterialTextDetailService extends IService<MaterialTextDetail>{

	MaterialTextDetail search(Integer wechatId, Integer materialId);
	
}
