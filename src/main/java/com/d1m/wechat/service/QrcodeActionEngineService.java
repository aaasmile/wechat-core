package com.d1m.wechat.service;

import com.d1m.wechat.dto.QrcodeActionEngineDto;
import com.d1m.wechat.model.QrcodeActionEngine;
import com.d1m.wechat.model.User;
import com.d1m.wechat.pamametermodel.ActionEngineModel;
import com.d1m.wechat.pamametermodel.QrcodeModel;
import com.github.pagehelper.Page;

public interface QrcodeActionEngineService extends IService<QrcodeActionEngine> {

	QrcodeActionEngine create(Integer wechatId, User user,
			QrcodeModel qrcodeModel);

	void update(Integer wechatId, User user, QrcodeModel qrcodeModel);

	void delete(Integer wechatId, User user, Integer qrcodeActionEngineId);

	Page<QrcodeActionEngineDto> search(Integer wechatId,
			ActionEngineModel actionEngineModel, boolean queryCount);

}
