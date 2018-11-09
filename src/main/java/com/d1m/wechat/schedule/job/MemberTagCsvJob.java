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
                //设置上传文件为导入中状态
                memberTagCsvService.updateFileStatus(fileId, MemberTagCsvStatus.IN_PROCESS);
                //设置上传数据状态为处理中
                memberTagDataService.updateDataStatus(fileId, 1);//1 代表处理中
                //数据标签检查
                List<MemberTagData> list = memberTagDataService.getMembertagCsvData(fileId);
                if (CollectionUtils.isNotEmpty(list)) {
                    log.info("======准备数据标签检查》》》》》============");
                    memberTagDataService.checkDataIsOK(list);
                } else {
                    errorMsg = "没有找到数据！";
                    /*memberTagCsvService.updateCsv(fileId, errorMsg);*/
                    log.info("fileId:" + fileId + "," + errorMsg);
                }

                //获取待加签的正确数据
                List<MemberTagData> addTagsDataList = memberTagDataService.getCsvData(fileId);
                if (CollectionUtils.isNotEmpty(addTagsDataList)) {
                    log.info("======准备加签》》》》》============");
                    memberTagDataService.addTags(addTagsDataList);
                } else {
                    errorMsg = "没有找到正确数据！";
                    /*memberTagCsvService.updateCsv(fileId, errorMsg);*/
                    log.info("fileId:" + fileId + "," + errorMsg);
                }

                //统计结果
                log.info("======统计结果》》》》》============");
                memberTagCsvService.updateCountCsv(fileId);

                //结果更新
                memberTagCsvService.updateFileStatus(fileId, MemberTagCsvStatus.PROCESS_SUCCEED);
                //memberTagDataService.updateDataStatus(fileId,2);
                log.info("======会员导入加签完成》》》》》============");
            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("会员导入批量加标签失败：" + e.getMessage());
            return ReturnT.FAIL;
        }
    }


}
