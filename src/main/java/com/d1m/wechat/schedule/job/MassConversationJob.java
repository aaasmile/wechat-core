package com.d1m.wechat.schedule.job;

import javax.annotation.Resource;

import com.d1m.wechat.model.enums.MassConversationResultStatus;
import com.d1m.wechat.pamametermodel.MassConversationModel;
import com.d1m.wechat.schedule.BaseJobHandler;
import com.d1m.wechat.service.ConversationService;
import com.d1m.wechat.util.ParamUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

@JobHander(value="massConversationJob")
@Component
public class MassConversationJob extends BaseJobHandler {

    @Resource
    private ConversationService conversationService;

    @Override
    public ReturnT<String> run(String... strings) throws Exception {
        try {
            if(strings!=null){
                // 参数1为微信ID，参数2为群发ID
                Integer wechatId = ParamUtil.getInt(strings[0], null);
                Integer massId = ParamUtil.getInt(strings[1], null);
                XxlJobLogger.log("wechatId : "+wechatId + ", massId : " + massId);
                MassConversationModel massConversationModel = new MassConversationModel();
                massConversationModel.setId(massId);
                massConversationModel.setStatus(MassConversationResultStatus.WAIT_SEND.name());
                conversationService.sendMassConversation(wechatId, null, massConversationModel);
            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("群发定时消息失败：" + e.getMessage());
            return ReturnT.FAIL;
        }
    }
}
