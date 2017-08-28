package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.MaterialTextDetailMapper;
import com.d1m.wechat.model.MaterialTextDetail;
import com.d1m.wechat.service.MaterialTextDetailService;

@Service
public class MaterialTextDetailServiceImpl extends BaseService<MaterialTextDetail> 
	implements MaterialTextDetailService{
	
	@Autowired
	private MaterialTextDetailMapper materialTextDetailMapper;

	@Override
	public Mapper<MaterialTextDetail> getGenericMapper() {
		return materialTextDetailMapper;
	}

	@Override
	public MaterialTextDetail search(Integer wechatId, Integer materialId) {
		MaterialTextDetail materialTextDetail = new MaterialTextDetail();
		materialTextDetail.setWechatId(wechatId);
		materialTextDetail.setMaterialId(materialId);
		return materialTextDetailMapper.selectOne(materialTextDetail);
	}
	
	

}
