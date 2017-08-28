package com.d1m.wechat.schedule.job;

import com.d1m.wechat.schedule.BaseJobHandler;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.d1m.wechat.mapper.ConversationIndexMapper;

@JobHander(value="resetWeekConversationIndexJob")
@Component
public class ResetWeekConversationIndexJob extends BaseJobHandler {
	
	@Autowired
	private ConversationIndexMapper conversationIndexMapper;

	@Override
    public ReturnT<String> run(String... strings) throws Exception {
        try {
        	conversationIndexMapper.weekDelete();
        	return ReturnT.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("清除一周前的会话索引：" + e.getMessage());
            return ReturnT.FAIL;
        }
	}

}
