package com.d1m.wechat.controller.couponsetting;

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
import com.d1m.wechat.dto.CouponSettingDto;
import com.d1m.wechat.pamametermodel.CouponSettingModel;
import com.d1m.wechat.service.CouponSettingService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("/coupon-setting")
public class CouponSettingController extends BaseController {

	private Logger log = LoggerFactory.getLogger(CouponSettingController.class);

	@Autowired
	private CouponSettingService couponSettingService;

	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject list(
			@RequestBody(required = false) CouponSettingModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (model == null) {
				model = new CouponSettingModel();
			}
			Page<CouponSettingDto> page = couponSettingService.search(
					getWechatId(session), model, true);
			return representation(Message.COUPON_SETTING_LIST_SUCCESS,
					page.getResult(), model.getPageNum(), model.getPageSize(),
					page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{id}/get.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject get(@PathVariable Integer id, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			CouponSettingDto couponSettingDto = couponSettingService.get(
					getWechatId(session), id);
			return representation(Message.COUPON_SETTING_GET_SUCCESS,
					couponSettingDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(
			@RequestBody(required = false) CouponSettingModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<ActivityCouponSettingDto> dtos = couponSettingService.update(
					getWechatId(session), getUser(session), model);
			return representation(
					Message.ACTIVITY_COUPON_SETTING_CREATE_SUCCESS, dtos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
