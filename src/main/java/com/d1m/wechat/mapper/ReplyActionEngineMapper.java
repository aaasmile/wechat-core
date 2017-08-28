package com.d1m.wechat.mapper;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.ReplyActionEngineDto;
import com.d1m.wechat.model.ReplyActionEngine;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface ReplyActionEngineMapper extends MyMapper<ReplyActionEngine> {

	Page<ReplyActionEngineDto> search(@Param("wechatId") Integer wechatId,
			@Param("replyId") Integer replyId,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

}