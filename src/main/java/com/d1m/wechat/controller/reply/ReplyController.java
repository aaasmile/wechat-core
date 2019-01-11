package com.d1m.wechat.controller.reply;

import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.*;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MaterialImageTextDetail;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.Reply;
import com.d1m.wechat.model.enums.Effect;
import com.d1m.wechat.pamametermodel.ActionEngineCondition;
import com.d1m.wechat.pamametermodel.ActionEngineEffect;
import com.d1m.wechat.pamametermodel.ReplyModel;
import com.d1m.wechat.service.DcrmImageTextDetailService;
import com.d1m.wechat.service.MaterialImageTextDetailService;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.service.ReplyService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import tk.mybatis.mapper.entity.Example;

@Api(value = "自动回复API", tags = "自动回复接口")
@Controller
@RequestMapping("/reply")
public class ReplyController extends BaseController {

	private Logger log = LoggerFactory.getLogger(this.getClass());

	@Autowired
	private ReplyService replyService;

	@Autowired
	private MemberTagService memberTagService;

	@Autowired
	private MaterialService materialService;
	@Autowired
    private DcrmImageTextDetailService DcrmImageTextDetailService;
	@Autowired
	private MaterialImageTextDetailService materialImageTextDetailService;

	@ApiOperation(value = "创建自动回复", tags = "自动回复接口")
	@ApiResponse(code = 200, message = "1-创建自动回复成功")
	@RequestMapping(value = "new.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:auto-reply")
	public JSONObject create(@ApiParam("ReplyModel") @RequestBody(required = false) ReplyModel replyModel, HttpSession session) {
		try {
			Reply reply = replyService.create(getWechatId(session), getUser(session), replyModel);
			ReplyDto replyDto = new ReplyDto();
			replyDto.setId(reply.getId());
			return representation(Message.REPLY_CREATE_SUCCESS, replyDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "删除自动回复", tags = "自动回复接口")
	@ApiResponse(code = 200, message = "1-删除自动回复成功")
	@RequestMapping(value = "{id}/delete.json", method = RequestMethod.DELETE)
	@ResponseBody
	@RequiresPermissions("system-setting:auto-reply")
	public JSONObject delete(@ApiParam("回复ID") @PathVariable Integer id, HttpSession session) {
		try {
			replyService.delete(getWechatId(session), id);
			return representation(Message.REPLY_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "获取自动回复详情", tags = "自动回复接口")
	@ApiResponse(code = 200, message = "1-获取自动回复详情成功")
	@RequestMapping(value = "{id}/get.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:auto-reply")
	public JSONObject get(@ApiParam("回复ID") @PathVariable Integer id, HttpSession session) {
		try {
			Integer wechatId = getWechatId();
			ReplyDto replyDto = replyService.get(getWechatId(), id);
			JSONObject json = convertReply(wechatId, replyDto);
			return representation(Message.REPLY_GET_SUCCESS, json);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	private JSONObject convertReply(Integer wechatId, ReplyDto replyDto) {
		List<ReplyActionEngineDto> rules = replyDto.getRules();
		JSONArray rulesArray = new JSONArray();
		if (rules != null && !rules.isEmpty()) {
			for (ReplyActionEngineDto replyActionEngineDto : rules) {
				rulesArray.add(convertRules(wechatId, replyActionEngineDto));
			}
		}
		JSONObject json = new JSONObject();
		json.put("rules", rulesArray);
		json.put("createdAt", replyDto.getCreatedAt());
		json.put("id", replyDto.getId());
		json.put("matchMode", replyDto.getMatchMode());
		json.put("name", replyDto.getName());
		json.put("replyType", replyDto.getReplyType());
		json.put("weight", replyDto.getWeight());
		json.put("keyWords", replyDto.getKeyWords());
		return json;
	}

	@ApiOperation(value = "更新自动回复", tags = "自动回复接口")
	@ApiResponse(code = 200, message = "1-更新自动回复成功")
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:auto-reply")
	public JSONObject list(@ApiParam("ReplyModel") @RequestBody(required = false) ReplyModel replyModel, HttpSession session) {
		try {
			if (replyModel == null) {
				replyModel = new ReplyModel();
			}
			Page<ReplyDto> page = replyService.search(getWechatId(session), replyModel, true);

			JSONArray array = new JSONArray();
			List<ReplyDto> result = page.getResult();
			Integer wechatId = getWechatId(session);
			for (ReplyDto dto : result) {
				array.add(convertReply(wechatId, dto));
			}
			return representation(Message.REPLY_LIST_SUCCESS, array, replyModel.getPageSize(), replyModel.getPageNum(), page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	public JSONObject convertRules(Integer wechatId, ReplyActionEngineDto dto) {
		JSONObject json = new JSONObject();
		json.put("id", dto.getId());
		json.put("name", dto.getName());
		json.put("start_at", DateUtil.formatYYYYMMDDHHMMSS(dto.getStart_at()));
		json.put("end_at", DateUtil.formatYYYYMMDDHHMMSS(dto.getEnd_at()));
		if (StringUtils.isNotBlank(dto.getConditions())) {
			ActionEngineCondition parseObject = JSONObject.parseObject(dto.getConditions(), ActionEngineCondition.class);
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
									materialDto.setId(material.getId());
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

	@ApiOperation(value = "更新自动回复", tags = "自动回复接口")
	@ApiResponse(code = 200, message = "1-更新自动回复成功")
	@RequestMapping(value = "update.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:auto-reply")
	public JSONObject update(@RequestBody(required = false) ReplyModel replyModel, HttpSession session) {
		try {
			replyService.update(getWechatId(session), getUser(session), replyModel);
			return representation(Message.REPLY_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "获取自动回复详情", tags = "自动回复接口")
	@ApiResponse(code = 200, message = "1-获取自动回复详情成功")
	@RequestMapping(value = "getsubscribe.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("system-setting:auto-reply")
	public JSONObject getsubscribe(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {
			ReplyDto subscribeReply = replyService.getSubscribeReply(getWechatId(session));
			JSONObject json = new JSONObject();
			if (subscribeReply != null) {
				json.put("id", subscribeReply.getId());
			}
			return representation(Message.REPLY_GET_SUCCESS, json);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
