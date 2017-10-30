package com.d1m.wechat.controller.activity;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import com.d1m.wechat.dto.ActivityCouponSettingDto;
import com.d1m.wechat.pamametermodel.ActivityModel;
import com.d1m.wechat.service.ActivityCouponSettingService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping("/activity-coupon-setting")
@Api(value="活动优惠券API", tags="活动优惠券接口")
public class ActivityCouponSettingController extends BaseController {

	private Logger log = LoggerFactory
			.getLogger(ActivityCouponSettingController.class);

	@Autowired
	private ActivityCouponSettingService activityCouponSettingService;
	
	@ApiOperation(value="查询活动优惠券列表", tags="活动优惠券接口")
	@ApiResponse(code=200, message="1-活动优惠券列表成功, 0-系统异常")
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject list(
			@ApiParam("ActivityModel")
			@RequestBody(required = false) ActivityModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (model == null) {
				model = new ActivityModel();
			}
			Page<ActivityCouponSettingDto> page = activityCouponSettingService
					.search(getWechatId(session), model, true);
			return representation(Message.ACTIVITY_COUPON_SETTING_LIST_SUCCESS,
					page.getResult(), model.getPageNum(), model.getPageSize(),
					page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="创建新的活动优惠券", tags="活动优惠券接口")
	@ApiResponse(code=200, message="1-活动优惠券创建成功, 0-系统异常")
	@RequestMapping(value = "new.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(
			@ApiParam("ActivityModel")
			@RequestBody(required = false) ActivityModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<ActivityCouponSettingDto> dtos = activityCouponSettingService
					.create(getWechatId(session), getUser(session), model);
			return representation(
					Message.ACTIVITY_COUPON_SETTING_CREATE_SUCCESS, dtos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="根据ID删除活动优惠券", tags="活动优惠券接口")
	@ApiResponse(code=200, message="1-活动优惠券删除成功, 0-系统异常")
	@RequestMapping(value = "{activityCouponSettingId}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject delete(
			@ApiParam("活动优惠券ID")
			@PathVariable Integer activityCouponSettingId,
			HttpSession session) {
		try {
			activityCouponSettingService.delete(getWechatId(session),
					getUser(session), activityCouponSettingId);
			return representation(Message.ACTIVITY_COUPON_SETTING_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
