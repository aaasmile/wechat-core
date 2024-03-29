package com.d1m.wechat.controller.api;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
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
import com.d1m.wechat.dto.BusinessAreaListDto;
import com.d1m.wechat.dto.BusinessDto;
import com.d1m.wechat.model.OauthUrl;
import com.d1m.wechat.pamametermodel.BusinessModel;
import com.d1m.wechat.service.BusinessService;
import com.d1m.wechat.service.OauthUrlService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

/**
 * Created on 16/11/25.
 */
@Controller
@RequestMapping("/api/store")
@Api(value="店铺API", tags="店铺接口")
public class StoreApiController extends ApiController {

	private Logger log = LoggerFactory.getLogger(StoreApiController.class);

	@Autowired
	private BusinessService businessService;

	@Resource
	private OauthUrlService oauthUrlService;
	
	@ApiOperation(value="根据ID获取门店信息", tags="店铺接口")
	@ApiResponse(code=200, message="返回门店信息")
	@RequestMapping(value = "/getOutlet/{id}.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject getOutlet(
			@ApiParam("门店ID")
			@PathVariable Integer id, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			BusinessDto businessDto = businessService.get(null, id);
			return representation(Message.BUSINESS_GET_SUCCESS, businessDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value="获取门店信息列表", tags="店铺接口")
	@ApiResponse(code=200, message="返回门店信息列表")
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject list(
			@ApiParam("BusinessModel")
			@RequestBody(required = false) BusinessModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (model == null) {
				model = new BusinessModel();
			}
			model.disablePage();
			Integer wechatId = null;
			log.info("shortUrl : {}.", model.getShortUrl());
			if (StringUtils.isNotBlank(model.getShortUrl())) {
				OauthUrl urlObj = oauthUrlService.getByShortUrl(model
						.getShortUrl());
				log.info("urlObj : {}",
						(urlObj != null ? urlObj.getId() : null));
				if (urlObj != null) {
					wechatId = urlObj.getWechatId();
				}
			}
			Page<BusinessDto> page = businessService.search(wechatId, model,
					true);
			return representation(Message.BUSINESS_LIST_SUCCESS,
					page.getResult(), model.getPageNum(), model.getPageSize(),
					page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="获取地区列表", tags="店铺接口")
	@ApiResponse(code=200, message="返回地区列表")
	@RequestMapping(value = "area-list.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject areaList(HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<BusinessAreaListDto> provinceList = businessService
					.getProvinceList(null);
			List<BusinessAreaListDto> cityList = businessService
					.getCityList(null);
			List<List<BusinessAreaListDto>> back = new ArrayList<List<BusinessAreaListDto>>();
			back.add(provinceList);
			back.add(cityList);
			return representation(Message.SUCCESS, back);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
