package com.d1m.wechat.mapper;

import java.util.List;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.ReplyWords;
import com.d1m.wechat.util.MyMapper;

public interface ReplyWordsMapper extends MyMapper<ReplyWords> {

	List<String> getReplyWordByReplyId(@Param("replyId") Integer replyId);
}