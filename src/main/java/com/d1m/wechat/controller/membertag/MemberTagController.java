package com.d1m.wechat.controller.membertag;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.github.pagehelper.Page;
import org.apache.shiro.authz.annotation.Logical;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.controller.file.Upload;
import com.d1m.wechat.controller.file.UploadController;
import com.d1m.wechat.dto.MemberTagDto;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.MemberTagCsv;
import com.d1m.wechat.pamametermodel.AddMemberTagModel;
import com.d1m.wechat.pamametermodel.AddMemberTagTaskModel;
import com.d1m.wechat.pamametermodel.MemberTagModel;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.FileUtils;
import com.d1m.wechat.util.Message;

@Controller
@RequestMapping("/member-tag")
public class MemberTagController extends BaseController {

	private Logger log = LoggerFactory.getLogger(MemberTagController.class);

	@Autowired
	private MemberTagService memberTagService;
	
	@Autowired
	private MemberTagCsvService memberTagCsvService;

	@RequestMapping(value = "new.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject create(@RequestBody AddMemberTagModel tags,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		try {
			List<MemberTagDto> memberTagDtos = memberTagService.create(
					getUser(session), getWechatId(session), tags);
			return representation(Message.MEMBER_TAG_CREATE_SUCCESS,
					memberTagDtos);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "{id}/delete.json", method = RequestMethod.DELETE)
	public JSONObject delete(@PathVariable Integer id, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			memberTagService.delete(getUser(session), getWechatId(session), id);
			return representation(Message.MEMBER_TAG_DELETE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}

	@RequestMapping(value = "list.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value={"member:list","system-setting:auto-reply"},logical=Logical.OR)
	public JSONObject list(
			@RequestBody(required = false) MemberTagModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		Page<MemberTag> memberTags = memberTagService.search(
				getWechatId(session), model.getMemberTagTypeId(), model.getName(), 
				model.getSortName(), model.getSortDir(),
				model.getPageNum(), model.getPageSize(), true);
		/*List<MemberTagDto> memberTagDtos = new ArrayList<MemberTagDto>();
		List<MemberTag> result = memberTags.getResult();
		for (MemberTag memberTag : result) {
			memberTagDtos.add(convert(memberTag));
		}*/
		return representation(Message.MEMBER_TAG_LIST_SUCCESS, memberTags,
				model.getPageNum(), model.getPageSize(), memberTags.getTotal());
	}

	private MemberTagDto convert(MemberTag memberTag) {
		MemberTagDto dto = new MemberTagDto();
		dto.setCreatedAt(DateUtil.formatYYYYMMDDHHMMSS(memberTag.getCreatedAt()));
		dto.setId(memberTag.getId());
		dto.setName(memberTag.getName());
		dto.setMemberTagTypeId(memberTag.getMemberTagTypeId());
		return dto;
	}

	@RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject update(@PathVariable Integer id,
			@RequestBody(required = false) MemberTagModel model, HttpSession session,
			HttpServletRequest request, HttpServletResponse response) {
		try {
			if(model == null){
				model = new MemberTagModel();
			}
			memberTagService.update(getUser(session), getWechatId(session), id,
					model.getMemberTagTypeId(), model.getName());
			return representation(Message.MEMBER_TAG_UPDATE_SUCCESS);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@RequestMapping(value = "addtag-csv.json", method = RequestMethod.POST)
	public JSONObject addMemberTagByCSV(
			@RequestParam(required = false) MultipartFile file,
			HttpServletResponse response, HttpSession session) {
		try {
			Upload upload = UploadController.upload(getWechatId(), file, Constants.ADD_MEMBER_TAG_BY_CSV,
					Constants.MEMBER);
			String oriFileName = file.getOriginalFilename();
			String uploadPath =  upload.getAbsolutePath();
			String csv = upload.getAccessPath();
			String csvName = upload.getNewFileName();
			String encode = FileUtils.codeString(uploadPath);
			if(!encode.equals("UTF-8")){
				throw new WechatException(Message.FILE_UTF8_ENCODING_ERROR);
			}
			memberTagService.csvAddMemberTag(getWechatId(session),uploadPath,getUser(session).getId(),oriFileName,csv,csvName);
			
			JSONObject json = new JSONObject();
			json.put("oriName", oriFileName);
			json.put("csvUrl", csv);
			json.put("csvName",csvName);
			json.put("encode", encode);
			return representation(Message.CSV_UPLOAD_SUCCESS, json);
		} catch (Exception e) {
			log.error(e.getMessage());
			return wrapException(e);
		}
	}
	
	@RequestMapping(value = "tag-task.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addMemberTagTaskList(
			@RequestBody(required = false) AddMemberTagTaskModel tagTask,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		if(tagTask == null){
			tagTask = new AddMemberTagTaskModel();
		}
		Page<MemberTagCsv> memberTagCsvs = memberTagCsvService.searchTask(
				getWechatId(session), tagTask, true);
		return representation(Message.MEMBER_TAG_TASK_LIST_SUCCESS, memberTagCsvs.getResult(),
				tagTask.getPageNum(), tagTask.getPageSize(), memberTagCsvs.getTotal());
	}
	
	@RequestMapping(value = "move-tag.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject moveTag(
			@RequestBody(required = false) MemberTagModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		if(model == null){
			model = new MemberTagModel();
		}
		memberTagService.moveTag(getWechatId(session), model);
		return representation(Message.MEMBER_TAG_MOVE_SUCCESS);
	}
	
	@RequestMapping(value = "search.json", method = RequestMethod.POST)
	@ResponseBody
	@RequiresPermissions(value={"member:list","system-setting:auto-reply"},logical=Logical.OR)
	public JSONObject search(
			@RequestBody(required = false) MemberTagModel model,
			HttpSession session, HttpServletRequest request,
			HttpServletResponse response) {
		List<MemberTagDto> dtos = memberTagService.searchName(
				getWechatId(session), model);
		return representation(Message.MEMBER_TAG_SEARCH_SUCCESS, dtos);
	}

}
