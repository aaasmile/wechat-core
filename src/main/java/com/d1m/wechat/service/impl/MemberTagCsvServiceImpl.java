package com.d1m.wechat.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import tk.mybatis.mapper.common.Mapper;

import com.d1m.wechat.mapper.MemberTagCsvMapper;
import com.d1m.wechat.model.MemberTagCsv;
import com.d1m.wechat.pamametermodel.AddMemberTagTaskModel;
import com.d1m.wechat.service.MemberTagCsvService;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

@Service
public class MemberTagCsvServiceImpl extends BaseService<MemberTagCsv> implements MemberTagCsvService{

	@Autowired
	private MemberTagCsvMapper memberTagCsvMapper;
	
	@Override
	public Mapper<MemberTagCsv> getGenericMapper() {
		// TODO Auto-generated method stub
		return memberTagCsvMapper;
	}

	@Override
	public MemberTagCsv selectByTaskName(String taskName) {
		return memberTagCsvMapper.selectByTaskName(taskName);
	}
	
	@Override
	public Page<MemberTagCsv> searchTask(Integer wechatId,
			AddMemberTagTaskModel tagTask, boolean queryCount) {
		if (tagTask.pagable()) {
			PageHelper.startPage(tagTask.getPageNum(),
					tagTask.getPageSize(), queryCount);
		}
		return memberTagCsvMapper.searchTask(wechatId, tagTask.getStatus(), 
				tagTask.getStart(), tagTask.getEnd(), tagTask.getSortName(),
				tagTask.getSortDir());
	}

}
