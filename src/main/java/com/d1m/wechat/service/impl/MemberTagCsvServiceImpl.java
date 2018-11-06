package com.d1m.wechat.service.impl;

import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.mapper.MemberTagCsvMapper;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.util.MyMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class MemberTagCsvServiceImpl implements MemberTagCsvService {

    @Autowired
    private MemberTagCsvMapper memberTagCsvMapper;

    @Override
    public MemberTagCsv selectByTaskName(String taskName) {
        return memberTagCsvMapper.selectOne(MemberTagCsv.builder().task(taskName).build());
    }

//    @Override
//    public Page<MemberTagCsv> searchTask(Integer wechatId,
//                                         AddMemberTagTaskModel tagTask, boolean queryCount) {
//        if (tagTask.pagable()) {
//            PageHelper.startPage(tagTask.getPageNum(),
//                    tagTask.getPageSize(), queryCount);
//        }
//        return memberTagCsvMapper.searchTask(wechatId, tagTask.getStatus(),
//                tagTask.getStart(), tagTask.getEnd(), tagTask.getSortName(),
//                tagTask.getSortDir());
//    }

    @Override
    public MyMapper<MemberTagCsv> getMapper() {
        return this.memberTagCsvMapper;
    }
}
