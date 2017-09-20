package com.d1m.wechat.customservice.controller;

import java.io.Serializable;
import java.util.Collection;
import java.util.Date;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSONObject;
import lombok.extern.slf4j.Slf4j;
import org.springframework.messaging.handler.annotation.*;
import org.springframework.stereotype.Controller;

import com.d1m.common.ds.TenantContext;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.customservice.CustomerServiceRepresentative;
import com.d1m.wechat.dto.ConversationDto;
import com.d1m.wechat.dto.UserDto;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.model.enums.Event;
import com.d1m.wechat.service.UserService;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.DateUtil;

/**
 * CustomServiceController 客户服务
 * <p>
 * 处理客服人员
 *
 * @author f0rb on 2017-03-14.
 * @deprecated livechat迁移到微服务wechat-customservice
 */
@Slf4j
@Controller
@Deprecated
public class CustomServiceController {

    /* 公众号在线客服 */
    private final Map<Serializable, Map<Serializable, CustomerServiceRepresentative>> wechatCsrMap = new ConcurrentHashMap<>();
    /* 会员会话接入到客服的映射, 一个会员对应一个客服 */
    private final Map<Serializable, CustomerServiceRepresentative> memberCsrMap = new ConcurrentHashMap<>();
    /* 客服锁, 确保客服上下线时会话能够正常接入 */
    private final Map<Serializable, ReadWriteLock> memberCsrLockMap = new ConcurrentHashMap<>();

    @Resource
    private UserService userService;
    @Resource
    private WechatService wechatService;
    @Resource
    private TenantHelper tenantHelper;

    @MessageExceptionHandler
    public void handleExceptions(Throwable t) {
        log.error("Error handling message: " + t.getMessage(), t);
    }

    /**
     * 管理客服在线列表
     */
    @MessageMapping({
            "/csr/{wechatId}/{userId}/{action}"
    })
    @SendTo(TOPIC.CSR)//客服代表 Custom Service Rep
    public Collection<?> action(
            @DestinationVariable Integer userId,
            @DestinationVariable Integer wechatId,
            @DestinationVariable String action,
            @Payload(required = false) JSONObject json
    ) {
        // 多数据源支持
        String domain = tenantHelper.getTenantByWechatId(wechatId);
        if (domain!=null){
            TenantContext.setCurrentTenant(domain);
            log.info("mq domain: "+domain);
        }
        UserDto user = userService.getById(userId);
        if (user == null) {
            throw new RuntimeException("用户不存在:" + userId);
        }
        Wechat wechat = wechatService.getById(wechatId);
        if (wechat == null) {
            throw new RuntimeException("微信公众号未配置:" + wechatId);
        }

        // 为公众号创建新上线的客服的map
        if (!wechatCsrMap.containsKey(wechatId)) {
            synchronized (wechatCsrMap) {
                if (!wechatCsrMap.containsKey(wechatId)) {
                    wechatCsrMap.put(wechatId, new ConcurrentHashMap<Serializable, CustomerServiceRepresentative>());
                }
            }
        }

        Map<Serializable, CustomerServiceRepresentative> csrMap = wechatCsrMap.get(wechatId);

        if (Action.ONLINE.equals(action)) {
            processOnline(wechat, user);
        } else if (Action.OFFLINE.equals(action)) {
            processOffline(wechat, user);
        } else if (Action.ACCEPT.equals(action)) {//暂时什么都不做, 只返回客服列表
            processAccept(wechat, user, json.getInteger("memberId"));
        } else if (Action.FINISH.equals(action)) {
            processFinish(wechat, user, json.getInteger("memberId"));
        } else {
            log.warn("UNKNOWN ACTION : {}", action);
        }
        return csrMap.values();
    }

    private void processOnline(Wechat wechat, UserDto user) {
        Integer userId = user.getId();
        Map<Serializable, CustomerServiceRepresentative> csrMap = wechatCsrMap.get(wechat.getId());
        if (csrMap.containsKey(userId)) {
            log.warn("客服已在线:{}", userId);
        }
        CustomerServiceRepresentative csr = new CustomerServiceRepresentative();
        csr.setId(user.getId());
        csr.setNickname(user.getUsername());
        csr.setPhotoUrl(user.getAvatar());
        //csr.setCurrentCustomerCount(0);
        csrMap.put(userId, csr);
        log.info("客服上线: [{}]{}", csr.getId(), csr.getNickname());
    }

    private void processOffline(Wechat wechat, UserDto user) {
        Integer userId = user.getId();
        Map<Serializable, CustomerServiceRepresentative> csrMap = wechatCsrMap.get(wechat.getId());
        if (csrMap.containsKey(userId)) {
            ReadWriteLock memberCsrLock = new ReentrantReadWriteLock();
            try {
                memberCsrLockMap.put(userId, memberCsrLock);
                memberCsrLock.writeLock().lock();
                CustomerServiceRepresentative csr = csrMap.remove(userId);
                // 移除当前客服所有对话的会员
                for (Map.Entry<Serializable, CustomerServiceRepresentative> entry : memberCsrMap.entrySet()) {
                    if (userId.equals(entry.getValue().getId())) {
                        memberCsrMap.remove(entry.getKey());
                    }
                }
                log.info("客服下线: [{}]{}", csr.getId(), csr.getNickname());
                memberCsrLockMap.remove(userId);
            } finally {
                memberCsrLock.writeLock().unlock();
            }
        } else {
            log.warn("客服已下线:{}", userId);
        }
    }

