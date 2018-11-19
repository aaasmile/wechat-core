package com.d1m.wechat.service.impl;

import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.dto.MemberStatsCounts;
import com.d1m.wechat.dto.MemberTagCsvDto;
import com.d1m.wechat.mapper.MemberTagCsvMapper;
import com.d1m.wechat.mapper.MemberTagDataMapper;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.pamametermodel.AddMemberTagTaskModel;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Slf4j
@Service
public class MemberTagCsvServiceImpl implements MemberTagCsvService {

    @Autowired
    private MemberTagCsvMapper memberTagCsvMapper;

    @Autowired
    private MemberTagDataMapper memberTagDataMapper;

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
    public Page<MemberTagCsvDto> searchTask(Integer wechatId, AddMemberTagTaskModel tagTask) {
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
        map.put("noStatus",2);
        return memberTagCsvMapper.searchTask(map);
    }

    @Override
    public MyMapper<MemberTagCsv> getMapper() {
        return this.memberTagCsvMapper;
    }

    /**
     * 更新上传文件
     *
     * @param fileId
     * @param
     * @param status
     */
    public void updateFileStatus(Integer fileId, MemberTagCsvStatus status) {
        MemberTagCsv csv = new MemberTagCsv();
        csv.setStatus(status);
        csv.setFileId(fileId);
        int t = memberTagCsvMapper.updateByPrimaryKeySelective(csv);
        log.info("更新上传文件：" + t);
    }

    /**
     * 更新上传文件信息
     *
     * @param fileId
     */
    public void updateCsv(Integer fileId, String errorMsg) {

        MemberTagCsv csv = new MemberTagCsv();
        csv.setFileId(fileId);
        csv.setErrorMsg(errorMsg);
        csv.setSuccessCount(0);
        csv.setFailCount(0);
        csv.setStatus(MemberTagCsvStatus.PROCESS_SUCCEED);
        memberTagCsvMapper.updateByPrimaryKeySelective(csv);
    }

    /**
     * 更新上传信息表统计数据
     *
     * @param fileId
     */
    public void updateCountCsv(Integer fileId) {
        MemberStatsCounts memberStatsCounts = memberTagDataMapper.getCount(fileId);
        if (memberStatsCounts != null) {
            MemberTagCsv csv = new MemberTagCsv();
            csv.setFailCount(memberStatsCounts.getFailCount());
            csv.setSuccessCount(memberStatsCounts.getSuccessCount());
            csv.setFileId(fileId);
            int t = memberTagCsvMapper.updateByPrimaryKeySelective(csv);
            log.info("更新上传信息表统计数据结果：" + t);
        }
    }
}
