package com.d1m.wechat.schedule.job;

import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.schedule.BaseJobHandler;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * Created by d1m on 2016/9/23.
 * 每月月初清空本月群发数
 */
@JobHander(value="resetMonthSendJob")
@Component
public class ResetMonthSendJob extends BaseJobHandler {
    @Autowired
    private MemberMapper memberMapper;

    @Override
    public ReturnT<String> run(String... strings) throws Exception {
        try {
            memberMapper.resetMonthSend();
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("清空本月群发数失败：" + e.getMessage());
            return ReturnT.FAIL;
        }
    }
}
