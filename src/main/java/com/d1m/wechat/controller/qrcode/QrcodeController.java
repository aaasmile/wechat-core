package com.d1m.wechat.controller.qrcode;

import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.shiro.authz.annotation.RequiresPermissions;
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
import com.d1m.wechat.dto.QrcodeDto;
import com.d1m.wechat.model.Qrcode;
import com.d1m.wechat.pamametermodel.QrcodeModel;
import com.d1m.wechat.service.QrcodeService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("/qrcode")
public class QrcodeController extends BaseController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QrcodeService qrcodeService;

	@RequestMapping(value = "{id}/download.json", method = RequestMethod.GET)
	public void download(@PathVariable Integer id,
			HttpServletResponse response, HttpSession session) {
		response.setCharacterEncoding("utf-8");
		response.setContentType("multipart/form-data");
		QrcodeDto qrcodeDto = qrcodeService.get(getWechatId(session), id);
		if (qrcodeDto != null) {
			String fileName = qrcodeDto.getQrcodeUrl().substring(
					qrcodeDto.getQrcodeUrl().lastIndexOf("/") + 1);
			response.setHeader("Content-Disposition", "attachment;fileName="
					+ fileName);
			InputStream inputStream = null;
			ServletOutputStream outputStream = null;
			try {
				URL url = new URL(qrcodeDto.getQrcodeUrl());
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

	@RequestMapping(value = "new.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("qrCode:list")
	public JSONObject create(
			@RequestBody(required = false) QrcodeModel qrcodeModel,
			HttpSession session) {
		try {
			Qrcode qrcode = qrcodeService.create(getWechatId(session),
					getUser(session), qrcodeModel);
			QrcodeDto qrcodeDto = qrcodeService.get(qrcode.getWechatId(),
					qrcode.getId());
			return representation(Message.QRCODE_CREATE_SUCCESS, qrcodeDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{id}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	@RequiresPermissions("qrCode:list")
	public JSONObject delete(@PathVariable Integer id, HttpSession session) {
		try {
			qrcodeService.delete(getWechatId(session), id);
			return representation(Message.QRCODE_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{id}/get.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("qrCode:list")
	public JSONObject get(@PathVariable Integer id, HttpSession session) {
		try {
			QrcodeDto qrcode = qrcodeService.get(getWechatId(session), id);
			return representation(Message.QRCODE_GET_SUCCESS, qrcode);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("qrCode:list")
	public JSONObject list(
			@RequestBody(required = false) QrcodeModel qrcodeModel,
			HttpSession session) {
		try {
			Page<QrcodeDto> page = qrcodeService.list(getWechatId(session),
					qrcodeModel);
			return representation(Message.QRCODE_LIST_SUCCESS,
					page.getResult(), qrcodeModel.getPageSize(),
					qrcodeModel.getPageNum(), page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("qrCode:list")
	public JSONObject update(@PathVariable Integer id,
			@RequestBody(required = false) QrcodeModel qrcodeModel,
			HttpSession session) {
		try {
			qrcodeService.update(getWechatId(session), id, qrcodeModel);
			return representation(Message.QRCODE_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@RequestMapping(value = "init-qrcode.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject initQrcode(HttpSession session) {
		try {
			qrcodeService.init(getWechatId(session), getUser(session));
			return representation(Message.QRCODE_INIT_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
