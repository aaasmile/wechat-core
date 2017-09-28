package com.d1m.wechat.migrate;

import java.util.*;
import java.util.concurrent.Future;
import javax.annotation.Resource;

import cn.d1m.wechat.client.model.WxUser;
import cn.d1m.wechat.client.model.common.WxList;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.AsyncResult;
import org.springframework.stereotype.Component;

import com.d1m.common.ds.TenantContext;
import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.wechatclient.WechatClientDelegate;

/**
 * 类描述
 *
 * @author f0rb on 2016-12-08.
 */
@Slf4j
@Component
public class MigrateUserExecutor {
    @Resource
    private MemberMapper memberMapper;
    @Resource
    private MemberService memberService;

    @Async("callerRunsExecutor")
    public Future<String> batchInsertOpenId(String tenant, Integer wechatId, List<String> openIdList) {
        TenantContext.setCurrentTenant(tenant);
        String result = "";
        int size = openIdList.size();
        String first = openIdList.get(0);
        String last = openIdList.get(size - 1);
        try {
            int count = memberMapper.batchInsertOpenId(wechatId, openIdList);
            log.info("wechatId[{}]批量插入openid[{} ~ {}] 共{}条, 实际插入{}条", wechatId, first, last, size, count);
            log.info("OpenId插入成功: wechatId[{}] {} ~ {}", wechatId, first, last);
        } catch (Exception e) {
            result = String.format("OpenId插入失败: wechatId[%d] [%s ~ %s]", wechatId, first, last);
            log.error(result, e);
        }
        return new AsyncResult<>(result);
    }

    @Async("callerRunsExecutor")
    public Future<Integer> execute(String tenant, List<MemberDto> members, Integer wechatId) {
        TenantContext.setCurrentTenant(tenant);
        log.info("WechatId[{}]用户信息同步任务: {}条", wechatId, members.size());

        Map<String, Integer> openId2memberId = new HashMap<>();
        List<WxUser> wxUserReqList = new LinkedList<WxUser>();
        for (MemberDto member : members) {
            WxUser wxUser = new WxUser();
            wxUser.setOpenid(member.getOpenId());
            openId2memberId.put(member.getOpenId(), member.getId());
            wxUserReqList.add(wxUser);
        }
        WxList<WxUser> wxList = WechatClientDelegate.batchGetUserInfo(wechatId, wxUserReqList);
        List<WxUser> errorList = new LinkedList<>();
        if (wxList.success()) {
            List<WxUser> wxUserList = wxList.get();
            Date current = new Date();
            for (WxUser wxUser : wxUserList) {
                try {
                    // 数据转换
                    Member newMember = memberService.getMemberByWxUser(wxUser, wechatId, current);
                    newMember.setId(openId2memberId.get(newMember.getOpenId()));
                    memberService.updateNotNull(newMember);
                } catch (Exception e) {
                    errorList.add(wxUser);
                    log.error("会员更新失败:" + wxUser.getOpenid(), e);
                }
            }
        } else {
            log.error("用户同步失败: [{}]{}", wxList.getErrcode(), wxList.getErrmsg());
        }

        //批量插入数据库
        //if (!updateList.isEmpty()) {
        //    memberMapper.batchUpdate(updateList);
        //}
        //StringBuilder result = new StringBuilder();
        //for (WxUser wxUser : errorList) {
        //    result.append(wxUser.getOpenid()).append(",");
        //}
        //if (result.length() > 0) {
        //    result.deleteCharAt(result.length() - 1);
        //}
        int success = members.size() - errorList.size();
        log.info("WechatId[{}]用户信息完成任务: {}条", wechatId, members.size() - errorList.size());
        return new AsyncResult<>(success);
    }
}
