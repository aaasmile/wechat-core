package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.BusinessAreaListDto;
import com.d1m.wechat.dto.BusinessDto;
import com.d1m.wechat.model.Business;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface BusinessMapper extends MyMapper<Business> {

	Page<BusinessDto> search(@Param("wechatId") Integer wechatId,
			@Param("status") Byte status, @Param("province") Integer province,
			@Param("city") Integer city, @Param("lng") Double lng,
			@Param("lat") Double lat, @Param("query") String query,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

	BusinessDto get(@Param("wechatId") Integer wechatId, @Param("id") Integer id);

	List<BusinessDto> searchByLngLat(@Param("wechatId") Integer wechatId,
			@Param("lng") Double lng, @Param("lat") Double lat,
			@Param("size") Integer size);

	String searchByBusinessId(@Param("id") Integer id);

	Page<BusinessDto> searchDirect(@Param("wechatId") Integer wechatId, @Param("province") Integer province,
								   @Param("city") Integer city, @Param("sortName") String sortName, @Param("sortDir") String sortDir);

	List<Business> getAll();

	List<BusinessAreaListDto> getProvinceList(
			@Param("wechatId") Integer wechatId);

	List<BusinessAreaListDto> getCityList(@Param("wechatId") Integer wechatId);

}
