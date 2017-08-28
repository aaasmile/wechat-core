package com.d1m.wechat.controller.qrcode;

import java.util.ArrayList;
import java.util.List;

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

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.dto.QrcodeActionEngineDto;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.QrcodeActionEngine;
import com.d1m.wechat.model.enums.Effect;
import com.d1m.wechat.pamametermodel.ActionEngineCondition;
import com.d1m.wechat.pamametermodel.ActionEngineEffect;
import com.d1m.wechat.pamametermodel.ActionEngineModel;
import com.d1m.wechat.pamametermodel.QrcodeModel;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.service.QrcodeActionEngineService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("/qrcode-action-engine")
public class QrcodeActionEngineController extends BaseController {

	Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private QrcodeActionEngineService qrcodeActionEngineService;

	@Autowired
	private MemberTagService memberTagService;

	@Autowired
	private MaterialService materialService;

	@RequestMapping(value = "new.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(
			@RequestBody(required = false) QrcodeModel qrcodeModel,
			HttpSession session) {
		try {
			QrcodeActionEngine qae = qrcodeActionEngineService.create(
					getWechatId(session), getUser(session), qrcodeModel);
			QrcodeActionEngineDto dto = new QrcodeActionEngineDto();
			dto.setId(qae.getId());
			return representation(Message.QRCODE_ACTION_ENGINE_CREATE_SUCCESS,
					dto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(
			@RequestBody(required = false) QrcodeModel qrcodeModel,
			HttpSession session) {
		try {
			qrcodeActionEngineService.update(getWechatId(session),
					getUser(session), qrcodeModel);
			return representation(Message.QRCODE_ACTION_ENGINE_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{qrcodeActionEngineId}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject delete(@PathVariable Integer qrcodeActionEngineId,
			HttpSession session) {
		try {
			qrcodeActionEngineService.delete(getWechatId(session),
					getUser(session), qrcodeActionEngineId);
			return representation(Message.QRCODE_ACTION_ENGINE_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject list(
			@RequestBody(required = false) ActionEngineModel actionEngineModel,
			HttpSession session) {
		try {
			if (actionEngineModel == null) {
				actionEngineModel = new ActionEngineModel();
			}
			Integer wechatId = getWechatId(session);
			Page<QrcodeActionEngineDto> page = qrcodeActionEngineService
					.search(wechatId, actionEngineModel, false);
			JSONArray array = new JSONArray();
			List<QrcodeActionEngineDto> result = page.getResult();
			for (QrcodeActionEngineDto dto : result) {
				array.add(convert(wechatId, dto));
			}
			JSONObject json = new JSONObject();
			json.put("rules", array);
			return representation(Message.QRCODE_ACTION_ENGINE_LIST_SUCCESS,
					json, actionEngineModel.getPageSize(),
					actionEngineModel.getPageNum(), page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	public JSONObject convert(Integer wechatId, QrcodeActionEngineDto dto) {
		JSONObject json = new JSONObject();
		json.put("id", dto.getId());
		json.put("name", dto.getName());
		json.put("start_at", DateUtil.formatYYYYMMDDHHMMSS(dto.getStart_at()));
		json.put("end_at", DateUtil.formatYYYYMMDDHHMMSS(dto.getEnd_at()));
		if (StringUtils.isNotBlank(dto.getConditions())) {
			ActionEngineCondition parseObject = JSONObject.parseObject(
					dto.getConditions(), ActionEngineCondition.class);
			if (parseObject != null) {
				Integer[] memberTagIds = parseObject.getMemberTagIds();
				if (memberTagIds != null && memberTagIds.length > 0) {
					List<MemberTagDto> memberTagDtos = new ArrayList<MemberTagDto>();
					MemberTag memberTag = null;
					MemberTagDto memberTagDto = null;
					for (Integer memberTagId : memberTagIds) {
						memberTag = memberTagService.get(wechatId, memberTagId);
						memberTagDto = new MemberTagDto();
						memberTagDto.setId(memberTag.getId());
						memberTagDto.setName(memberTag.getName());
						memberTagDtos.add(memberTagDto);
					}
					parseObject.setMemberTags(memberTagDtos);
					parseObject.setMemberTagIds(null);
				}
				json.put("condition", parseObject);
			}
		}
		if (StringUtils.isNotBlank(dto.getEffect())) {
			List<ActionEngineEffect> qrcodeActionEngineEffects = JSONObject
					.parseArray(dto.getEffect(), ActionEngineEffect.class);
			JSONArray codeArray = new JSONArray();
			JSONObject codeJson = null;
			if (qrcodeActionEngineEffects != null) {
				JSONArray valueArray = null;
				JSONObject valueJson = null;
				for (ActionEngineEffect qae : qrcodeActionEngineEffects) {
					Integer[] value = qae.getValue();
					if (qae.getCode().byteValue() == Effect.ADD_MEMBER_TAG
							.getValue()) {
						valueArray = new JSONArray();
						MemberTag memberTag = null;
						for (Integer id : value) {
							memberTag = memberTagService.get(wechatId, id);
							if (memberTag != null) {
								valueJson = new JSONObject();
								valueJson.put("id", memberTag.getId());
								valueJson.put("name", memberTag.getName());
								valueArray.add(valueJson);
							}
						}
					} else if (qae.getCode().byteValue() == Effect.SEND_IMAGE_TEXT
							.getValue()) {
						valueArray = new JSONArray();
						MaterialDto materialDto = null;
						for (Integer id : value) {
							materialDto = materialService.getImageText(
									wechatId, id);
							if (materialDto != null) {
								valueArray.add(materialDto);
							}
						}
					} else if (qae.getCode().byteValue() == Effect.SEND_IMAGE
							.getValue()) {
						Material material = null;
						for (Integer id : value) {
							valueArray = new JSONArray();
							material = materialService
									.getMaterial(wechatId, id);
							if (material != null) {
								valueJson = new JSONObject();
								valueJson.put("id", material.getId());
								valueJson.put("url", material.getPicUrl());
								valueArray.add(valueJson);
							}
						}
					} else if (qae.getCode().byteValue() == Effect.SEND_TEXT
							.getValue()) {
						valueArray = new JSONArray();
					} else {
						continue;
					}
					codeJson = new JSONObject();
					codeJson.put("code", qae.getCode());
					codeJson.put("value", valueArray);
					codeJson.put("content", qae.getContent());
					codeArray.add(codeJson);
				}
				json.put("effect", codeArray);
			}
		}
		return json;
	}

}
