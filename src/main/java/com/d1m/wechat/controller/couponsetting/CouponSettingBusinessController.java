package com.d1m.wechat.controller.couponsetting;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.CouponSettingBusinessDto;
import com.d1m.wechat.pamametermodel.CouponSettingModel;
import com.d1m.wechat.service.CouponSettingBusinessService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("/coupon-setting-business")
public class CouponSettingBusinessController extends BaseController {

	private Logger log = LoggerFactory
			.getLogger(CouponSettingBusinessController.class);

	@Autowired
	private CouponSettingBusinessService couponSettingBusinessService;

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
			Page<CouponSettingBusinessDto> page = couponSettingBusinessService
					.search(getWechatId(session), model, true);
			return representation(Message.COUPON_SETTING_BUSINESS_LIST_SUCCESS,
					page.getResult(), model.getPageNum(), model.getPageSize(),
					page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
