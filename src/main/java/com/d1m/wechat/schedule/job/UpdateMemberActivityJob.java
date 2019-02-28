package com.d1m.wechat.schedule.job;

import java.util.Date;
import java.util.List;
import javax.annotation.Resource;

import com.d1m.wechat.schedule.BaseJobHandler;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

import com.d1m.wechat.dto.MemberStatusDto;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.DateUtil;

/**
 * 刷新会员活跃度任务
 *
 * @author d1m
 */
@JobHander(value="updateMemberActivityJob")
@Component
public class UpdateMemberActivityJob extends BaseJobHandler {

    private Logger log = LoggerFactory.getLogger(UpdateMemberActivityJob.class);
    @Resource
    private MemberService memberService;

    @Resource
    private WechatService wechatService;

    @Override
    public ReturnT<String> run(String... strings) throws Exception {
        try {
            Date endDate = DateUtil.getDateEnd(DateUtil.getYestoday());
            List<Wechat> wechatList = wechatService.getWechatList();
            for (Wechat wechat : wechatList) {
                List<MemberStatusDto> list = memberService.getMemberStatus(wechat.getId(), endDate);
                for (MemberStatusDto memberStatusDto : list) {
                    memberService.updateMemberActivity(memberStatusDto, endDate);
                }
            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            XxlJobLogger.log("刷新会员活跃度失败：" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

}
