package com.d1m.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.AreaInfoMapper;
import com.d1m.wechat.model.AreaInfo;
import com.d1m.wechat.service.AreaInfoService;

@Service
public class AreaInfoServiceImpl extends BaseService<AreaInfo> implements
		AreaInfoService {

	@Autowired
	AreaInfoMapper areaInfoMapper;

	public void setAreaInfoMapper(AreaInfoMapper areaInfoMapper) {
		this.areaInfoMapper = areaInfoMapper;
	}

	@Override
	public Mapper<AreaInfo> getGenericMapper() {
		return areaInfoMapper;
	}

	@Override
	public List<AreaInfo> selectAll() {
		return areaInfoMapper.selectAll();
	}

	@Override
	public List<AreaInfo> selectByParentId(Integer parentId) {
		return areaInfoMapper.selectByParentId(parentId);
	}

	@Override
	public AreaInfo getByCName(String cName) {
		List<AreaInfo> areaInfos = areaInfoMapper.selectByCName(cName);
		return areaInfos.isEmpty() ? null : areaInfos.get(0);
	}

	@Override
	public String selectNameById(int area) {
		return areaInfoMapper.selectNameById(area, null);
	}

	@Override
	public String selectNameById(int area, String lang) {
		return areaInfoMapper.selectNameById(area, lang);
	}

}
