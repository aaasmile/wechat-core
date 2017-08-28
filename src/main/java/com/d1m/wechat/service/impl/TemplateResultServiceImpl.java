package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.TemplateResultMapper;
import com.d1m.wechat.model.TemplateResult;
import com.d1m.wechat.service.TemplateResultService;

@Service
public class TemplateResultServiceImpl extends BaseService<TemplateResult> implements TemplateResultService{

	@Autowired
	private TemplateResultMapper templateResultMapper;
	
	@Override
	public Mapper<TemplateResult> getGenericMapper() {
		// TODO Auto-generated method stub
		return templateResultMapper;
	}

}
