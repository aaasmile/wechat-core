package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.BusinessItemDto;
import com.d1m.wechat.dto.OfflineActivityDto;
import com.d1m.wechat.model.OfflineActivity;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface OfflineActivityMapper extends MyMapper<OfflineActivity> {

	Page<OfflineActivityDto> search(@Param("wechatId") Integer wechatId, 
			@Param("query") String query, @Param("activityStatus") Integer activityStatus, 
			@Param("status") Integer status, @Param("sortName") String sortName, 
			@Param("sortDir") String sortDir);

	OfflineActivityDto get(@Param("wechatId") Integer wechatId, @Param("id") Integer id);

	List<BusinessItemDto> getVisibleBusiness(@Param("wechatId") Integer wechatId, 
			@Param("query") String query);
}