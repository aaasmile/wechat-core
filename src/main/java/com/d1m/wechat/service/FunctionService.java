package com.d1m.wechat.service;

import java.util.List;

import com.d1m.wechat.dto.FunctionDto;
import com.d1m.wechat.model.Function;
import com.d1m.wechat.pamametermodel.FunctionModel;

public interface FunctionService extends IService<Function>{

	List<FunctionDto> search(FunctionModel functionModel);
	

}
