package com.d1m.wechat.controller.reply;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.dto.MiniProgramDto;
import com.d1m.wechat.dto.ReplyActionEngineDto;
import com.d1m.wechat.model.Material;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.ReplyActionEngine;
import com.d1m.wechat.model.enums.Effect;
import com.d1m.wechat.pamametermodel.ActionEngineCondition;
import com.d1m.wechat.pamametermodel.ActionEngineEffect;
import com.d1m.wechat.pamametermodel.ActionEngineModel;
import com.d1m.wechat.pamametermodel.ReplyModel;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.service.ReplyActionEngineService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@Api(value = "自动回复行为规则API", tags = "自动回复行为规则接口")
@Controller
@RequestMapping("/reply-action-engine")
public class ReplyActionEngineController extends BaseController {

    private static final Logger log = LoggerFactory.getLogger(ReplyActionEngineController.class);

    @Resource
    private ReplyActionEngineService replyActionEngineService;

    @Resource
    private MaterialService materialService;

    @Resource
    private MemberTagService memberTagService;

    @ApiOperation(value = "创建自动回复行为规则", tags = "自动回复行为规则接口")
    @ApiResponse(code = 200, message = "1-创建自动回复行为规则成功")
    @RequestMapping(value = "new.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject create(
            @ApiParam("ReplyModel")
            @RequestBody(required = false) ReplyModel replyModel,
            HttpSession session) {
        try {
            ReplyActionEngine rae = replyActionEngineService.create(
                    getWechatId(session), getUser(session), replyModel);
            ReplyActionEngineDto dto = new ReplyActionEngineDto();
            dto.setId(rae.getId());
            return representation(Message.REPLY_ACTION_ENGINE_CREATE_SUCCESS,
                    dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "更新自动回复行为规则", tags = "自动回复行为规则接口")
    @ApiResponse(code = 200, message = "1-更新自动回复行为规则成功")
    @RequestMapping(value = "update.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(
            @ApiParam("ReplyModel")
            @RequestBody(required = false) ReplyModel replyModel,
            HttpSession session) {
        try {
            replyActionEngineService.update(getWechatId(session),
                    getUser(session), replyModel);
            return representation(Message.REPLY_ACTION_ENGINE_UPDATE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "删除自动回复行为规则", tags = "自动回复行为规则接口")
    @ApiResponse(code = 200, message = "1-删除自动回复行为规则成功")
    @RequestMapping(value = "{replyActionEngineId}/delete.json", method = RequestMethod.DELETE)
    @ResponseBody
    public JSONObject delete(
            @ApiParam("回复行为规则ID")
            @PathVariable Integer replyActionEngineId,
            HttpSession session) {
        try {
            replyActionEngineService.delete(getWechatId(session),
                    getUser(session), replyActionEngineId);
            return representation(Message.REPLY_ACTION_ENGINE_DELETE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "获取自动回复行为规则列表", tags = "自动回复行为规则接口")
    @ApiResponse(code = 200, message = "1-获取自动回复行为规则列表成功")
    @RequestMapping(value = "list.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject list(
            @ApiParam("ActionEngineModel")
            @RequestBody(required = false) ActionEngineModel actionEngineModel,
            HttpSession session) {
        try {
            if (actionEngineModel == null) {
                actionEngineModel = new ActionEngineModel();
            }
            Integer wechatId = getWechatId(session);
            Page<ReplyActionEngineDto> page = replyActionEngineService.search(
                    wechatId, actionEngineModel, false);
            JSONArray array = new JSONArray();
            List<ReplyActionEngineDto> result = page.getResult();
            for (ReplyActionEngineDto dto : result) {
                array.add(convert(wechatId, dto));
            }
            JSONObject json = new JSONObject();
            json.put("rules", array);
            return representation(Message.REPLY_ACTION_ENGINE_LIST_SUCCESS,
                    json, actionEngineModel.getPageSize(),
                    actionEngineModel.getPageNum(), page.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    public JSONObject convert(Integer wechatId, ReplyActionEngineDto dto) {
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
                                    wechatId, id, true);
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