    /**
     * 接入
     *
     * @param wechat
     * @param user
     * @param memberId
     */
    private void processAccept(Wechat wechat, UserDto user, Integer memberId) {
        Map<Serializable, CustomerServiceRepresentative> csrMap = wechatCsrMap.get(wechat.getId());
        CustomerServiceRepresentative csr = csrMap.get(user.getId());
        if (!memberCsrMap.containsKey(memberId)) {
            if(csr!=null){
                memberCsrMap.put(memberId, csr);
                int ccc = csr.getCurrentCustomerCount().incrementAndGet();
                log.info("公众号[{}]: 客服[{}]接待人数为:{}", wechat.getName(), csr.getNickname(), ccc);
            }
        } else {
            CustomerServiceRepresentative acceptedCsr = memberCsrMap.get(memberId);
            if (!csr.getId().equals(acceptedCsr.getId())) {
                log.warn("公众号[{}]: 客服[{}]请求接入的会员[{}]已由客服[{}]接入",
                         wechat.getName(), csr.getNickname(), memberId, acceptedCsr.getNickname());
            }
        }
    }

    private void processFinish(Wechat wechat, UserDto user, Integer memberId) {
        Map<Serializable, CustomerServiceRepresentative> csrMap = wechatCsrMap.get(wechat.getId());
        if (memberCsrMap.containsKey(memberId)) {
            CustomerServiceRepresentative csr = memberCsrMap.remove(memberId);
            int ccc = csr.getCurrentCustomerCount().decrementAndGet();
            log.info("客服[{}]接待人数为:{}", csr.getNickname(), ccc);
        }
    }

    /**
     * 把客服(fromId)与会员(memberId)的会话转接给客服(toId)
     *
     * @return 客服在线列表
     */
    @MessageMapping({
            "/csr/transfer/{wechatId}/{fromId}/{toId}/{memberId}"
    })
    @SendTo(TOPIC.MSG)
    public ConversationDto transfer(
            @DestinationVariable Integer wechatId,
            @DestinationVariable Integer fromId,
            @DestinationVariable Integer toId,
            @DestinationVariable Integer memberId
    ) {
        // 多数据源支持
        String domain = tenantHelper.getTenantByWechatId(wechatId);
        if (domain!=null){
            TenantContext.setCurrentTenant(domain);
            log.info("mq domain: "+domain);
        }
        //TODO 处理并发
        Map<Serializable, CustomerServiceRepresentative> csrMap = wechatCsrMap.get(wechatId);
        CustomerServiceRepresentative fromCsr = csrMap.get(fromId);

        if (!memberCsrMap.containsKey(memberId)) {
            log.warn("会员[{}]会话关系不存在", memberId);
        }

        if (csrMap.containsKey(toId)) {
            CustomerServiceRepresentative toCsr = csrMap.get(toId);
            toCsr.getCurrentCustomerCount().incrementAndGet();
            fromCsr.getCurrentCustomerCount().decrementAndGet();
            memberCsrMap.put(memberId, toCsr);
        } else {
            log.warn("客服[{}]不在线", toId);
        }

        ConversationDto conversationDto = new ConversationDto();
        conversationDto.setCsrId(toId);
        conversationDto.setMemberId(memberId + "");
        conversationDto.setCreatedAt(DateUtil.formatYYYYMMDDHHMMSS(new Date()));
        conversationDto.setEvent(Event.CONVERSATION_TRANSFER.getValue());

        return conversationDto;
    }

    @MessageMapping("/msg/incoming/{wechatId}")
    @SendTo(TOPIC.MSG)//客服代表
    public Object sendMessageToUser(@DestinationVariable Integer wechatId, ConversationDto conversation) {
        // 多数据源支持
        String domain = tenantHelper.getTenantByWechatId(wechatId);
        if (domain!=null){
            TenantContext.setCurrentTenant(domain);
            log.info("mq domain: "+domain);
        }
        Integer toMemberId = Integer.valueOf(conversation.getMemberId());

        ReadWriteLock memberCsrLock = memberCsrLockMap.get(toMemberId);
        try {
            if (memberCsrLock != null) {
                memberCsrLock.readLock().lock();
            }
            if (memberCsrMap.containsKey(toMemberId)) {
                //消息分配给正在接待该会员的客服
                conversation.setCsrId(memberCsrMap.get(toMemberId).getId());
            } else {
                //根据客服的当前接待数来确定将会话分配给哪个客服
                Map<Serializable, CustomerServiceRepresentative> csrMap = wechatCsrMap.get(wechatId);
                CustomerServiceRepresentative chosen = null;
                int currentCustomerCount = Integer.MAX_VALUE;
                for (CustomerServiceRepresentative customerServiceRepresentative : csrMap.values()) {
                    int ccc = customerServiceRepresentative.getCurrentCustomerCount().get();
                    if (ccc < currentCustomerCount) {
                        currentCustomerCount = ccc;
                        chosen = customerServiceRepresentative;
                    }
                }

                if (chosen != null) {
                    conversation.setCsrId(chosen.getId());
                    chosen.getCurrentCustomerCount().incrementAndGet();//接待客户+1
                    memberCsrMap.put(toMemberId, chosen);
                } else {
                    //TODO 处理客服全部下线的情况
                    log.warn("没有客服在线: {}", wechatId);
                }
            }
        } finally {
            if (memberCsrLock != null) {
                memberCsrLock.readLock().unlock();
            }
        }

        return conversation;
    }



    interface TOPIC {
        String CSR = "/topic/csr/{wechatId}";//客服代表 Custom Service Rep
        String MSG = "/topic/msg/{wechatId}";
    }

    interface Action {
        String ONLINE = "online";
        String OFFLINE = "offline";
        String ACCEPT = "accept";
        String FINISH = "finish";//结束一个会话
        String TRANSFER = "transfer";//转接一个会话
    }

}
