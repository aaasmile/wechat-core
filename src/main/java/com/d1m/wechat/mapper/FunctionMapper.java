package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.FunctionDto;
import com.d1m.wechat.model.Function;
import com.d1m.wechat.util.MyMapper;

public interface FunctionMapper extends MyMapper<Function> {

	List<FunctionDto> search(@Param("id") Integer id,
			@Param("code") String code, @Param("sortName") String sortName,
			@Param("sortDir") String sortDir);

	List<Integer> getAll();
}
