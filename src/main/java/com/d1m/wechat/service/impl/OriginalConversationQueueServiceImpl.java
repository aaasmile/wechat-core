package com.d1m.wechat.service.impl;

import javax.annotation.Resource;

import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.model.enums.Event;
import com.d1m.wechat.model.enums.MsgType;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.ParamUtil;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.d1m.wechat.mapper.OriginalConversationMapper;
import com.d1m.wechat.model.OriginalConversation;
import com.d1m.wechat.service.OriginalConversationService;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;

import java.util.Date;

/**
 * 专门处理待处理的OriginalConversation
 *
 * @author f0rb
 */
@Slf4j
@Service
public class OriginalConversationQueueServiceImpl implements ApplicationListener<ContextRefreshedEvent> {
    @Resource
    OriginalConversationService originalConversationService;

    @Resource
    OriginalConversationMapper originalConversationMapper;

    @Resource
    MemberService memberService;

    @Resource
    WechatService wechatService;

    private void initOriginalConversationQueue() {
        Page<OriginalConversation> originalConversations =
                originalConversationMapper.searchActiveOriginalConversations();
        log.info("active originalConversations size : {}", originalConversations.size());
        Document document = null;
        for (OriginalConversation originalConversation : originalConversations) {
            try {
                document = DocumentHelper.parseText(originalConversation.getContent());
                Element root = document.getRootElement();
                String toUserName = ParamUtil.getElementContent(root, "ToUserName");
                String fromUserName = ParamUtil.getElementContent(root, "FromUserName");
                String event = ParamUtil.getElementContent(root, "Event");
                String msgType = ParamUtil.getElementContent(root, "MsgType");
                String createTime = ParamUtil.getElementContent(root, "CreateTime");
                Date current = new Date(ParamUtil.getLong(createTime, null) * 1000);
                Wechat wechat = wechatService.getByOpenId(toUserName);

                if(wechat!=null) {
                    // 兼容原始会话wechatid为空的情况
                    originalConversation.setWechatId(wechat.getId());
                    originalConversationService.updateNotNull(originalConversation);

                    Member member = null;
                    Event et = Event.getValueByName(event);
                    if (et == null || !et.isSystem()) {
                        member = memberService.createMember(originalConversation.getWechatId(), fromUserName, current);
                    }
                    MsgType mt = MsgType.getByName(msgType);
                    if ((mt!=null&&!mt.equals(MsgType.EVENT))||(et!=null&&et.isInteractive())) {
                        member.setLastConversationAt(current);
                        memberService.updateAll(member);
                    }
                    originalConversationService.execute(member, document, originalConversation);
                }
            } catch (DocumentException e) {
                log.error("initOriginalConversationQueue error",e);
                break;
            }
        }
    }

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event) {
        if(event.getApplicationContext().getParent() == null)
        {
            log.debug("initOriginalConversationQueue start...");
            initOriginalConversationQueue();
        }
    }
}
