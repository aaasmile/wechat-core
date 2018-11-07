package com.d1m.wechat.controller.member;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
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
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.support.RequestContextUtils;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.controller.report.ReportXlsxStreamView;
import com.d1m.wechat.dto.MemberDto;
import com.d1m.wechat.dto.MemberLevelDto;
import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.pamametermodel.AddMemberTagModel;
import com.d1m.wechat.pamametermodel.ExcelMember;
import com.d1m.wechat.service.AreaInfoService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.impl.MemberServiceImpl;
import com.d1m.wechat.util.ConstantsUtil;
import com.d1m.wechat.util.I18nUtil;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.github.pagehelper.Page;

import cn.d1m.wechat.client.model.WxUser;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

@Controller
@RequestMapping("/member")
@Api(value = "会员API", tags = "会员接口")
public class MemberController extends BaseController {

	private Logger log = LoggerFactory.getLogger(MemberController.class);

	@Autowired
	private MemberService memberService;

	@Autowired
	private AreaInfoService areaInfoService;

	@ApiOperation(value = "拉取微信会员信息", tags = "会员接口")
	@ApiResponse(code = 200, message = "1-拉取微信会员信息成功")
	@RequestMapping(value = "/pullwxmember.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject pullWxMember(HttpSession session, HttpServletRequest request) {
		try {
			String nextOpenId = request.getParameter("next_openid");
			memberService.pullWxMember(getWechatId(session), nextOpenId);
			return representation(Message.MEMBER_PULL_WX_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "更新微信会员信息", tags = "会员接口")
	@ApiResponse(code = 200, message = "更新微信会员信息成功")
	@RequestMapping(value = "/updateMemberInfo.json", method = RequestMethod.GET)
	@ResponseBody
	public JSONObject updateMemberInfo(HttpSession session, HttpServletRequest request) {
		try {
			JSONObject json = new JSONObject();
			json.put("resultCode", 0);
			Integer wechatId = getWechatId(session);
			boolean isNext = true;
			String msg = "";
			do {
				List<MemberDto> memberDtoList = memberService.searchBySql(wechatId,
						"is_subscribe =1 and nickname is null and (local_head_img_url is null or local_head_img_url ='') limit 0,1 ");
				// List<MemberDto> memberDtoList = memberService.searchBySql(wechatId,
				// "is_subscribe =1");
				Date current = new Date();
				if (memberDtoList.size() > 0) {
					MemberDto memberDto = memberDtoList.get(0);
					Member exist = memberService.getMember(wechatId, memberDto.getId());
					log.info("updateMemberInfo openId: {}", exist.getOpenId());
					WxUser wxuser = WechatClientDelegate.getUser(wechatId, exist.getOpenId());
					if (null != wxuser) {
						log.info("WxUser:" + JSON.toJSON(wxuser));
						Member newMember = memberService.getMemberByWxUser(wxuser, wechatId, current);
						MemberServiceImpl.updateMemberByWxMember(exist, newMember);
						memberService.updateAll(exist);
					} else {
						msg = "openid 查找不到数据:" + memberDto.getOpenId();
						isNext = false;
						log.info("updateMemberInfo 查找不到数据 openId: {}", exist.getOpenId());
					}
				} else {
					msg = "获取数据为空";
					isNext = false;
				}
			} while (isNext);

			json.put("msg", msg);
			return json;
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@ApiOperation(value = "获取会员详情", tags = "会员接口")
	@ApiResponse(code = 200, message = "1-获取会员详情成功")
	@RequestMapping(value = "{id}/get.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("member:list")
	public JSONObject get(@ApiParam("会员ID") @PathVariable Integer id, HttpSession session) {
		try {

			MemberDto memberDto = memberService.getMemberDto(getWechatId(session), id);
			MemberLevelDto memberLevelDto = memberService.selectMemberProfile(id, getWechatId(session));
			memberDto.setCredits(memberLevelDto.getCredits());
			memberDto.setLevel(memberLevelDto.getLevel());

			return representation(Message.MEMBER_GET_SUCCESS, memberDto);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	/**
	 * @param activity
	 *            活跃度 [20,40]
	 * @param attentionStartTime
	 *            关注起始时间 2016-04-07T16:00:00.000Z
	 * @param attentionEndTime
	 *            关注结束时间 2016-05-07T16:00:00.000Z
	 * @param batchSendOfMonth
	 *            本月群发 [10,60]
	 * @param cancelStartTime
	 *            取消关注起始时间 2016-05-07T16:00:00.000Z
	 * @param cancelEndTime
	 *            取消关注结束时间 2016-06-07T16:00:00.000Z
	 * @param country
	 *            国家 {"code" : 860000, "remark" : "中国"}
	 * @param province
	 *            省份 {"code" : 860000, "remark" : "福建"}
	 * @param city
	 *            城市 {"code" : 860000, "remark" : "宁德"}
	 * @param nickname
	 *            昵称
	 * @param sex
	 *            性别 {"code" : 0, "value" : "男"}
	 * @param subscribe
	 *            是否关注 {"code" : 0, "value" : "未关注"}
	 * @param memberIds
	 *            会员ID [2,4,5,6,7]
	 * @param memberTags
	 *            标签[{id:1},{id:3}]
	 * @param sortName
	 * @param sortDir
	 * @param pageNum
	 * @param pageSize
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "获取会员列表", tags = "会员接口")
	@ApiResponse(code = 200, message = "1-获取会员列表成功")
	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("member:list")
	public JSONObject list(
			@ApiParam(name = "AddMemberTagModel", required = false) @RequestBody(required = false) AddMemberTagModel addMemberTagModel,
			HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {

			if (addMemberTagModel == null) {
				addMemberTagModel = new AddMemberTagModel();
			}
			Page<MemberDto> page = memberService.search(getWechatId(session), addMemberTagModel, true);
			log.info("Country:" + RequestContextUtils.getLocale(request).getCountry());
			if (CollectionUtils.isNotEmpty(page)) {
				for (MemberDto dto : page.getResult()) {
					dto.setSubscribeScene(ConstantsUtil.subscribeSceneChangeLanguage(dto.getSubscribeScene(),
							RequestContextUtils.getLocale(request).getCountry()));
				}
			}
			return representation(Message.MEMBER_LIST_SUCCESS, page.getResult(), addMemberTagModel.getPageNum(),
					addMemberTagModel.getPageSize(), page.getTotal());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}

	}

	@ApiOperation(value = "导出会员列表", tags = "会员接口")
	@ApiResponse(code = 200, message = "导出会员列表")
	@RequestMapping(value = "/export.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("member:list")
	public ModelAndView exportExcel(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		ReportXlsxStreamView view = null;
		AddMemberTagModel addMemberTagModel = null;
		String data = request.getParameter("data");
		if (StringUtils.isNotBlank(data)) {
			addMemberTagModel = (AddMemberTagModel) JSON.parseObject(data, AddMemberTagModel.class);
		}
		if (addMemberTagModel == null) {
			addMemberTagModel = new AddMemberTagModel();
		}
		Locale locale = RequestContextUtils.getLocale(request);
		String name = I18nUtil.getMessage("follwer.list", locale);
		String lang = RequestContextUtils.getLocale(request).getCountry();
		List<MemberDto> memberDtos = null;
		Integer[] memberIds = addMemberTagModel.getMemberIds();
		Boolean sendToAll = addMemberTagModel.getSendToAll();
		if (memberIds != null && memberIds.length != 0) {
			memberDtos = memberService.getMemberList(addMemberTagModel, getWechatId(session));
		} else {
			List<ExcelMember> excelMemberList = null;
			if (sendToAll != null && sendToAll) {
				excelMemberList = memberService.totalMember(getWechatId(session), null, false);
			} else {
				excelMemberList = memberService.totalMember(getWechatId(session), addMemberTagModel, false);
			}
			if (excelMemberList != null) {
				view = excelMemberView(locale, name, excelMemberList);
				return new ModelAndView(view);
			}
		}
		view = memberView(locale, name, memberDtos, areaInfoService, lang);
		return new ModelAndView(view);

	}

	public ReportXlsxStreamView memberView(Locale locale, String name,
			List<MemberDto> memberDtoList, AreaInfoService areaInfoService, String lang) {
		ReportXlsxStreamView view;
		view = new ReportXlsxStreamView(name, new ReportXlsxStreamView.CellProcessor() {
			@Override
			public void process(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) {
				Sheet sheet = workbook.createSheet();
				Row titleRow = sheet.createRow(0);
				// 填充表头
				ExcelMember.fillTitles(titleRow, locale);
				if (memberDtoList != null && !memberDtoList.isEmpty()) {
					int j = 1;
					for (MemberDto memberDto : memberDtoList) {
						try {
							Row dataRow = sheet.createRow(j);
							dataRow.setHeight((short) 600);
							dataRow.createCell(0).setCellValue(j);
							memberDto.fillRows(dataRow, locale, areaInfoService, lang);
							j++;
						} catch (Exception e) {
							log.error(e.getMessage(), e);
							log.error(memberDto.toString());
						}
					}
				}
			}
		});
		return view;
	}

	public ReportXlsxStreamView excelMemberView(Locale locale, String name,
			List<ExcelMember> excelMemberList) {
		ReportXlsxStreamView view;
		view = new ReportXlsxStreamView(name, new ReportXlsxStreamView.CellProcessor() {
			@Override
			public void process(Map<String, Object> map, Workbook workbook, HttpServletRequest httpServletRequest,
					HttpServletResponse httpServletResponse) {
				Sheet sheet = workbook.createSheet();
				Row titleRow = sheet.createRow(0);
				// 填充表头
				ExcelMember.fillTitles(titleRow, locale);
				if (excelMemberList != null && !excelMemberList.isEmpty()) {
					int j = 1;
					for (ExcelMember excelMember : excelMemberList) {
						try {
							Row dataRow = sheet.createRow(j);
							dataRow.setHeight((short) 600);
							dataRow.createCell(0).setCellValue(j);
							excelMember.fillRows(dataRow, locale);
							j++;
						} catch (Exception e) {
							log.error(e.getMessage(), e);
							log.error(excelMember.toString());
						}
					}
				}
			}
		});
		return view;
	}

	/**
	 * memberIds为null或者空:根据条件筛选的全部会员加标签，memberIds不为空:指定的会员加标签
	 *
	 * @param activity
	 *            活跃度 [20,40]
	 * @param attentionStartTime
	 *            关注起始时间 2016-04-07T16:00:00.000Z
	 * @param attentionEndTime
	 *            关注结束时间 2016-05-07T16:00:00.000Z
	 * @param batchSendOfMonth
	 *            本月群发 [10,60]
	 * @param cancelStartTime
	 *            取消关注起始时间 2016-05-07T16:00:00.000Z
	 * @param cancelEndTime
	 *            取消关注结束时间 2016-06-07T16:00:00.000Z
	 * @param country
	 *            国家 {"code" : 860000, "remark" : "中国"}
	 * @param province
	 *            省份 {"code" : 860000, "remark" : "福建"}
	 * @param city
	 *            城市 {"code" : 860000, "remark" : "宁德"}
	 * @param nickname
	 *            昵称
	 * @param sex
	 *            性别 {"code" : 0, "value" : "男"}
	 * @param subscribe
	 *            是否关注 {"code" : 0, "value" : "未关注"}
	 * @param memberIds
	 *            会员ID [2,4,5,6,7]
	 * @param memberTags
	 *            标签[{id:1},{id:3}]
	 * @param tags
	 *            标签(没有id则新增标签) [{id:1},{id:3},{name:"技术员"}]
	 * @param session
	 * @return
	 */
	@ApiOperation(value = "添加会员标签", tags = "会员接口")
	@ApiResponse(code = 200, message = "1-添加会员标签成功")
	@RequestMapping(value = "addtag.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("member:list")
	public JSONObject addMemberTag(
			@ApiParam(name = "AddMemberTagModel", required = false) @RequestBody(required = false) AddMemberTagModel addMemberTagModel,
			HttpSession session) {
		try {
			List<MemberTagDto> memberTagDtos = memberService.addMemberTag(getWechatId(session), getUser(session),
					addMemberTagModel);
			Map<String, List<MemberTagDto>> map = new HashMap<String, List<MemberTagDto>>();
			map.put("tags", memberTagDtos);
			return representation(Message.MEMBER_ADD_TAG_SUCCESS, map);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	/**
	 * 删除某个会员单个标签
	 *
	 * @param memberId
	 * @param tagId
	 * @param session
	 * @param request
	 * @param response
	 * @return
	 */
	@ApiOperation(value = "删除会员标签", tags = "会员接口")
	@ApiResponse(code = 200, message = "1-删除会员标签成功")
	@RequestMapping(value = "/{memberId}/{memberTagId}/deltag.json", method = RequestMethod.DELETE)
	@ResponseBody
	public JSONObject deltag(@ApiParam("会员ID") @PathVariable Integer memberId,
			@ApiParam("会员标签ID") @PathVariable Integer memberTagId, HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			memberService.deleteMemberTag(getWechatId(session), memberId, memberTagId);
			return representation(Message.MEMBER_TAG_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}

	}

	@ApiOperation(value = "会员同步微信标签", tags = "会员接口")
	@ApiResponse(code = 200, message = "1-会员同步微信标签成功")
	@RequestMapping(value = "initwxtag.json", method = RequestMethod.GET)
	@ResponseBody
	@RequiresPermissions("member:list")
	public JSONObject syncWxTag(HttpSession session, HttpServletRequest request, HttpServletResponse response) {
		try {
			memberService.syncWxTag(getWechatId(session), getUser(session));
			return representation(Message.MEMBER_SYNC_WX_TAG_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

}
