package com.d1m.wechat.service;

import com.d1m.wechat.domain.entity.MemberTagCsv;

public interface MemberTagCsvService extends BaseService<MemberTagCsv> {

    MemberTagCsv selectByTaskName(String taskName);

//    Page<MemberTagCsv> searchTask(Integer wechatId,
//                                  AddMemberTagTaskModel tagTask, boolean queryCount);

}
