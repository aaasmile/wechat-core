package com.d1m.wechat.controller.activity;

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
import com.d1m.wechat.dto.ActivityDto;
import com.d1m.wechat.model.Activity;
import com.d1m.wechat.pamametermodel.ActivityModel;
import com.d1m.wechat.service.ActivityService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("/activity")
public class ActivityController extends BaseController {

	private Logger log = LoggerFactory.getLogger(ActivityController.class);

	@Autowired
	private ActivityService activityService;

	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject list(@RequestBody(required = false) ActivityModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if (model == null) {
				model = new ActivityModel();
			}
			Page<ActivityDto> page = activityService.search(
					getWechatId(session), model, true);
			return representation(Message.ACTIVITY_LIST_SUCCESS,
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
			ActivityDto activityDto = activityService.get(getWechatId(session),
					id);
			return representation(Message.ACTIVITY_GET_SUCCESS, activityDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{id}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject delete(@PathVariable Integer id, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			activityService.delete(getWechatId(session), id);
			return representation(Message.ACTIVITY_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "new.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(
			@RequestBody(required = false) ActivityModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			Activity activity = activityService.create(getWechatId(session),
					getUser(session), model);
			Activity record = new Activity();
			record.setId(activity.getId());
			return representation(Message.ACTIVITY_CREATE_SUCCESS, record);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(
			@RequestBody(required = false) ActivityModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			activityService.update(getWechatId(session), getUser(session),
					model);
			return representation(Message.ACTIVITY_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
