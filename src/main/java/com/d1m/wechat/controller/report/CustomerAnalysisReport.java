package com.d1m.wechat.controller.report;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.lang.StringUtils;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.controller.membertag.MemberTagController;
import com.d1m.wechat.dto.ActivityListDto;
import com.d1m.wechat.dto.PieDto;
import com.d1m.wechat.dto.QrcodeDto;
import com.d1m.wechat.dto.ReportActivityDto;
import com.d1m.wechat.dto.ReportActivityUserDto;
import com.d1m.wechat.dto.ReportAreaBaseDto;
import com.d1m.wechat.dto.ReportArticleDto;
import com.d1m.wechat.dto.ReportKeyMatchDto;
import com.d1m.wechat.dto.ReportKeyMatchTopDto;
import com.d1m.wechat.dto.ReportMemberTagDto;
import com.d1m.wechat.dto.ReportMenuChildDto;
import com.d1m.wechat.dto.ReportMenuDto;
import com.d1m.wechat.dto.ReportMenuGroupDto;
import com.d1m.wechat.dto.ReportMessageDto;
import com.d1m.wechat.dto.ReportQrcodeDto;
import com.d1m.wechat.dto.ReportQrcodeItemDto;
import com.d1m.wechat.dto.ReportUserSourceDto;
import com.d1m.wechat.dto.TrendDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.pamametermodel.ArticleModel;
import com.d1m.wechat.pamametermodel.MenuReportModel;
import com.d1m.wechat.pamametermodel.MessageModel;
import com.d1m.wechat.pamametermodel.QrcodeModel;
import com.d1m.wechat.pamametermodel.SourceUserModel;
import com.d1m.wechat.pamametermodel.UserModel;
import com.d1m.wechat.service.ConversationService;
import com.d1m.wechat.service.MemberReplyService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.service.MenuGroupService;
import com.d1m.wechat.service.MenuService;
import com.d1m.wechat.service.QrcodeService;
import com.d1m.wechat.service.ReportActivityService;
import com.d1m.wechat.service.ReportArticleSourceService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

/**
 * 客户分析
 * 
 * @author sahara
 */
@Controller
@RequestMapping("/report")
public class CustomerAnalysisReport extends BaseController {

