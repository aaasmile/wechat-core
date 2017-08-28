package com.d1m.wechat.controller.business;

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
import com.d1m.wechat.dto.BusinessCategoryDto;
import com.d1m.wechat.service.BusinessCategoryService;
import com.d1m.wechat.util.Message;

@Controller
@RequestMapping("/business-category")
public class BusinessCategoryController extends BaseController {

	private Logger log = LoggerFactory
			.getLogger(BusinessCategoryController.class);

	@Autowired
	BusinessCategoryService businessCategoryService;

	/*@RequestMapping(value = "create.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(@RequestParam(required = true) String name,
			@RequestParam(required = false) Integer parentId,
			HttpSession session) {
		try {
			businessCategoryService.create(name, parentId);
			return representation(Message.BUSINESS_CATEGORY_CREATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "list.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject list(
			@RequestParam(required = false) String name,
			@RequestParam(required = false) Integer parentId,
			@RequestParam(required = false) String sortName,
			@RequestParam(required = false) String sortDir,
			@RequestParam(required = false, defaultValue = "10") Integer pageSize,
			@RequestParam(required = false, defaultValue = "1") Integer pageNum,
			HttpSession session) {
		try {
			Page<BusinessCategoryDto> page = businessCategoryService.list(name,
					parentId, sortName, sortDir, pageSize, pageNum);
			return representation(Message.BUSINESS_CATEGORY_LIST_SUCCESS,
					page.getResult(), pageSize, pageNum, page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}*/

	/**
	 * @RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
	 * @ResponseBody public JSONObject update(@PathVariable Integer id,
	 * @RequestParam String name, HttpSession session) { try {
	 *               businessCategoryService.update(getWechatId(session), id,
	 *               name); return
	 *               representation(Message.BUSINESS_CATEGORY_UPDATE_SUCCESS); }
	 *               catch (Exception e) { log.error(e.getMessage()); return
	 *               wrapException(e); } }
	 */
	
	@RequestMapping(value = "business-category-list.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject list(HttpServletRequest request,
			HttpServletResponse response, HttpSession session) {
		try {
			List<BusinessCategoryDto> list = businessCategoryService.list();
			return representation(Message.BUSINESS_CATEGORY_LIST_SUCCESS, list);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
