package com.d1m.wechat.controller.api;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/upgradev461")
public class UpgradeV461 extends BaseController {

  @Autowired
  MemberService memberService;

  @RequestMapping("transferMember")
  public String transferMember() {
    Integer wechatId = this.getWechatId();
    int count = memberService.loadMember(wechatId);
    return "member...load...." + count;
  }
}
