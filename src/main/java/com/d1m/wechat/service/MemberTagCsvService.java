package com.d1m.wechat.service;

import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.pamametermodel.AddMemberTagTaskModel;
import com.github.pagehelper.Page;

public interface MemberTagCsvService extends BaseService<MemberTagCsv> {

    MemberTagCsv selectByTaskName(String taskName);

    /**
     * 查询任务列表接口
     * @param wechatId
     * @param tagTask
     * @return
     */
    Page<MemberTagCsv> searchTask(Integer wechatId, AddMemberTagTaskModel tagTask);
}
