package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.dto.ReplyDto;
import com.d1m.wechat.model.Reply;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface ReplyMapper extends MyMapper<Reply> {

	Page<ReplyDto> search(@Param("wechatId") Integer wechatId, @Param("query") String query,
			@Param("sortName") String sortName, @Param("sortDir") String sortDir);

	ReplyDto get(@Param("wechatId") Integer wechatId,
			@Param("replyId") Integer replyId);

	ReplyDto getSubscribeReply(@Param("wechatId") Integer wechatId);

	List<ReplyDto> searchMatchReply(@Param("wechatId") Integer wechatId,
			@Param("content") String content);

	ReplyDto getDefaultReply(@Param("wechatId") Integer wechatId);
}
