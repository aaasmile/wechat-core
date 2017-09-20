package com.d1m.wechat.mapper;

import com.d1m.wechat.util.MyMapper;
import com.d1m.wechat.model.popup.PopupCountryArea;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface PopupCountryAreaMapper extends MyMapper<PopupCountryArea> {

    List<PopupCountryArea> selectAreaByLevel(@Param("level") int level);


}