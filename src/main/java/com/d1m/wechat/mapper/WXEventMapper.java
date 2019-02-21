package com.d1m.wechat.mapper;

import com.d1m.wechat.model.WXEvent;
import com.d1m.wechat.util.MyMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WXEventMapper extends MyMapper<WXEvent> {

    List<String> selectEventItmes(@Param("id") Integer id);
}
