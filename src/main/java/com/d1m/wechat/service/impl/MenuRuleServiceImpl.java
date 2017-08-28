package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.MenuRuleMapper;
import com.d1m.wechat.model.MenuRule;
import com.d1m.wechat.service.MenuRuleService;

@Service
public class MenuRuleServiceImpl extends BaseService<MenuRule> implements
		MenuRuleService {

	@Autowired
	private MenuRuleMapper menuRuleMapper;

	@Override
	public Mapper<MenuRule> getGenericMapper() {
		return menuRuleMapper;
	}

}
