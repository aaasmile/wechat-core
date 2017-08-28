package com.d1m.wechat.controller.qrcode;

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
import com.d1m.wechat.dto.QrcodeDto;
import com.d1m.wechat.dto.QrcodeTypeDto;
import com.d1m.wechat.model.QrcodeType;
import com.d1m.wechat.pamametermodel.QrcodeTypeModel;
import com.d1m.wechat.service.QrcodeTypeService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("/qrcode-type")
public class QrcodeTypeController extends BaseController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	QrcodeTypeService qrcodeTypeService;

	@RequestMapping(value = "new.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(
			@RequestBody(required = false) QrcodeTypeModel qrcodeTypeModel,
			HttpSession session) {
		try {
			QrcodeType qrcode = qrcodeTypeService.create(getWechatId(session),
					getUser(session), qrcodeTypeModel);
			QrcodeDto qrcodeDto = new QrcodeDto();
			qrcodeDto.setId(qrcode.getId());
			qrcodeDto.setName(qrcode.getName());
			return representation(Message.QRCODE_TYPE_CREATE_SUCCESS, qrcodeDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{id}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject delete(@PathVariable Integer id, HttpSession session) {
		try {
			qrcodeTypeService.delete(getWechatId(session), id);
			return representation(Message.QRCODE_TYPE_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject list(
			@RequestBody(required = false) QrcodeTypeModel qrcodeTypeModel,
			HttpSession session) {
		try {
			if (qrcodeTypeModel == null) {
				qrcodeTypeModel = new QrcodeTypeModel();
			}
			Page<QrcodeTypeDto> page = qrcodeTypeService.list(
					getWechatId(session), qrcodeTypeModel);
			return representation(Message.QRCODE_TYPE_LIST_SUCCESS,
					page.getResult(), qrcodeTypeModel.getPageSize(),
					qrcodeTypeModel.getPageNum(), page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(@PathVariable Integer id,
			@RequestBody(required = false) QrcodeTypeModel qrcodeTypeModel,
			HttpSession session) {
		try {
			qrcodeTypeModel.setId(id);
			qrcodeTypeService.update(getWechatId(session), qrcodeTypeModel);
			return representation(Message.QRCODE_TYPE_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
}
