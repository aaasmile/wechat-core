package com.d1m.wechat.controller.api;

import com.d1m.common.ds.TenantContext;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.service.MemberService;
import com.esotericsoftware.minlog.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

/**
 * Created by Owen Jia on 2017/7/12.
 */
@RestController
@RequestMapping("/mapi/popup")
public class PopupApiController extends ApiController{

    private Logger log = LoggerFactory.getLogger(MemberApiController.class);

    @Autowired
    private MemberService memberService;

    @Resource
    TenantHelper tenantHelper;

    @RequestMapping(value = "/member/{memberId}", method = RequestMethod.GET)
    @ResponseBody
    public Member getMember(@PathVariable(name = "memberId") Integer id) {
        // 多数据源支持,临时方案
        String domain = tenantHelper.getTenantByIdentifier("marni");
        if (domain!=null){
            TenantContext.setCurrentTenant(domain);
            Log.info("mq domain: "+domain);
        }
        try {
            Member member = memberService.selectByKey(id);
            member.setCreatedAt(null);
            member.setLastConversationAt(null);
            member.setSubscribeAt(null);
            member.setUnsubscribeAt(null);
            return member;
        } catch (Exception e) {
            e.printStackTrace();
            return new Member();
        }
    }
}
