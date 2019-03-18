package com.d1m.wechat.mapper;

import com.d1m.wechat.model.WxEvent;
import com.d1m.wechat.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WxEventMapper extends MyMapper<WxEvent> {

    List<String> selectEventItmes(@Param("id") Integer id);
}
