package com.d1m.wechat.controller.member.statistics;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.QRcodeStatisticsDto;
import com.d1m.wechat.service.MemberQRcodeStatisticsService;
import com.d1m.wechat.util.Message;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping("/api/member-qrcodeStatistics")
@Api(value="会员扫码统计API", tags="会员扫码关注统计接口")
public class MemberQRcodeStatisticsController extends BaseController {

	private Logger log = LoggerFactory.getLogger(MemberQRcodeStatisticsController.class);

	
	@Autowired
	private MemberQRcodeStatisticsService MemberQRcodeStatisticsService;
	
	
	@ApiOperation(value="查询会员扫码统计", tags="会员扫码统计接口")
	@ApiResponse(code=200, message="1-查询会员扫码统计结果成功")
	@RequestMapping(value = "qrcodeStatistics.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject queryQRcodeStatistics(
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<QRcodeStatisticsDto> qrcodeStatisticsDtos = MemberQRcodeStatisticsService.getListMemberQrcodeStatistics();
			return representation(Message.MEMBER_QRCODE_STATISTICS,
					qrcodeStatisticsDtos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	
}
