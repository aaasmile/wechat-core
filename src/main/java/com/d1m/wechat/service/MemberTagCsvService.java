package com.d1m.wechat.service;

import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.dto.ImportCsvDto;
import com.d1m.wechat.dto.MemberTagCsvDto;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
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
    Page<MemberTagCsvDto> searchTask(Integer wechatId, AddMemberTagTaskModel tagTask);

    /**
     * 更新上传文件
     *
     * @param fileId
     * @param
     * @param status
     */
    public void updateFileStatus(Integer fileId, MemberTagCsvStatus status);

    /**
     * 更新上传文件信息
     *
     * @param fileId
     */
     void updateCsv(Integer fileId, String errorMsg);

    /**
     * 更新上传信息表统计数据
     *
     * @param fileId
     */
    public void updateCountCsv(Integer fileId);
}
