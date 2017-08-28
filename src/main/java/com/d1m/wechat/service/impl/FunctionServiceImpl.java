package com.d1m.wechat.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.dto.FunctionDto;
import com.d1m.wechat.mapper.FunctionMapper;
import com.d1m.wechat.model.Function;
import com.d1m.wechat.pamametermodel.FunctionModel;
import com.d1m.wechat.service.FunctionService;
import com.d1m.wechat.util.FunctionTreeBuilder;


@Service
public class FunctionServiceImpl extends BaseService<Function> implements FunctionService{
	
	@Autowired
	FunctionMapper FunctionMapper;

	@Override
	public List<FunctionDto> search(FunctionModel functionModel) {
		
		List<FunctionDto> functionDtos = FunctionMapper.search(functionModel.getId(), functionModel.getCode(),
				functionModel.getSortName(), functionModel.getSortDir());
		
		return new FunctionTreeBuilder(functionDtos).buildJSONTree();
	}

	@Override
	public Mapper<Function> getGenericMapper() {
		// TODO Auto-generated method stub
		return FunctionMapper;
	}
	

}
