package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.CompanyMapper;
import com.d1m.wechat.model.Company;
import com.d1m.wechat.service.CompanyService;

@Service
public class CompanyServiceImpl extends BaseService<Company> 
	implements CompanyService{
	
	@Autowired
	private CompanyMapper companyMapper;

	@Override
	public Mapper<Company> getGenericMapper() {
		return companyMapper;
	}

}
