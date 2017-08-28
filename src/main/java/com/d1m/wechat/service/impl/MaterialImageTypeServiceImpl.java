package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.MaterialImageTypeMapper;
import com.d1m.wechat.model.MaterialImageType;
import com.d1m.wechat.service.MaterialImageTypeService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class MaterialImageTypeServiceImpl extends
		BaseService<MaterialImageType> implements MaterialImageTypeService {

	@Autowired
	private MaterialImageTypeMapper materialImageTypeMapper;

	public void setMaterialImageTypeMapper(
			MaterialImageTypeMapper materialImageTypeMapper) {
		this.materialImageTypeMapper = materialImageTypeMapper;
	}

	@Override
	public Mapper<MaterialImageType> getGenericMapper() {
		return materialImageTypeMapper;
	}

	@Override
	public Page<MaterialImageType> search(Integer wechatId, String name,
			String sortName, String sortDir, Integer pageNum, Integer pageSize,
			boolean queryCount) {
		PageHelper.startPage(pageNum, pageSize, queryCount);
		return materialImageTypeMapper
				.search(wechatId, name, sortName, sortDir);
	}

}
