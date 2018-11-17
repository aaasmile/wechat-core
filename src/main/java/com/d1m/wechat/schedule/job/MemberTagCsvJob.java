package com.d1m.wechat.schedule.job;

import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.model.enums.MemberTagDataStatus;
import com.d1m.wechat.schedule.BaseJobHandler;
import com.d1m.wechat.service.*;

import com.d1m.wechat.util.ParamUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

@JobHander(value = "memberTagCsvJob")
@Component
@Slf4j
public class MemberTagCsvJob extends BaseJobHandler {

    @Resource
    private MemberTagDataService memberTagDataService;
    @Resource
    private MemberTagCsvService memberTagCsvService;

    @Override
    public ReturnT<String> run(String... strings) throws Exception {
        String errorMsg = null;
        try {
            if (strings != null) {
                //获取导入文件id
                Integer fileId = ParamUtil.getInt(strings[0], null);
                XxlJobLogger.log("获取导入文件id : " + fileId);
                log.info("获取导入文件id : " + fileId);
                //数据标签检查
                CopyOnWriteArrayList<MemberTagData> list = memberTagDataService.getMembertagCsvData(fileId);

                if (CollectionUtils.isNotEmpty(list)) {
                    //设置上传文件为导入中状态
                    memberTagCsvService.updateFileStatus(fileId, MemberTagCsvStatus.IN_PROCESS);
                    //分批处理
                    memberTagDataService.batchExecute(fileId, list);
                }
                //统计结果
                log.info("======统计结果》》》》》============");
                memberTagCsvService.updateCountCsv(fileId);

                //结果更新
                memberTagCsvService.updateFileStatus(fileId, MemberTagCsvStatus.PROCESS_SUCCEED);
                log.info("======会员导入加签完成》》》》》============");
            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            XxlJobLogger.log("会员导入批量加标签失败：" + e.getMessage());
            return ReturnT.FAIL;
        }
    }


}
