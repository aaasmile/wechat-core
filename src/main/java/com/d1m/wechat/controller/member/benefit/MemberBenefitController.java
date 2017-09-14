package com.d1m.wechat.controller.member.benefit;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.benefit.MemberBenefitDto;
import com.d1m.wechat.pamametermodel.MemberBenefitListModel;
import com.d1m.wechat.service.MemberBenefitService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("/benefit/member")
public class MemberBenefitController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(MemberBenefitController.class);
	
	@Autowired
	private MemberBenefitService memberBenefitService;
	
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("member:list")
	public JSONObject benefitList(@RequestBody(required = false) MemberBenefitListModel memberBenefitListModel,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			log.info("memberBenefitListModel params:{}", memberBenefitListModel);
			
			Integer wechatId = getWechatId(session);
			Page<MemberBenefitDto> page = 
					memberBenefitService.search(wechatId, memberBenefitListModel);
			return representation(Message.MEMBER_LIST_SUCCESS, page.getResult(),
					memberBenefitListModel.getPageNum(),
					memberBenefitListModel.getPageSize(), page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return wrapException(e);
		}
		
	}
	
	@RequestMapping(value = "{id}/get.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("member:list")
	public JSONObject get(@PathVariable Integer id, HttpSession session) {
		try {
			log.info("id:{}", id);
			Integer wechatId = getWechatId(session);
			
			MemberBenefitDto memberDto = memberBenefitService.getMemberBenefitDto(
					wechatId, id);
			return representation(Message.MEMBER_GET_SUCCESS, memberDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
}
