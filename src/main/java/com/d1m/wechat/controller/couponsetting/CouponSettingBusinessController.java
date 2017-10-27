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

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping("/coupon-setting-business")
@Api(value="优惠券门店API", tags="优惠券门店接口")
public class CouponSettingBusinessController extends BaseController {

	private Logger log = LoggerFactory
			.getLogger(CouponSettingBusinessController.class);

	@Autowired
	private CouponSettingBusinessService couponSettingBusinessService;
	
	@ApiOperation(value="获取优惠券门店列表", tags="优惠券门店接口")
	@ApiResponse(code=200, message="1-优惠券门店列表成功")
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject list(
			@ApiParam("CouponSettingModel")
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
