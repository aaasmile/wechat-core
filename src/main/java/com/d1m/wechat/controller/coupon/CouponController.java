package com.d1m.wechat.controller.coupon;

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
import com.d1m.wechat.dto.CouponDto;
import com.d1m.wechat.pamametermodel.CouponModel;
import com.d1m.wechat.service.CouponService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("/coupon")
public class CouponController extends BaseController {

	private Logger log = LoggerFactory.getLogger(CouponController.class);

	@Autowired
	private CouponService couponService;

	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject list(@RequestBody(required = false) CouponModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (model == null) {
				model = new CouponModel();
			}
			Page<CouponDto> page = couponService.search(getWechatId(session),
					model, true);
			return representation(Message.COUPON_LIST_SUCCESS,
					page.getResult(), model.getPageNum(), model.getPageSize(),
					page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
