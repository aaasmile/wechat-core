package com.d1m.wechat.schedule.job;

import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;

import com.github.pagehelper.PageHelper;
import org.apache.commons.collections.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.schedule.BaseJobHandler;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.service.MemberTagDataService;
import com.d1m.wechat.util.ParamUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

@JobHander(value = "memberTagCsvJob")
@Component
public class MemberTagCsvJob extends BaseJobHandler {
	
	private static final Logger log = LoggerFactory.getLogger(MemberTagCsvJob.class);

    @Resource
    private MemberTagDataService memberTagDataService;
    @Resource
    private MemberTagCsvService memberTagCsvService;

    @Override
    public ReturnT<String> run(String... strings) throws Exception {
        try {
            if (strings != null) {
                //获取导入文件id
                Integer fileId = ParamUtil.getInt(strings[0], null);
                XxlJobLogger.log("获取导入文件id : " + fileId);
                log.info("获取导入文件id : " + fileId);
                //数据标签检查
                PageHelper.startPage(1,
                 1000, true);
                CopyOnWriteArrayList<MemberTagData> list = memberTagDataService.getMembertagCsvData(fileId);

                if (CollectionUtils.isNotEmpty(list)) {
                    //设置上传文件为处理中状态
                    memberTagCsvService.updateFileStatus(fileId, MemberTagCsvStatus.IN_PROCESS);
                    //异步发起批量处理
                    memberTagDataService.asyncCsvJobBatch(list,fileId);
                }


            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            XxlJobLogger.log("会员导入批量加标签失败：" + e.getMessage());
            return ReturnT.FAIL;
        }
    }


}
