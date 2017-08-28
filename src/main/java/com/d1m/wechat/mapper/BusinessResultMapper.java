package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.BusinessResult;
import com.d1m.wechat.util.MyMapper;

public interface BusinessResultMapper extends MyMapper<BusinessResult> {

	BusinessResult searchByUniqId(@Param("sid") String sid);
}