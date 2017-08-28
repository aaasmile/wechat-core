package com.d1m.wechat.service;

import com.d1m.wechat.model.ActionEngine;

public interface ActionEngineService extends IService<ActionEngine> {

	ActionEngine get(Integer wechatId, Integer id);

}
