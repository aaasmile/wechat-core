package com.d1m.wechat.controller.membertagtype;

import java.util.ArrayList;
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
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.controller.report.TagCategoryExport;
import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.dto.MemberTagTypeDto;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.MemberTagType;
import com.d1m.wechat.pamametermodel.MemberTagModel;
import com.d1m.wechat.pamametermodel.MemberTagTypeModel;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.service.MemberTagTypeService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;

@Controller
@RequestMapping("/member-tag-type")
public class MemberTagTypeController extends BaseController {

	private Logger log = LoggerFactory.getLogger(MemberTagTypeController.class);

	@Autowired
	private MemberTagTypeService memberTagTypeService;

	@Autowired
	private MemberTagService memberTagService;
	
	@RequestMapping(value = "new.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(@RequestBody(required = false) MemberTagTypeModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			if(model == null){
				model = new MemberTagTypeModel();
			}
			MemberTagType memberTagType = memberTagTypeService.create(
					getUser(session), getWechatId(session), model.getName(), model.getParentId());
			return representation(Message.MEMBER_TAG_TYPE_CREATE_SUCCESS,
					memberTagType.getId());
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{id}/delete.json", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			memberTagTypeService.delete(getUser(session), getWechatId(session),
					id);
			return representation(Message.MEMBER_TAG_TYPE_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value={"member:list","system-setting:auto-reply"},logical=Logical.OR)
	public JSONObject list(@RequestBody(required = false) MemberTagTypeModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		if(model == null){
			model = new MemberTagTypeModel();
		}
		Page<MemberTagType> memberTagTypes = memberTagTypeService.search(
				getWechatId(session), model.getName(), model.getSortName(), 
				model.getSortDir(), model.getPageNum(), model.getPageSize(), true);
		List<MemberTagTypeDto> memberTagTypeDtos = new ArrayList<MemberTagTypeDto>();
		for (MemberTagType memberTagType : memberTagTypes.getResult()) {
			memberTagTypeDtos.add(convert(memberTagType));
		}
		return representation(Message.MEMBER_TAG_TYPE_LIST_SUCCESS,
				memberTagTypeDtos, model.getPageNum(), model.getPageSize(), memberTagTypes.getTotal());
	}

	@RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
	public JSONObject update(@PathVariable Integer id,
			@RequestBody(required = false) MemberTagTypeModel model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if(model == null){
				model = new MemberTagTypeModel();
			}
			memberTagTypeService.update(getUser(session), getWechatId(session),
					id, model.getName());
			return representation(Message.MEMBER_TAG_TYPE_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	private MemberTagTypeDto convert(MemberTagType memberTagType) {
		MemberTagTypeDto dto = new MemberTagTypeDto();
		dto.setCreatedAt(DateUtil.formatYYYYMMDDHHMMSS(memberTagType
				.getCreatedAt()));
		dto.setId(memberTagType.getId());
		dto.setName(memberTagType.getName());
		return dto;
	}
	
	@RequestMapping(value = "listAllTypesTags.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:tag-category-management")
	public JSONObject getAllTypesTags(HttpSession session) {
		final Integer wechatId = getWechatId(session);
		List<MemberTagTypeDto> selectAll = memberTagTypeService.selectAllTagTypes(wechatId);
		List<MemberTagDto> allMemberTags = memberTagService.getAllMemberTags(wechatId);
		
		for(MemberTagTypeDto memberTagType : selectAll) {
			List<MemberTagDto> tagList = new ArrayList<>();
			for(MemberTagDto tagDto : allMemberTags) {
				if(memberTagType.getId().equals(tagDto.getMemberTagTypeId())) {
					tagList.add(tagDto);
				}
			}
			memberTagType.setTagList(tagList);
		}
		return representation(Message.MEMBER_TAG_TYPE_LIST_SUCCESS, selectAll);
	}

	@RequestMapping(value = "exportCategorys.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions("system-setting:tag-category-management")
	public ModelAndView exportCategorys(HttpSession session, HttpServletRequest request) {
		/*final Integer wechatId = getWechatId(session);
		String data = request.getParameter("data");
		final List<String> categoryList = Arrays.asList(data.split(","));
		List<MemberTagTypeDto> tagTypeList = memberTagTypeService.selectAllTagTypes(wechatId);
		List<MemberTagDto> allMemberTags = memberTagService.getAllMemberTags(wechatId);
		
		CollectionUtils.filter(tagTypeList, new Predicate() {
            public boolean evaluate(Object o) {
                return categoryList.contains(String.valueOf(((MemberTagTypeDto) o).getId()));
            }
        });
		
		for(MemberTagTypeDto memberTagType : tagTypeList) {
			List<MemberTagDto> tagList = new ArrayList<>();
			for(MemberTagDto tagDto : allMemberTags) {
				if(memberTagType.getId().equals(tagDto.getMemberTagTypeId())) {
					tagList.add(tagDto);
				}
			}
			memberTagType.setTagList(tagList);
		}
		ModelMap model = new ModelMap();
		model.put("tagTypeList", tagTypeList);*/
		MemberTagModel model = null;
 		String data = request.getParameter("data");
 		if(StringUtils.isNotBlank(data)){
 			model = (MemberTagModel)JSON.parseObject(data, MemberTagModel.class);
 		}
 		if(model == null){
 			model = new MemberTagModel();
 		}
		List<MemberTag> dtos = memberTagService.exportList(getWechatId(), model);
		ModelMap modelMap = new ModelMap();
		modelMap.put("tagList", dtos);
		TagCategoryExport report = new TagCategoryExport();
		return new ModelAndView(report, modelMap);
	}
}
