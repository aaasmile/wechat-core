package com.d1m.wechat.schedule.job;

import java.util.Date;
import java.util.concurrent.CopyOnWriteArrayList;

import javax.annotation.Resource;

import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.util.DateUtil;
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
    //默认每批次处理数量
    private static final Integer BATCHSIZE = 1000;
    @Resource
    private MemberTagDataService memberTagDataService;
    @Resource
    private MemberTagCsvService memberTagCsvService;
    @Resource
    private MemberService memberService;

    @Override
    public ReturnT<String> run(String... strings) throws Exception {
        log.info("【异步调度加签】开始时间：" + DateUtil.formatYYYYMMDDHHMMSSS(new Date()));
        try {
            if (strings != null) {
                //获取导入文件id
                Integer fileId = ParamUtil.getInt(strings[0], null);
                XxlJobLogger.log("获取导入文件id : " + fileId);
                log.info("获取导入文件id : " + fileId);
                Integer wechatId = ParamUtil.getInt(strings[1], null);
                Integer batchSize = memberService.getBatchSize(wechatId) != null ? memberService.getBatchSize(wechatId) : BATCHSIZE;
                //设置上传文件为处理中状态
                memberTagCsvService.updateFileStatus(fileId, MemberTagCsvStatus.IN_PROCESS);
                int count = 0;
                while (true) {
                    CopyOnWriteArrayList<MemberTagData> list = memberTagDataService.getMembertagCsvData(fileId,
                     (count * batchSize), batchSize);
                    if (CollectionUtils.isNotEmpty(list)) {
                        //发起批量处理
                        memberTagDataService.batchExecute(list);
                    } else {
                        break;
                    }
                    count++;
                }
                //统计成功和失败条数
                memberTagCsvService.updateCountCsv(fileId);
                //更新上传文件状态为已完成
                memberTagCsvService.updateFileStatus(fileId, MemberTagCsvStatus.PROCESS_SUCCEED);
                log.info("======会员导入加签完成》》》》》============");
                XxlJobLogger.log("======会员导入加签完成》》》》》============");

            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            XxlJobLogger.log("会员导入批量加标签失败：" + e.getMessage());
            return ReturnT.FAIL;
        }finally {
            log.info("【异步调度加签】结束时间：" + DateUtil.formatYYYYMMDDHHMMSSS(new Date()));
        }
    }


}
