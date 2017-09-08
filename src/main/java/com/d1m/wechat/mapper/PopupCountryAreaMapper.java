package com.d1m.wechat.mapper;

import com.d1m.wechat.model.popup.dao.PopupCountryAreaDao;
import com.d1m.wechat.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PopupCountryAreaMapper extends MyMapper<PopupCountryAreaDao> {

    List<PopupCountryAreaDao> selectAreaByLevel(@Param("level") int level);


}