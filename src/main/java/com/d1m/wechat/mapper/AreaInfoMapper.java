package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.AreaInfo;
import com.d1m.wechat.util.MyMapper;

public interface AreaInfoMapper extends MyMapper<AreaInfo> {

	List<AreaInfo> selectByParentId(@Param("parentId") Integer parentId);

	List<AreaInfo> selectByCName(@Param("cName") String cName);

	String selectNameById(@Param("id") Integer id, @Param("lang") String lang);

	Integer selectIdByName(@Param("cName") String cName, 
			@Param("parentId") Integer parentId);

}
