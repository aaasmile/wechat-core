package com.d1m.wechat.controller.conversation;

import java.util.ArrayList;
import java.util.List;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.ConversationDto;
import com.d1m.wechat.dto.ImageTextDto;
import com.d1m.wechat.dto.MaterialDto;
import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.model.Conversation;
import com.d1m.wechat.model.UserBehavior;
import com.d1m.wechat.model.UserLocation;
import com.d1m.wechat.model.enums.MassConversationResultStatus;
import com.d1m.wechat.model.enums.MsgType;
import com.d1m.wechat.pamametermodel.ConversationModel;
import com.d1m.wechat.pamametermodel.MassConversationModel;
import com.d1m.wechat.service.ConversationService;
import com.d1m.wechat.service.MaterialService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import com.github.pagehelper.PageHelper;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

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

	@ApiOperation(value = "创建群发会话", tags = "会话接口")
	@ApiResponse(code = 200, message = "1-创建群发会话成功")
	@RequestMapping(value = "mass/new.json", method = RequestMethod.POST)
	@RequiresPermissions("member:list")
	public JSONObject createMass(@ApiParam(name = "MassConversationModel", required = false) @RequestBody(required = false) MassConversationModel massConversationModel, HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {
			conversationService.preMassConversation(getWechatId(session), getUser(session), massConversationModel);
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
			conversationService.sendMassConversation(getWechatId(session), getUser(session), massConversationModel);
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
			Conversation conversation = conversationService.wechatToMember(getWechatId(session), getUser(session), conversationModel);
			MemberDto member = memberService.getMemberDto(getWechatId(session), conversation.getMemberId());
			ConversationDto dto = new ConversationDto();
			dto.setId(conversation.getId());
			dto.setCreatedAt(DateUtil.formatYYYYMMDDHHMMSS(conversation.getCreatedAt()));
			dto.setContent(conversation.getContent());
			dto.setCurrent(DateUtil.formatYYYYMMDDHHMMSS(conversation.getCreatedAt()));
			dto.setDir(conversation.getDirection() ? 1 : 0);
			dto.setIsMass(conversation.getIsMass() ? 1 : 0);
			dto.setMemberId(conversation.getMemberId() + "");
			dto.setMemberNickname(member.getNickname());
			dto.setMemberPhoto(conversation.getMemberPhoto());
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
		Integer wechatId = getWechatId(session);
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
		Integer wechatId = getWechatId(session);
		Page<ConversationDto> page = conversationService.searchMass(getWechatId(session), conversationModel, true);
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
			if(conversationModel.getMemberId() == null) {
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
			if(conversationModel.getMemberId() == null) {
				return this.representation(Message.CONVERSATION_LIST_FAIL, null);
			}
			Integer wechatId = getWechatId();
			PageHelper.startPage(conversationModel.getPageNum(), conversationModel.getPageSize(), true);
			Page<UserLocation> userLocationPage = conversationService.selectUserLocation(wechatId, conversationModel);
			return this.representation(Message.CONVERSATION_LIST_SUCCESS, userLocationPage.getResult(), userLocationPage.getPageSize(), userLocationPage.getPageNum(), userLocationPage.getTotal());
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
				MaterialDto materialDto = materialService.getImageText(wechatId, conversationDto.getMaterialId());
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

	private List<ConversationDto> convert(Page<ConversationDto> page) {
		List<ConversationDto> result = page.getResult();
		ImageTextDto item = null;
		JSONObject itemJson = null;
		for (ConversationDto conversationDto : result) {
			if (conversationDto.getMsgType() != MsgType.MPNEWS.getValue()) {
				continue;
			}
			if (StringUtils.isBlank(conversationDto.getContent())) {
				continue;
			}
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
		return result;
	}
}