	private Logger log = LoggerFactory.getLogger(MemberTagController.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private MemberTagService memberTagService;

	@Autowired
	private QrcodeService qrcodeService;

	@Autowired
	private ConversationService conversationService;

	@Autowired
	private MenuService menuService;

	@Autowired
	private MenuGroupService menuGroupService;
	
	@Autowired
	private MemberReplyService memberReplyService;
	
	@Autowired
	private ReportArticleSourceService reportArticleService;

	@Autowired
	private ReportActivityService reportActivityService;

	@RequestMapping(value = "trend.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions(value={"dash_board:list","data-report:customer-analysis"},logical=Logical.OR)
	public JSONObject trend(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end, HttpSession session) {
		try {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = DateUtil.getDateBegin(dateFormat1.parse(start));
			Date date2 = DateUtil.getDateEnd(dateFormat1.parse(end));
			TrendDto trendDto = memberService.trend(getWechatId(session),
					date1, date2);
			return representation(Message.REPORT_MENU_TREND_SUCCESS, trendDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "pie.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions(value={"dash_board:list","data-report:customer-analysis"},logical=Logical.OR)
	public JSONObject pie(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end,
			@RequestParam(required = true) String type, HttpSession session) {
		try {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = DateUtil.getDateBegin(dateFormat1.parse(start));
			Date date2 = DateUtil.getDateEnd(dateFormat1.parse(end));

			if (!StringUtils.isBlank(type) && type.compareTo("area") == 0) {
				// 区域
				List<ReportAreaBaseDto> list = memberService.pieArea(
						getWechatId(session), date1, date2, type);
				return representation(Message.MEMBER_GET_SUCCESS, list);
			} else {// 语言、性别
				PieDto pieDto = memberService.pie(getWechatId(session), date1,
						date2, type);
				return representation(Message.MEMBER_GET_SUCCESS, pieDto);
			}
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	// 活跃用户
	@RequestMapping(value = "activity-user.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions(value={"dash_board:list","data-report:customer-analysis"},logical=Logical.OR)
	public JSONObject activityUser(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end,
			@RequestParam(required = true) Integer top, HttpSession session) {
		try {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = DateUtil.getDateBegin(dateFormat1.parse(start));
			Date date2 = DateUtil.getDateEnd(dateFormat1.parse(end));

			Page<ReportActivityUserDto> page = memberService.activityUser(
					getWechatId(session), date1, date2, top);
			List<ReportActivityUserDto> activityUserDtos = new ArrayList<ReportActivityUserDto>();
			for (ReportActivityUserDto business : page.getResult()) {
				activityUserDtos.add(business);
			}
			return representation(Message.MEMBER_GET_SUCCESS, activityUserDtos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	// 标签用户
	@RequestMapping(value = "tag-user.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions(value={"dash_board:list","data-report:customer-analysis"},logical=Logical.OR)
	public JSONObject tagUser(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end,
			@RequestParam(required = true) Integer top, HttpSession session) {
		try {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = DateUtil.getDateBegin(dateFormat1.parse(start));
			Date date2 = DateUtil.getDateEnd(dateFormat1.parse(end));

			Page<ReportMemberTagDto> page = memberTagService.tagUser(
					getWechatId(session), date1, date2, top);
			List<ReportMemberTagDto> tagUserDtos = new ArrayList<ReportMemberTagDto>();
			for (int i = 0; i < page.size(); i++) {
				ReportMemberTagDto param = page.get(i);
				param.setId(i + 1);
				tagUserDtos.add(param);
			}
			return representation(Message.MEMBER_GET_SUCCESS, tagUserDtos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	// 用户来源分析
	@RequestMapping(value = "source-user.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("data-report:customer-source-analysis")
	public JSONObject sourceUser(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end, HttpSession session) {
		try {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = DateUtil.getDateBegin(dateFormat1.parse(start));
			Date date2 = DateUtil.getDateEnd(dateFormat1.parse(end));
			ReportUserSourceDto page = memberService.sourceUser(
					getWechatId(session), date1, date2);
			return representation(Message.MEMBER_GET_SUCCESS, page);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	// 二维码分析
	@RequestMapping(value = "qrcode.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("data-report:qrCode-analysis")
	public JSONObject qrcode(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end, 
			@RequestParam(required = false) Integer qrcodeId,
			@RequestParam(required = false) String scene,
			@RequestParam(required = false) Integer status,
			HttpSession session) {
		try {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = DateUtil.getDateBegin(dateFormat1.parse(start));
			Date date2 = DateUtil.getDateEnd(dateFormat1.parse(end));
			ReportQrcodeDto dto = qrcodeService.qrcode(getWechatId(session),
					date1, date2, qrcodeId, scene, status);
			return representation(Message.QRCODE_GET_SUCCESS, dto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	// 二维码统计列表导出
	@RequestMapping(value = "export-qrcode.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("data-report:qrCode-analysis")
	public ModelAndView exportExcelByQrcode(HttpSession session, HttpServletRequest request,
			HttpServletResponse response){
		QrcodeModel model = null;
		String data = request.getParameter("data");
		if(StringUtils.isNotBlank(data)){
			model = (QrcodeModel)JSON.parseObject(data, QrcodeModel.class);
		}
		if(model == null){
			model = new QrcodeModel();
		}
		if (model.getStart() == null || model.getEnd() == null){
			throw new WechatException(Message.REPORT_QRCODE_START_OR_END_NOT_BLANK);
		}
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = DateUtil.getDateBegin(dateFormat1.parse(model.getStart()));
			date2 = DateUtil.getDateEnd(dateFormat1.parse(model.getEnd()));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		ModelMap modelMap = new ModelMap();
		List<ReportQrcodeItemDto> dtos = qrcodeService.qrcodeList(getWechatId(session),
				date1, date2, model.getQrcodeId(), model.getScene(), model.getStatus());
		modelMap.put("dtos", dtos);
		ReportQrcodeData reportQrcodeData = new ReportQrcodeData();
		return new ModelAndView(reportQrcodeData, modelMap);
	}
	
	// 每日二维码统计列表导出
	@RequestMapping(value = "export-dateqrcode.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("data-report:qrCode-analysis")
	public ModelAndView exportExcelByDateQrcode(HttpSession session, HttpServletRequest request,
			HttpServletResponse response){
		QrcodeModel model = null;
		String data = request.getParameter("data");
		if(StringUtils.isNotBlank(data)){
			model = (QrcodeModel)JSON.parseObject(data, QrcodeModel.class);
		}
		if(model == null){
			model = new QrcodeModel();
		}
		if (model.getStart() == null || model.getEnd() == null){
			throw new WechatException(Message.REPORT_QRCODE_START_OR_END_NOT_BLANK);
		}
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = DateUtil.getDateBegin(dateFormat1.parse(model.getStart()));
			date2 = DateUtil.getDateEnd(dateFormat1.parse(model.getEnd()));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		ModelMap modelMap = new ModelMap();
		List<ReportQrcodeItemDto> dtos = qrcodeService.dateQrcodeList(getWechatId(session),
				date1, date2, model.getQrcodeId(), model.getScene(), model.getStatus());
		modelMap.put("dtos", dtos);
		ReportDateQrcodeData reportDateQrcodeData = new ReportDateQrcodeData();
		return new ModelAndView(reportDateQrcodeData, modelMap);
	}

	// 消息分析
	@RequestMapping(value = "message.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("data-report:message-analysis")
	public JSONObject message(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end, 
			@RequestParam(required = true) String type,
			HttpSession session) {
		
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = DateUtil.getDateBegin(dateFormat.parse(start));
			Date endDate = DateUtil.getDateEnd(dateFormat.parse(end));
			JSONObject json = null;
			if ("day".equals(type)){
				ReportMessageDto dto = conversationService.messageReport(
						getWechatId(session), startDate, endDate);
				json =  representation(Message.REPORT_DAY_MESSAGE_GET_SUCCESS, dto);
			}else if ("hour".equals(type)){
				ReportMessageDto dtos = conversationService.hourMessageReport(
						getWechatId(session), startDate, endDate);
				json =  representation(Message.REPORT_HOUR_MESSAGE_GET_SUCCESS, dtos);
			}
			return json;
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
		
	}
	
	// 小时消息分析
	@RequestMapping(value = "hour-message.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject hourMessage(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end, HttpSession session) {
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = dateFormat.parse(start);
			Date endDate = dateFormat.parse(end);
			ReportMessageDto dtos = conversationService.hourMessageReport(
					getWechatId(session), startDate, endDate);
			return representation(Message.REPORT_HOUR_MESSAGE_GET_SUCCESS, dtos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	// 菜单分析
	@RequestMapping(value = "menu.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("data-report:menu-analysis")
	public JSONObject menu(@RequestBody(required = true) MenuReportModel model, HttpSession session) {
		try {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = DateUtil.getDateBegin(dateFormat1.parse(model.getStart()));
			Date date2 = DateUtil.getDateEnd(dateFormat1.parse(model.getEnd()));

			ReportMenuDto dto = menuService.menuReport(getWechatId(session),model.getGroupId(), model.getStatus(), date1, date2);
			return representation(Message.REPORT_MENU_GET_SUCCESS, dto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	// 菜单组
	@RequestMapping(value = "menu-group.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("data-report:menu-analysis")
	public JSONObject menuGroup(HttpSession session) {
		try {
			List<ReportMenuGroupDto> list = menuGroupService
					.menuGroupList(getWechatId(session));
			return representation(Message.REPORT_MENU_GET_SUCCESS, list);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	// 子菜单分析
	@RequestMapping(value = "menu-child.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("data-report:menu-analysis")
	public JSONObject menuChild(@RequestParam(required = true) Integer groupId,
			@RequestParam(required = true) String start,
			@RequestParam(required = true) String end, HttpSession session) {
		try {

			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = DateUtil.getDateBegin(dateFormat1.parse(start));
			Date date2 = DateUtil.getDateEnd(dateFormat1.parse(end));

			List<ReportMenuChildDto> list = menuService.menuChildReport(
					getWechatId(session), groupId, date1, date2);
			return representation(Message.REPORT_MENU_GET_SUCCESS, list);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	// 关键字匹配分析
	@RequestMapping(value = "keyMatch.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("data-report:keyword-analysis")
	public JSONObject keyMatch(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end, HttpSession session) {
		try {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = DateUtil.getDateBegin(dateFormat1.parse(start));
			Date date2 = DateUtil.getDateEnd(dateFormat1.parse(end));
			ReportKeyMatchDto dto = memberReplyService.keyMatch(
					getWechatId(session), date1, date2);
			return representation(Message.REPORT_REPLY_GET_SUCCESS, dto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	// 关键字匹配TOP
	@RequestMapping(value = "matchTop.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("data-report:keyword-analysis")
	public JSONObject matchTop(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end,
			@RequestParam(required = true) Integer top, HttpSession session) {
		try {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = DateUtil.getDateBegin(dateFormat1.parse(start));
			Date date2 = DateUtil.getDateEnd(dateFormat1.parse(end));

			Page<ReportKeyMatchTopDto> page = memberReplyService.matchTop(
					getWechatId(session), date1, date2, top);
			List<ReportKeyMatchTopDto> dtos = new ArrayList<ReportKeyMatchTopDto>();
			for (int i = 0; i < page.size(); i++) {
				ReportKeyMatchTopDto param = page.get(i);
				dtos.add(param);
			}
			return representation(Message.REPORT_REPLY_GET_SUCCESS, dtos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	// 关键字未匹配TOP
	@RequestMapping(value = "unMatchTop.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("data-report:keyword-analysis")
	public JSONObject unMatchTop(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end,
			@RequestParam(required = true) Integer top, HttpSession session) {
		try {
			DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date date1 = DateUtil.getDateBegin(dateFormat1.parse(start));
			Date date2 = DateUtil.getDateEnd(dateFormat1.parse(end));

			Page<ReportKeyMatchTopDto> page = memberReplyService.unMatchTop(
					getWechatId(session), date1, date2, top);
			List<ReportKeyMatchTopDto> dtos = new ArrayList<ReportKeyMatchTopDto>();
			for (int i = 0; i < page.size(); i++) {
				ReportKeyMatchTopDto param = page.get(i);
				dtos.add(param);
			}
			return representation(Message.REPORT_REPLY_GET_SUCCESS, dtos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	// 群发图文分析
	@RequestMapping(value = "article.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("data-report:img-text-analysis")
	public JSONObject article(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end, 
			@RequestParam(required = false) String type,
			HttpSession session) {
		
		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = DateUtil.getDateBegin(dateFormat.parse(start));
			Date endDate = DateUtil.getDateEnd(dateFormat.parse(end));
			JSONObject json = null;
			if (type == null){
				type = "day";
			}
			if ("day".equals(type)){
				 ReportArticleDto dto = reportArticleService.articleReport(
						 getWechatId(session), startDate, endDate);
				json =  representation(Message.REPORT_DAY_ARTICLE_GET_SUCCESS, dto);
			}else if ("hour".equals(type)){
				ReportArticleDto dto = reportArticleService.hourArticleReport(
						getWechatId(session), startDate, endDate);
				json =  representation(Message.REPORT_HOUR_ARTICLE_GET_SUCCESS, dto);
			}
			return json;
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}	
	}

	// 活动分析
	@RequestMapping(value = "activity.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("data-report:activity-analysis")
	public JSONObject activity(@RequestParam(required = true) String start,
			@RequestParam(required = true) String end,
			@RequestParam(required = false) Integer activityId,
			@RequestParam(required = false) Integer couponSettingId,
			HttpSession session) {

		try {
			DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			Date startDate = DateUtil.getDateBegin(dateFormat.parse(start));
			Date endDate = DateUtil.getDateEnd(dateFormat.parse(end));
			ReportActivityDto dto = reportActivityService.activityReport(
					getWechatId(session), startDate, endDate, activityId, couponSettingId);
			return representation(Message.REPORT_ACTIVITY_GET_SUCCESS, dto);

		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	// 活动-优惠券列表接口
	@RequestMapping(value = "activity-list.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("data-report:activity-analysis")
	public JSONObject activityList(HttpSession session) {

		try {
			List<ActivityListDto> dto = reportActivityService.activityList(
					getWechatId(session));
			return representation(Message.REPORT_ACTIVITY_LIST_SUCCESS, dto);

		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	//二维码列表接口
	@RequestMapping(value = "qrcode-list.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("data-report:qrcode-analysis")
	public JSONObject qrcodeList(@RequestParam(required = false) Integer status,
			HttpSession session) {
		
		try {
			List<QrcodeDto> dto = qrcodeService.qrcodeList(
					getWechatId(session), status);
			return representation(Message.REPORT_QRCODE_LIST_SUCCESS, dto);
			
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}	
	}
	
	// 导出用户来源分析
	@RequestMapping(value = "export-sourceuser.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("data-report:customer-source-analysis")
	public ModelAndView exportExcelBySourceUser(HttpSession session, HttpServletRequest request,
			HttpServletResponse response){
		SourceUserModel model = null;
		String data = request.getParameter("data");
		if(StringUtils.isNotBlank(data)){
			model = (SourceUserModel)JSON.parseObject(data, SourceUserModel.class);
		}
		if(model == null){
			model = new SourceUserModel();
		}
		if (model.getStart() == null || model.getEnd() == null){
			throw new WechatException(Message.REPORT_SOURCE_USER_START_OR_END_NOT_BLANK);
		}
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = DateUtil.getDateBegin(dateFormat1.parse(model.getStart()));
			date2 = DateUtil.getDateEnd(dateFormat1.parse(model.getEnd()));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		ModelMap modelMap = new ModelMap();
		ReportUserSourceDto dto = memberService.sourceUser(
				getWechatId(session), date1, date2);
		modelMap.put("dto", dto);
		SourceUserData sourceUserData = new SourceUserData();
		return new ModelAndView(sourceUserData, modelMap);
	}
	
	// 导出图文分析
	@RequestMapping(value = "export-article.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("data-report:img-text-analysis")
	public ModelAndView exportArticle(HttpSession session, HttpServletRequest request,
			HttpServletResponse response){
		ArticleModel model = null;
		String data = request.getParameter("data");
		if(StringUtils.isNotBlank(data)){
			model = (ArticleModel)JSON.parseObject(data, ArticleModel.class);
		}
		if(model == null){
			model = new ArticleModel();
		}
		if (model.getStart() == null || model.getEnd() == null){
			throw new WechatException(Message.REPORT_ARTICLE_START_OR_END_NOT_BLANK);
		}
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = DateUtil.getDateBegin(dateFormat1.parse(model.getStart()));
			date2 = DateUtil.getDateEnd(dateFormat1.parse(model.getEnd()));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		ModelMap modelMap = new ModelMap();
		ReportArticleDto dto = reportArticleService.articleReport(getWechatId(session), date1, date2);
		modelMap.put("dto", dto);
		ArticleData articleData = new ArticleData();
		return new ModelAndView(articleData, modelMap);
	}
	
	// 导出消息分析
	@RequestMapping(value = "export-message.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("data-report:message-analysis")
	public ModelAndView exportMessage(HttpSession session, HttpServletRequest request,
			HttpServletResponse response){
		MessageModel model = null;
		String data = request.getParameter("data");
		if(StringUtils.isNotBlank(data)){
			model = (MessageModel)JSON.parseObject(data, MessageModel.class);
		}
		if(model == null){
			model = new MessageModel();
		}
		if (model.getStart() == null || model.getEnd() == null){
			throw new WechatException(Message.REPORT_MESSAGE_START_OR_END_NOT_BLANK);
		}
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = DateUtil.getDateBegin(dateFormat1.parse(model.getStart()));
			date2 = DateUtil.getDateEnd(dateFormat1.parse(model.getEnd()));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		ModelMap modelMap = new ModelMap();
		ReportMessageDto dto = new ReportMessageDto();
		if (model.getType() != null && "day".equals(model.getType())){
			dto = conversationService.messageReport(getWechatId(session), date1, date2);
		}else if (model.getType() != null && "hour".equals(model.getType())){
			dto = conversationService.hourMessageReport(getWechatId(session), date1, date2);
		}
		modelMap.put("dto", dto);
		modelMap.put("type", model.getType());
		MessageData messageData = new MessageData();
		return new ModelAndView(messageData, modelMap);
	}
	
	// 导出菜单分析
	@RequestMapping(value = "export-menu.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("data-report:menu-analysis")
	public ModelAndView exportMenu(HttpSession session, HttpServletRequest request,
			HttpServletResponse response){
		MenuReportModel model = null;
		String data = request.getParameter("data");
		if(StringUtils.isNotBlank(data)){
			model = (MenuReportModel)JSON.parseObject(data, MenuReportModel.class);
		}
		if(model == null){
			model = new MenuReportModel();
		}
		if (model.getStart() == null || model.getEnd() == null){
			throw new WechatException(Message.REPORT_MENU_START_OR_END_NOT_BLANK);
		}
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = DateUtil.getDateBegin(dateFormat1.parse(model.getStart()));
			date2 = DateUtil.getDateEnd(dateFormat1.parse(model.getEnd()));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		ModelMap modelMap = new ModelMap();
		ReportMenuDto dto = menuService.menuReport(getWechatId(session), model.getGroupId(), model.getStatus(), date1, date2);
		modelMap.put("dto", dto);
		MenuData menuData = new MenuData();
		return new ModelAndView(menuData, modelMap);
	}
	
	// 导出关键字分析
	@RequestMapping(value = "export-key.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("data-report:keyword-analysis")
	public ModelAndView exportMKey(HttpSession session, HttpServletRequest request,
			HttpServletResponse response){
		MessageModel model = null;
		String data = request.getParameter("data");
		if(StringUtils.isNotBlank(data)){
			model = (MessageModel)JSON.parseObject(data, MessageModel.class);
		}
		if(model == null){
			model = new MessageModel();
		}
		if (model.getStart() == null || model.getEnd() == null){
			throw new WechatException(Message.REPORT_MESSAGE_START_OR_END_NOT_BLANK);
		}
		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		Date date1 = null;
		Date date2 = null;
		try {
			date1 = DateUtil.getDateBegin(dateFormat1.parse(model.getStart()));
			date2 = DateUtil.getDateEnd(dateFormat1.parse(model.getEnd()));
		} catch (ParseException e) {
			log.error(e.getMessage());
		}
		ModelMap modelMap = new ModelMap();
		Page<ReportKeyMatchTopDto> dtos = memberReplyService.match(
				getWechatId(session), date1, date2);
		modelMap.put("dtos", dtos);
		KeyWordData keyWordData = new KeyWordData();
		return new ModelAndView(keyWordData, modelMap);
	}

        // 导出用户分析
 	@RequestMapping(value = "export-user.json", method = RequestMethod.POST)
 	@ResponseBody
 	@RequiresPermissions(value={"dash_board:list","data-report:customer-analysis"},logical=Logical.OR)
 	public ModelAndView exportExcelByUser(HttpSession session, HttpServletRequest request,
 			HttpServletResponse response){
 		UserModel model = null;
 		String data = request.getParameter("data");
 		if(StringUtils.isNotBlank(data)){
 			model = (UserModel)JSON.parseObject(data, UserModel.class);
 		}
 		if(model == null){
 			model = new UserModel();
 		}
 		if (model.getStart() == null || model.getEnd() == null){
 			throw new WechatException(Message.REPORT_USER_START_OR_END_NOT_BLANK);
 		}
 		DateFormat dateFormat1 = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
 		Date date1 = null;
 		Date date2 = null;
 		try {
 			date1 = DateUtil.getDateBegin(dateFormat1.parse(model.getStart()));
 			date2 = DateUtil.getDateEnd(dateFormat1.parse(model.getEnd()));
 		} catch (ParseException e) {
 			log.error(e.getMessage());
 		}
 		ModelMap modelMap = new ModelMap();
 		TrendDto dto = memberService.trend(getWechatId(session),
				date1, date2);
 		modelMap.put("dto", dto);
 		UserData userData = new UserData();
 		return new ModelAndView(userData, modelMap);
 	}
}
