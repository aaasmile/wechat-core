package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.ReplyWordsMapper;
import com.d1m.wechat.model.ReplyWords;
import com.d1m.wechat.service.ReplyWordsService;

public class ReplyWordsServiceImpl extends BaseService<ReplyWords> implements 
	ReplyWordsService{
	
	@Autowired
	private ReplyWordsMapper replyWordsMapper;

	@Override
	public Mapper<ReplyWords> getGenericMapper() {
		return replyWordsMapper;
	}

}
