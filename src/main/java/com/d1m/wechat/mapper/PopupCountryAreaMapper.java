package com.d1m.wechat.mapper;

import com.d1m.wechat.model.popup.dao.PopupCountryArea;
import com.d1m.wechat.util.MyMapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.List;

@Component
public interface PopupCountryAreaMapper extends MyMapper<PopupCountryArea> {

    List<PopupCountryArea> selectAreaByLevel(@Param("level") int level);

    List<HashMap<String,String>> selectAllAreaMap(@Param("codes") String codes);
}