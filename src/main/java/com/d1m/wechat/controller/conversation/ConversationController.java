package com.d1m.wechat.controller.conversation;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.*;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.*;
import com.d1m.wechat.model.enums.ConversationStatus;
import com.d1m.wechat.model.enums.MassConversationResultStatus;
import com.d1m.wechat.model.enums.MsgType;
import com.d1m.wechat.pamametermodel.ConversationModel;
import com.d1m.wechat.pamametermodel.MassConversationModel;
import com.d1m.wechat.service.*;
import com.d1m.wechat.util.CommonUtils;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static com.d1m.wechat.util.IllegalArgumentUtil.notBlank;

@Api(value = "会话API", tags = "会话接口")
@RestController
@RequestMapping("/conversation")
public class ConversationController extends BaseController {

    private Logger log = LoggerFactory.getLogger(ConversationController.class);

    @Autowired
    private ConversationService conversationService;

    @Autowired
    private MemberService memberService;

    @Autowired
    private MaterialService materialService;

    @Resource
    TenantHelper tenantHelper;
    @Autowired
    private MaterialImageTextDetailService materialImageTextDetailService;
    @Autowired
    private DcrmImageTextDetailService dcrmImageTextDetailService;

    @ApiOperation(value = "创建群发会话", tags = "会话接口")
    @ApiResponse(code = 200, message = "1-创建群发会话成功")
    @RequestMapping(value = "mass/new.json", method = RequestMethod.POST)
    @RequiresPermissions("member:list")
    public JSONObject createMass(@ApiParam(name = "MassConversationModel", required = false) @RequestBody(required = false) MassConversationModel massConversationModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            conversationService.preMassConversation(getWechatId(), getUser(), massConversationModel);
            return representation(Message.CONVERSATION_MASS_CREATE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "群发审核", tags = "会话接口")
    @ApiResponse(code = 200, message = "1-群发审核成功")
    @RequestMapping(value = "mass/audit.json", method = RequestMethod.POST)
    public JSONObject auditMass(@ApiParam(name = "MassConversationModel", required = false) @RequestBody(required = false) MassConversationModel massConversationModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            conversationService.auditMassConversation(getWechatId(session), getUser(session), massConversationModel);
            return representation(Message.CONVERSATION_MASS_AUDIT_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "群发发送", tags = "会话接口")
    @ApiResponse(code = 200, message = "1-群发发送成功")
    @RequestMapping(value = "mass/send.json", method = RequestMethod.POST)
    public JSONObject sendMass(@ApiParam(name = "MassConversationModel", required = false) @RequestBody(required = false) MassConversationModel massConversationModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            massConversationModel.setStatus(MassConversationResultStatus.AUDIT_PASS.name());
            conversationService.sendMassConversation(getWechatId(), getUser(), massConversationModel);
            return representation(Message.CONVERSATION_MASS_SEND_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "创建会话", tags = "会话接口")
    @ApiResponse(code = 200, message = "1-创建会话成功")
    @RequestMapping(value = "kfmember.json", method = RequestMethod.POST)
    public JSONObject create(@ApiParam(name = "ConversationModel", required = false) @RequestBody(required = false) ConversationModel conversationModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (conversationModel == null) {
                conversationModel = new ConversationModel();
            }
            notBlank(conversationModel.getMemberId(), Message.MEMBER_ID_NOT_EMPTY);
            MemberDto member = memberService.getMemberDto(getWechatId(), conversationModel.getMemberId());
            notBlank(member, Message.MEMBER_NOT_EXIST);
            Conversation conversation = null;
            //转发至social-wechat-core-api
            if (conversationModel.getNewid() != null) {
                CommonUtils.send2SocialWechatCoreApi(getWechatId(), member, conversationModel.getNewid(), conversationModel.getNewtype(), conversationService);
                conversation = new Conversation();
                conversation.setMsgType(MsgType.NEWS.getValue());
                conversation.setDirection(true);
                conversation.setWechatId(getWechatId());
                conversation.setUserId(getUser().getId());
                conversation.setMemberId(member.getId());
                conversation.setOpenId(member.getOpenId());
                conversation.setUnionId(member.getUnionId());
                conversation.setStatus(ConversationStatus.READ.getValue());
                conversation.setIsMass(false);
                if ("dcrm".equals(conversationModel.getNewtype())) {
                    DcrmImageTextDetailDto imageTextDto = dcrmImageTextDetailService.queryObject(conversationModel.getNewid());
                    JSONArray itemArray = new JSONArray();
                    JSONObject itemJson = new JSONObject();
                    itemJson.put("title", imageTextDto.getTitle());
                    itemJson.put("summary", imageTextDto.getSummary());
                    itemJson.put("materialCoverUrl", imageTextDto.getCoverPicUrl());
                    itemArray.add(itemJson);
                    conversation.setContent(itemArray.toJSONString());
                    conversation.setMaterialId(imageTextDto.getMaterialId());
                } else if ("wechat".equals(conversationModel.getNewtype())) {
                    MaterialImageTextDetail imageTextDto = materialImageTextDetailService.selectByKey(conversationModel.getNewid());
                    Material material = materialService.selectByKey(imageTextDto.getMaterialCoverId());
                    JSONArray itemArray = new JSONArray();
                    JSONObject itemJson = new JSONObject();
                    itemJson.put("title", imageTextDto.getTitle());
                    itemJson.put("summary", imageTextDto.getSummary());
                    itemJson.put("materialCoverUrl", material.getPicUrl());
                    itemArray.add(itemJson);
                    conversation.setContent(itemArray.toJSONString());
                    conversation.setMaterialId(imageTextDto.getMaterialId());
                }
            } else if (conversationModel.getNewid() == null && conversationModel.getMaterialId() == null && StringUtils.isBlank(conversationModel.getContent())) {
                throw new WechatException(Message.CONVERSATION_CONTENT_NOT_BLANK);
            }
            if (conversationModel.getNewid() == null) {
                conversation = conversationService.wechatToMember(getWechatId(), getUser(), conversationModel, member);
            }
            ConversationDto dto = new ConversationDto();
            if (conversation != null && conversation.getId() != null) {
                dto.setId(conversation.getId());
            }
            dto.setCreatedAt(DateUtil.formatYYYYMMDDHHMMSS(new Date()));
            dto.setContent(conversation.getContent());
            dto.setCurrent(DateUtil.formatYYYYMMDDHHMMSS(new Date()));
            dto.setDir(conversation.getDirection() ? 1 : 0);
            dto.setIsMass(conversation.getIsMass() ? 1 : 0);
            dto.setMemberId(member.getId() + "");
            dto.setMemberNickname(member.getNickname());
            dto.setMemberPhoto(member.getLocalHeadImgUrl());
            dto.setMsgType(conversation.getMsgType());
            dto.setEvent(conversation.getEvent());
            dto.setStatus(conversation.getStatus());
            dto.setMaterialId(conversation.getMaterialId());
            return representation(Message.CONVERSATION_CREATE_SUCCESS, dto);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "获取群发会话列表", tags = "会话接口")
    @ApiResponse(code = 200, message = "1-获取群发会话列表成功")
    @RequestMapping(value = "mass/list.json", method = RequestMethod.POST)
    @RequiresPermissions("message:batch-message-list")
    public JSONObject massList(@ApiParam(name = "ConversationModel", required = false) @RequestBody(required = false) ConversationModel conversationModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        if (conversationModel == null) {
            conversationModel = new ConversationModel();
        }
        Integer wechatId = getWechatId();
        Page<ConversationDto> page = conversationService.searchMass(wechatId, conversationModel, true);
        List<ConversationDto> result = convertMass(page, wechatId);
        return representation(Message.CONVERSATION_MASS_LIST_SUCCESS, result, conversationModel.getPageSize(), conversationModel.getPageNum(), page.getTotal());
    }

    @ApiOperation(value = "获取群发会话列表", tags = "会话接口")
    @ApiResponse(code = 200, message = "1-获取群发会话列表成功")
    @RequestMapping(value = "mass/auditlist.json", method = RequestMethod.POST)
    @RequiresPermissions("message:batch-message-review")
    public JSONObject massAuditList(@ApiParam(name = "ConversationModel", required = false) @RequestBody(required = false) ConversationModel conversationModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        if (conversationModel == null) {
            conversationModel = new ConversationModel();
        }
        // conversationModel.setStatus(MassConversationResultStatus.WAIT_AUDIT
        // .getValue());
        Integer wechatId = getWechatId();
        Page<ConversationDto> page = conversationService.searchMass(getWechatId(), conversationModel, true);
        List<ConversationDto> result = convertMass(page, wechatId);
        return representation(Message.CONVERSATION_MASS_LIST_SUCCESS, result, conversationModel.getPageSize(), conversationModel.getPageNum(), page.getTotal());
    }

    @ApiOperation(value = "获取本月群发会话可用数量", tags = "会话接口")
    @ApiResponse(code = 200, message = "1-获取本月群发会话可用数量成功")
    @RequestMapping(value = "massavailable.json", method = RequestMethod.POST)
    public JSONObject massavailable(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        Integer available = conversationService.countMassAvalible(getWechatId(session));
        JSONObject json = new JSONObject();
        json.put("available", available);
        return representation(Message.CONVERSATION_GET_MASS_AVAILABLE_SUCCESS, json);
    }

    @ApiOperation(value = "获取会话列表", tags = "会话接口")
    @ApiResponse(code = 200, message = "1-获取会话列表成功")
    @RequestMapping(value = "unread/list.json", method = RequestMethod.POST)
    @RequiresPermissions("message:message-list")
    public JSONObject listUnread(@ApiParam(name = "ConversationModel", required = false) @RequestBody(required = false) ConversationModel conversationModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        if (conversationModel == null) {
            conversationModel = new ConversationModel();
        }
        List<Byte> msgTypes = new ArrayList<Byte>();
        msgTypes.add(MsgType.IMAGE.getValue());
        msgTypes.add(MsgType.TEXT.getValue());
        msgTypes.add(MsgType.VOICE.getValue());
        conversationModel.setMsgTypes(msgTypes);
        Page<ConversationDto> page = conversationService.searchUnread(getWechatId(session), conversationModel, true);
        List<ConversationDto> result = convert(page);
        return representation(Message.CONVERSATION_LIST_SUCCESS, result, conversationModel.getPageSize(), conversationModel.getPageNum(), page.getTotal());
    }

    @ApiOperation(value = "获取会话列表", tags = "会话接口")
    @ApiResponse(code = 200, message = "1-获取会话列表成功")
    @RequestMapping(value = "list.json", method = RequestMethod.POST)
    public JSONObject list(@ApiParam(name = "ConversationModel", required = false) @RequestBody(required = false) ConversationModel conversationModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            if (conversationModel == null) {
                conversationModel = new ConversationModel();
            }
            conversationModel.setUpdateRead(true);
            Page<ConversationDto> page = conversationService.search(getWechatId(session), conversationModel, true);
            List<ConversationDto> result = convert(page);
            return representation(Message.CONVERSATION_LIST_SUCCESS, result, conversationModel.getPageSize(), conversationModel.getPageNum(), page.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "用户行为查询接口", tags = "用户行为查询接口")
    @ApiResponse(code = 200, message = "用户行为查询接口查询成功")
    @RequestMapping(value = "selectUserBehavior.json", method = RequestMethod.POST)
    public JSONObject selectUserBehavior(@ApiParam(name = "ConversationModel", required = false) @RequestBody(required = false) ConversationModel conversationModel, HttpSession session) {
        try {
            if (conversationModel.getMemberId() == null) {
                return this.representation(Message.CONVERSATION_LIST_FAIL, null);
            }
            PageHelper.startPage(conversationModel.getPageNum(), conversationModel.getPageSize(), true);
            Integer wechatId = getWechatId();
            Page<UserBehavior> userBehaviorPage = conversationService.selectUserBehavior(wechatId, conversationModel);
            return representation(Message.CONVERSATION_LIST_SUCCESS, userBehaviorPage.getResult(), userBehaviorPage.getPageSize(), userBehaviorPage.getPageNum(), userBehaviorPage.getTotal());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.CONVERSATION_LIST_FAIL);
        }
    }

    @ApiOperation(value = "用户位置查询接口", tags = "用户位置查询接口")
    @ApiResponse(code = 200, message = "用户位置查询接口查询成功")
    @RequestMapping(value = "selectUserLocation.json", method = RequestMethod.POST)
    public JSONObject selectUserLocation(@ApiParam(name = "ConversationModel", required = false) @RequestBody(required = false) ConversationModel conversationModel, HttpSession session) {
        try {
            if (conversationModel.getMemberId() == null) {
                return this.representation(Message.CONVERSATION_LIST_FAIL, null);
            }
            Integer wechatId = getWechatId();
//            PageHelper.startPage(conversationModel.getPageNum(), conversationModel.getPageSize(), true);
            List<UserLocation> userLocationPage = conversationService.selectUserLocation(wechatId, conversationModel);
            return this.representation(Message.CONVERSATION_LIST_SUCCESS, userLocationPage, 0, 0, userLocationPage.size());
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.CONVERSATION_LIST_FAIL);
        }
    }

    private List<ConversationDto> convertMass(Page<ConversationDto> page, Integer wechatId) {
        List<ConversationDto> result = page.getResult();
        ImageTextDto item = null;
        JSONObject itemJson = null;
        for (ConversationDto conversationDto : result) {
            MassConversationModel condition = JSONObject.parseObject(conversationDto.getConditions(), MassConversationModel.class);
            if (condition != null && condition.getNewid() != null) {
                List<ImageTextDto> itemDtos = new ArrayList<ImageTextDto>();
                item = new ImageTextDto();
                if ("dcrm".equals(condition.getNewtype())) {
                    DcrmImageTextDetailDto imageTextDto = dcrmImageTextDetailService.queryObject(condition.getNewid());
                    if (imageTextDto != null) {
                        item.setTitle(imageTextDto.getTitle());
                        item.setSummary(imageTextDto.getSummary());
                        item.setMaterialCoverUrl(imageTextDto.getCoverPicUrl());
                        item.setId(imageTextDto.getId());
                        conversationDto.setNewtype("dcrm");
                    }
                } else {
                    MaterialImageTextDetail imageTextDto = materialImageTextDetailService.selectByKey(condition.getNewid());
                    if (imageTextDto != null) {
                        Material material = materialService.selectByKey(imageTextDto.getMaterialCoverId());
                        item.setTitle(imageTextDto.getTitle());
                        item.setSummary(imageTextDto.getSummary());
                        item.setMaterialCoverUrl(material.getPicUrl());
                        item.setId(imageTextDto.getId());
                        conversationDto.setNewtype("wechat");
                    }
                }
                itemDtos.add(item);
                conversationDto.setItems(itemDtos);
                conversationDto.setContent(null);
                conversationDto.setMsgType(MsgType.MPNEWS.getValue());
                continue;
            }
            if (conversationDto.getMsgType() != MsgType.MPNEWS.getValue()) {
                continue;
            }
            if (conversationDto.getMaterialId() == null) {
                if (StringUtils.isNotBlank(conversationDto.getContent())) {
                    List<ImageTextDto> items = new ArrayList<ImageTextDto>();
                    itemJson = JSONArray.parseArray(conversationDto.getContent()).getJSONObject(0);
                    item = new ImageTextDto();
                    item.setTitle(itemJson.getString("title"));
                    item.setSummary(itemJson.getString("summary"));
                    item.setMaterialCoverUrl(itemJson.getString("materialCoverUrl"));
                    items.add(item);
                    if (conversationDto.getMaterialId() == null) {
                        conversationDto.setMaterialId(itemJson.getInteger("materialId"));
                    }
                    conversationDto.setItems(items);
                    conversationDto.setContent(null);
                }
            } else {
                MaterialDto materialDto = materialService.getImageText(wechatId, conversationDto.getMaterialId(), true);
                if (materialDto != null) {
                    List<ImageTextDto> itemDtos = new ArrayList<ImageTextDto>();
                    List<ImageTextDto> items = materialDto.getItems();
                    if (items != null && !items.isEmpty()) {
                        item = new ImageTextDto();
                        item.setTitle(items.get(0).getTitle());
                        item.setSummary(items.get(0).getSummary());
                        item.setMaterialCoverUrl(items.get(0).getMaterialCoverUrl());
                        itemDtos.add(item);
                        conversationDto.setItems(itemDtos);
                        conversationDto.setContent(null);
                    }
                }
            }
        }
        return result;
    }

    private static ObjectMapper om = new ObjectMapper();

    private List<ConversationDto> convert(Page<ConversationDto> page) {
        List<ConversationDto> result = page.getResult();
        ImageTextDto item = null;
        JSONObject itemJson = null;
        for (ConversationDto conversationDto : result) {
            try {
                if (StringUtils.isBlank(conversationDto.getContent())) {
                    continue;
                }
                List<ImageTextDto> items = new ArrayList<ImageTextDto>();
                //尝试解码
                try {
                    itemJson = JSONArray.parseArray(conversationDto.getContent()).getJSONObject(0);
                } catch (Exception e) {
                    itemJson = JSONObject.parseObject(conversationDto.getContent());
                }

                if (!itemJson.containsKey("title")) {
                    continue;
                }
                item = new ImageTextDto();
                item.setTitle(itemJson.getString("title"));
                item.setSummary(itemJson.getString("summary"));
                if (itemJson.containsKey("picurl")) {
                    item.setMaterialCoverUrl(itemJson.getString("picurl"));
                }
                if (itemJson.containsKey("materialCoverUrl")) {
                    item.setMaterialCoverUrl(itemJson.getString("materialCoverUrl"));
                }
                item.setId(itemJson.getInteger("id"));
                items.add(item);
                if (conversationDto.getMaterialId() == null) {
                    conversationDto.setMaterialId(itemJson.getInteger("materialId"));
                }
                conversationDto.setItems(items);
                conversationDto.setContent(om.writeValueAsString(items));
                conversationDto.setMsgType(Byte.valueOf("1"));
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
        return result;
    }
}
