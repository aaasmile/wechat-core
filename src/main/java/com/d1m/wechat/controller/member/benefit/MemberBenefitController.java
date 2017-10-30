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
import com.d1m.wechat.dto.benefit.MemberBenefitDetailDto;
import com.d1m.wechat.dto.benefit.MemberBenefitDto;
import com.d1m.wechat.pamametermodel.MemberBenefitListModel;
import com.d1m.wechat.service.MemberBenefitService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping("/benefit/member")
@Api(value="Benefit项目会员API", tags="Benefit项目会员接口")
public class MemberBenefitController extends BaseController {
	
	private Logger log = LoggerFactory.getLogger(MemberBenefitController.class);
	
	@Autowired
	private MemberBenefitService memberBenefitService;
	
	@ApiOperation(value="获取Benefit项目会员列表", tags="会员接口")
	@ApiResponse(code=200, message="1-获取会员列表成功")
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("member:list")
	public JSONObject benefitList(
			@ApiParam(name="MemberBenefitListModel", required=false)
				@RequestBody(required = false) MemberBenefitListModel memberBenefitListModel,
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
	
	@ApiOperation(value="获取Benefit项目会员详情成功", tags="会员接口")
	@ApiResponse(code=200, message="1-获取会员详情成功")
	@RequestMapping(value = "{memberId}/get.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("member:list")
	public JSONObject get(
			@ApiParam("会员ID")
				@PathVariable Integer memberId, HttpSession session) {
		try {
			log.info("memberId:{}", memberId);
			Integer wechatId = getWechatId(session);
			
			MemberBenefitDetailDto memberDto = memberBenefitService.getMemberBenefitDetailDto(
					wechatId, memberId);
			return representation(Message.MEMBER_GET_SUCCESS, memberDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
}
