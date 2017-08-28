package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.OfflineActivityBusiness;
import com.d1m.wechat.util.MyMapper;

public interface OfflineActivityBusinessMapper extends MyMapper<OfflineActivityBusiness> {

	void deleteBusinessList(@Param("offlineActivityId") Integer offlineActivityId,
			@Param("businessIds") List<Integer> businessIds);

	List<Integer> selectByOfflineActivityId(@Param("offlineActivityId") Integer offlineActivityId);
}