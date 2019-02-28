package com.d1m.wechat.mapper;

import com.d1m.wechat.model.ActionEngine;
import com.d1m.wechat.util.MyMapper;
import org.apache.ibatis.annotations.Param;

public interface ActionEngineMapper extends MyMapper<ActionEngine> {

    ActionEngine queryByQrcodeId(@Param("qrcodeId") Integer qrcodeId);
}