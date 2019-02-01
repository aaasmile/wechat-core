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
import com.d1m.wechat.dto.DcrmImageTextDetailDto;
import com.d1m.wechat.dto.ImageTextDto;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.dto.MiniProgramDto;
import com.d1m.wechat.dto.QrcodeActionEngineDto;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.QrcodeActionEngine;
import com.d1m.wechat.model.enums.Effect;
import com.d1m.wechat.pamametermodel.ActionEngineCondition;
import com.d1m.wechat.pamametermodel.ActionEngineEffect;
import com.d1m.wechat.pamametermodel.ActionEngineModel;
import com.d1m.wechat.pamametermodel.QrcodeModel;
import com.d1m.wechat.service.DcrmImageTextDetailService;
import com.d1m.wechat.service.MaterialImageTextDetailService;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.service.QrcodeActionEngineService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import tk.mybatis.mapper.entity.Example;

@Api(value = "二维码行为API", tags = "二维码行为接口")
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
	@Autowired
    private DcrmImageTextDetailService DcrmImageTextDetailService;
	@Autowired
	private MaterialImageTextDetailService materialImageTextDetailService;

	@ApiOperation(value = "创建二维码行为规则", tags = "二维码行为接口")
	@ApiResponse(code = 200, message = "1-创建二维码行为规则成功")
	@RequestMapping(value = "new.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(@ApiParam("QrcodeModel") @RequestBody(required = false) QrcodeModel qrcodeModel, HttpSession session) {
		try {
			QrcodeActionEngine qae = qrcodeActionEngineService.create(getWechatId(session), getUser(session), qrcodeModel);
			QrcodeActionEngineDto dto = new QrcodeActionEngineDto();
			dto.setId(qae.getId());
			return representation(Message.QRCODE_ACTION_ENGINE_CREATE_SUCCESS, dto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "更新二维码行为规则", tags = "二维码行为接口")
	@ApiResponse(code = 200, message = "1-更新二维码行为规则成功")
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(@ApiParam("QrcodeModel") @RequestBody(required = false) QrcodeModel qrcodeModel, HttpSession session) {
		try {
			qrcodeActionEngineService.update(getWechatId(session), getUser(session), qrcodeModel);
			return representation(Message.QRCODE_ACTION_ENGINE_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "删除二维码行为规则", tags = "二维码行为接口")
	@ApiResponse(code = 200, message = "1-删除二维码行为规则成功")
	@RequestMapping(value = "{qrcodeActionEngineId}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject delete(@ApiParam("二维码行为规则ID") @PathVariable Integer qrcodeActionEngineId, HttpSession session) {
		try {
			qrcodeActionEngineService.delete(getWechatId(session), getUser(session), qrcodeActionEngineId);
			return representation(Message.QRCODE_ACTION_ENGINE_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "获取二维码行为规则列表", tags = "二维码行为接口")
	@ApiResponse(code = 200, message = "1-获取二维码行为规则列表成功")
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject list(@ApiParam("ActionEngineModel") @RequestBody(required = false) ActionEngineModel actionEngineModel, HttpSession session) {
		try {
			if (actionEngineModel == null) {
				actionEngineModel = new ActionEngineModel();
			}
			Integer wechatId = getWechatId();
			Page<QrcodeActionEngineDto> page = qrcodeActionEngineService.search(wechatId, actionEngineModel, false);
			JSONArray array = new JSONArray();
			List<QrcodeActionEngineDto> result = page.getResult();
			for (QrcodeActionEngineDto dto : result) {
				array.add(convert(wechatId, dto));
			}
			JSONObject json = new JSONObject();
			json.put("rules", array);
			return representation(Message.QRCODE_ACTION_ENGINE_LIST_SUCCESS, json, actionEngineModel.getPageSize(), actionEngineModel.getPageNum(), page.getTotal());
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
			ActionEngineCondition parseObject = JSONObject.parseObject(dto.getConditions(), ActionEngineCondition.class);
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
		log.info("convert...effect..." + dto.getEffect());
		if (StringUtils.isNotBlank(dto.getEffect())) {
			List<ActionEngineEffect> qrcodeActionEngineEffects = JSONObject.parseArray(dto.getEffect(), ActionEngineEffect.class);
			JSONArray codeArray = new JSONArray();
			JSONObject codeJson = null;
			if (qrcodeActionEngineEffects != null) {
				JSONArray valueArray = null;
				JSONObject valueJson = null;
				for (ActionEngineEffect qae : qrcodeActionEngineEffects) {
					Integer[] value = qae.getValue();
					if (qae.getCode().byteValue() == Effect.ADD_MEMBER_TAG.getValue()) {
						log.info("convert...effect..." + Effect.ADD_MEMBER_TAG.toString());
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
					} else if (qae.getCode().byteValue() == Effect.SEND_IMAGE_TEXT.getValue()) {
						log.info("convert...effect..." + Effect.SEND_IMAGE_TEXT.toString());
						valueArray = new JSONArray();
						for (Integer id : value) {
							try {
								final Example example = new Example(MaterialImageTextDetail.class);
								example.createCriteria().andEqualTo("id", id).andEqualTo("status",(byte) 1);
								List<MaterialImageTextDetail> materialImageTextDetailList = materialImageTextDetailService.selectByExample(example);
								if(materialImageTextDetailList == null || materialImageTextDetailList.isEmpty()) {
									continue;
								}
								MaterialImageTextDetail detailDto = materialImageTextDetailList.get(0);
								Material material = materialService.selectByKey(detailDto.getMaterialCoverId());
								MaterialDto materialDto = new MaterialDto();
								ImageTextDto imageTextDto = new ImageTextDto();
								List<ImageTextDto> items = new ArrayList<ImageTextDto>();
								if(detailDto != null) {
									materialDto.setId(detailDto.getId());
									materialDto.setTitle(material.getTitle());
									
									imageTextDto.setAuthor("");
									imageTextDto.setContent(detailDto.getContent());
									imageTextDto.setSummary(detailDto.getSummary());
									imageTextDto.setTitle(detailDto.getTitle());
									imageTextDto.setMaterialCoverUrl(material.getPicUrl());
									imageTextDto.setContentSourceUrl(detailDto.getContentSourceUrl());
									items.add(imageTextDto);
									
									materialDto.setItems(items);
									valueArray.add(materialDto);
								}
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}
						}
					} else if (qae.getCode().byteValue() == Effect.SEND_IMAGE.getValue()) {
						log.info("convert...effect..." + Effect.SEND_IMAGE.toString());
						Material material = null;
						for (Integer id : value) {
							valueArray = new JSONArray();
							material = materialService.getMaterial(wechatId, id);
							if (material != null) {
								valueJson = new JSONObject();
								valueJson.put("id", material.getId());
								valueJson.put("url", material.getPicUrl());
								valueArray.add(valueJson);
							}
						}
					} else if (qae.getCode().byteValue() == Effect.SEND_MINI_PROGRAM.getValue()) {
						log.info("convert...effect..." + Effect.SEND_MINI_PROGRAM.toString());
						MiniProgramDto miniProgramDto;
						for (Integer id : value) {
							valueArray = new JSONArray();
							miniProgramDto = materialService.getMiniProgramByMaterialId(wechatId, id);
							if (miniProgramDto != null) {
								valueJson = new JSONObject();
								valueJson.put("id", miniProgramDto.getId());
								valueJson.put("thumbUrl", miniProgramDto.getThumbUrl());
								valueArray.add(valueJson);
							}
						}
					} else if (qae.getCode().byteValue() == Effect.SEND_TEXT.getValue()) {
						log.info("convert...effect..." + Effect.SEND_TEXT.toString());
						valueArray = new JSONArray();
					} else if(qae.getCode().byteValue() == Effect.SEND_DCRM_IMAGE_TEXT.getValue()) {
						log.info("convert...effect..." + Effect.SEND_DCRM_IMAGE_TEXT.toString());
						valueArray = new JSONArray();
						for (Integer id : value) {
							try {
								DcrmImageTextDetailDto detailDto = DcrmImageTextDetailService.queryObject(id);
								if(detailDto == null) {
									continue;
								}
								MaterialDto materialDto = new MaterialDto();
								ImageTextDto imageTextDto = new ImageTextDto();
								List<ImageTextDto> items = new ArrayList<ImageTextDto>();
								if(detailDto != null) {
									materialDto.setId(detailDto.getMaterialCoverId());
									materialDto.setTitle(detailDto.getTitle());
									
									imageTextDto.setAuthor("");
									imageTextDto.setContent(detailDto.getContent());
									imageTextDto.setSummary(detailDto.getSummary());
									imageTextDto.setTitle(detailDto.getTitle());
									imageTextDto.setMaterialCoverUrl(detailDto.getCoverPicUrl());
									imageTextDto.setContentSourceUrl(detailDto.getLink());
									items.add(imageTextDto);
									
									materialDto.setItems(items);
									valueArray.add(materialDto);
								}
							} catch (Exception e) {
								log.error(e.getMessage(), e);
							}
						}
					}
					else {
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
