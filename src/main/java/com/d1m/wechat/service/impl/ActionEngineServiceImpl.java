package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.ActionEngineMapper;
import com.d1m.wechat.model.ActionEngine;
import com.d1m.wechat.service.ActionEngineService;

@Service
public class ActionEngineServiceImpl extends BaseService<ActionEngine>
		implements ActionEngineService {

	@Autowired
	private ActionEngineMapper actionEngineMapper;

	public void setActionEngineMapper(ActionEngineMapper actionEngineMapper) {
		this.actionEngineMapper = actionEngineMapper;
	}

	@Override
	public Mapper<ActionEngine> getGenericMapper() {
		return actionEngineMapper;
	}

	@Override
	public ActionEngine get(Integer wechatId, Integer id) {
		ActionEngine record = new ActionEngine();
		record.setWechatId(wechatId);
		record.setId(id);
		return actionEngineMapper.selectOne(record);
	}

}
