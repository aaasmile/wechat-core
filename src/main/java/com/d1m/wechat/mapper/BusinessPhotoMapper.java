package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.BusinessPhoto;
import com.d1m.wechat.util.MyMapper;

public interface BusinessPhotoMapper extends MyMapper<BusinessPhoto> {

	BusinessPhoto searchLike(@Param("str") String str);
}