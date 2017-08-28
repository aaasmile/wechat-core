package com.d1m.wechat.mapper;

import java.util.Date;

import org.apache.ibatis.annotations.Param;

import com.d1m.wechat.model.MemberTagCsv;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

public interface MemberTagCsvMapper extends MyMapper<MemberTagCsv> {

	MemberTagCsv selectByTaskName(@Param("taskName") String taskName);

	Page<MemberTagCsv> searchTask(@Param("wechatId") Integer wechatId, @Param("status") Integer status, 
			@Param("start") Date start, @Param("end") Date end, @Param("sortName") String sortName,
			@Param("sortDir") String sortDir);
}