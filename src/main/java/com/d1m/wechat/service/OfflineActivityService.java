package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.BusinessItemDto;
import com.d1m.wechat.dto.OfflineActivityDto;
import com.d1m.wechat.model.OfflineActivity;
import com.d1m.wechat.pamametermodel.OfflineActivityModel;
import com.github.pagehelper.Page;

public interface OfflineActivityService extends IService<OfflineActivity>{

	Page<OfflineActivityDto> search(Integer wechatId, OfflineActivityModel model,
			boolean queryCount);

	OfflineActivity insert(Integer wechatId, Integer userId,
			OfflineActivityModel model);

	OfflineActivityDto get(Integer wechatId, Integer id);

	int update(Integer wechatId, Integer userId, Integer id, OfflineActivityModel model);

	int deleteById(Integer wechatId, Integer id);

	List<BusinessItemDto> getBusiness(Integer wechatId, String query);

}
