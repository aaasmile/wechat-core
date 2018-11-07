package com.d1m.wechat.service.impl;

import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.dto.ImportCsvDto;
import com.d1m.wechat.mapper.MemberTagCsvMapper;
import com.d1m.wechat.pamametermodel.AddMemberTagTaskModel;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


@Service
public class MemberTagCsvServiceImpl implements MemberTagCsvService {

    @Autowired
    private MemberTagCsvMapper memberTagCsvMapper;

    @Override
    public MemberTagCsv selectByTaskName(String taskName) {
        return memberTagCsvMapper.selectOne(MemberTagCsv.builder().task(taskName).build());
    }


    /**
     * 查询任务列表接口
     * @param wechatId
     * @param tagTask
     * @return
     */
    @Override
    public Page<ImportCsvDto> searchTask(Integer wechatId, AddMemberTagTaskModel tagTask) {
        if (tagTask.pagable()) {
            PageHelper.startPage(tagTask.getPageNum(),
                    tagTask.getPageSize(), true);
        }

        Map<String,Object> map= new HashMap<>();
        map.put("wechatId",wechatId);
        map.put("status",tagTask.getStatus());
        map.put("start",tagTask.getStart());
        map.put("end",tagTask.getEnd());
        map.put("sortName",tagTask.getSortName());
        map.put("sortDir",tagTask.getSortDir());
        return memberTagCsvMapper.searchTask(map);
    }

    @Override
    public MyMapper<MemberTagCsv> getMapper() {
        return this.memberTagCsvMapper;
    }
}
