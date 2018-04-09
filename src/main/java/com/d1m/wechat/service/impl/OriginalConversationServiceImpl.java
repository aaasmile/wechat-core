package com.d1m.wechat.service.impl;

import javax.annotation.Resource;

import org.dom4j.Document;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.d1m.wechat.mapper.OriginalConversationMapper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.OriginalConversation;
import com.d1m.wechat.service.ConversationService;
import com.d1m.wechat.service.OriginalConversationService;

import tk.mybatis.mapper.common.Mapper;

@Service
public class OriginalConversationServiceImpl extends
        BaseService<OriginalConversation> implements
        OriginalConversationService {
	
	private static final Logger log = LoggerFactory.getLogger(OriginalConversationServiceImpl.class);

    @Resource
    @Lazy
    private ConversationService conversationService;

    @Resource
    private OriginalConversationMapper originalConversationMapper;

    @Override
    public Mapper<OriginalConversation> getGenericMapper() {
        return originalConversationMapper;
    }

    @Override
    public OriginalConversation get(Integer wechatId, Integer id) {
        OriginalConversation record = new OriginalConversation();
        record.setWechatId(wechatId);
        record.setId(id);
        return originalConversationMapper.selectOne(record);
    }

    /**
     * 加了Async注解, 将此方法提交到spring管理的executor异步执行
     *
     * @param originalConversation originalConversation
     */
    //@Async("callerRunsExecutor")
    @Override
    public void execute(Member member, Document document, OriginalConversation originalConversation) {
//        log.info("original conversation id : {}, status : {}, start handle.",
//                 originalConversation.getId(), originalConversation.getStatus());
//        // #8015 5、再原始会话表中，我们需要存储全部处理完成时间，以方便后期分析系统处理性能。
//        originalConversation.setExecuteStart(new Date());
//        try {
//            boolean success = conversationService.memberToWechat(member, document, originalConversation);
//            if (success) {
//                originalConversation.setStatus(OriginalConversationStatus.SUCCESS.getValue());
//            }
//        } catch (Exception e) {
//            log.error("error : ", e);
//            originalConversation.setStatus(OriginalConversationStatus.FAIL.getValue());
//        }
//        originalConversation.setExecuteEnd(new Date());
//        int during = (int) (originalConversation.getExecuteEnd().getTime() - originalConversation.getExecuteStart().getTime());
//        originalConversation.setExecuteMillis(during);
//        updateAll(originalConversation);
//        log.info("original conversation id : {} finished for {}ms.", originalConversation.getId(), originalConversation.getExecuteMillis());
    }

}
