package com.d1m.wechat.controller.api;

import com.d1m.common.ds.TenantContext;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.service.MemberService;
import com.esotericsoftware.minlog.Log;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

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
@Api(value="微店API", tags="微店接口")
public class PopupApiController extends ApiController{

    private Logger log = LoggerFactory.getLogger(MemberApiController.class);

    @Autowired
    private MemberService memberService;

    @Resource
    TenantHelper tenantHelper;
    
	@ApiOperation(value="根据会员ID查询会员信息", tags="微店接口")
	@ApiResponse(code=200, message="返回结果会员信息")
    @RequestMapping(value = "/member/{memberId}", method = RequestMethod.GET)
    @ResponseBody
    public Member getMember(
    		@ApiParam("会员ID")
    		@PathVariable(name = "memberId") Integer id) {
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
