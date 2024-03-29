package com.d1m.wechat.controller.activity;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletOutputStream;
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
import com.d1m.wechat.dto.ActivityQrcodeDto;
import com.d1m.wechat.model.ActivityQrcode;
import com.d1m.wechat.pamametermodel.ActivityModel;
import com.d1m.wechat.pamametermodel.ActivityQrcodeModel;
import com.d1m.wechat.service.ActivityQrcodeService;
import com.d1m.wechat.service.ActivityService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping("/activity-qrcode")
@Api(value="活动二维码API", tags="活动二维码接口")
public class ActivityQrcodeController extends BaseController {

	private Logger log = LoggerFactory
			.getLogger(ActivityQrcodeController.class);

	@Autowired
	private ActivityQrcodeService activityQrcodeService;

	@Autowired
	private ActivityService activityService;
	
	@ApiOperation(value="查询活动二维码列表", tags="活动二维码接口")
	@ApiResponse(code=200, message="1-活动二维码列表成功, 0-系统异常")
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
			Page<ActivityQrcodeDto> page = activityQrcodeService.search(
					getWechatId(session), model, true);
			return representation(Message.ACTIVITY_QRCODE_LIST_SUCCESS,
					page.getResult(), model.getPageNum(), model.getPageSize(),
					page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@ApiOperation(value="根据ID下载活动二维码", tags="活动二维码接口")
	@RequestMapping(value = "{id}/download.json", method = RequestMethod.GET)
	public void download(
			@ApiParam("活动二维码ID")
			@PathVariable Integer id,
			HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		ActivityQrcode activityQrcode = activityQrcodeService.get(
				getWechatId(session), id);
		if (activityQrcode != null) {
			String qrcodeImgUrl = activityQrcode.getQrcodeImgUrl();
			String fileName = qrcodeImgUrl.substring(qrcodeImgUrl
					.lastIndexOf("/") + 1);
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ fileName);
			InputStream inputStream = null;
			ServletOutputStream outputStream = null;
			try {
				URL url = new URL(qrcodeImgUrl);
				HttpURLConnection uc = (HttpURLConnection) url.openConnection();
				uc.setDoInput(true);// 设置是否要从 URL 连接读取数据,默认为true
				uc.connect();
				inputStream = uc.getInputStream();
				outputStream = response.getOutputStream();
				byte[] b = new byte[1024];
				int length;
				while ((length = inputStream.read(b)) > 0) {
					outputStream.write(b, 0, length);
				}
				inputStream.close();
			} catch (Exception e) {
				log.error(e.getMessage());
			} finally {
				if (inputStream != null) {
					try {
						inputStream.close();
					} catch (IOException e) {
						log.error(e.getMessage());
					}
				}
				if (outputStream != null) {
					try {
						outputStream.close();
					} catch (IOException e) {
						log.error(e.getMessage());
					}
				}
			}
		}
	}
	
	@ApiOperation(value="根据ID删除活动二维码", tags="活动二维码接口")
	@ApiResponse(code=200, message="1-活动二维码删除成功, 0-系统异常")
	@RequestMapping(value = "{activityQrcodeId}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject delete(
			@ApiParam("活动二维码ID")
			@PathVariable Integer activityQrcodeId,
			HttpSession session) {
		try {
			activityQrcodeService.delete(getWechatId(session),
					getUser(session), activityQrcodeId);
			return representation(Message.ACTIVITY_QRCODE_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value="创建新的活动二维码", tags="活动二维码接口")
	@ApiResponse(code=200, message="1-活动二维码创建成功, 0-系统异常")
	@RequestMapping(value = "create.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(
			@ApiParam("ActivityModel")
			@RequestBody(required = false) ActivityQrcodeModel model,
			HttpSession session) {
		try {
			if (model == null) {
				model = new ActivityQrcodeModel();
			}
			activityService.createActivityQrcode(getWechatId(session),
					getUser(session), model);
			return representation(Message.ACTIVITY_QRCODE_CREATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
}
