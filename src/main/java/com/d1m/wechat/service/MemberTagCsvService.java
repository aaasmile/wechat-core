package com.d1m.wechat.service;

import com.d1m.wechat.model.MemberTagCsv;
import com.d1m.wechat.pamametermodel.AddMemberTagTaskModel;
import com.github.pagehelper.Page;

public interface MemberTagCsvService extends IService<MemberTagCsv>{

	MemberTagCsv selectByTaskName(String taskName);

	Page<MemberTagCsv> searchTask(Integer wechatId,
			AddMemberTagTaskModel tagTask, boolean queryCount);

}
