package com.d1m.wechat.controller.membertag;

import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.dto.ImportCsvDto;
import com.github.pagehelper.Page;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;

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
import com.d1m.wechat.schedule.job.MemberTagSyncJob;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.util.Constants;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.FileUtils;
import com.d1m.wechat.util.Message;

@Controller
@RequestMapping("/member-tag")
@Api(value = "会员标签API", tags = "会员标签接口")
public class MemberTagController extends BaseController {

    private Logger log = LoggerFactory.getLogger(MemberTagController.class);

    @Autowired
    private MemberTagService memberTagService;

    @Autowired
    private MemberTagCsvService memberTagCsvService;

    @Autowired
    private MemberTagSyncJob memberTagSyncJob;

    @ApiOperation(value = "创建会员标签", tags = "会员标签接口")
    @ApiResponse(code = 200, message = "1-创建会员标签成功")
    @RequestMapping(value = "new.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject create(
     @ApiParam("AddMemberTagModel")
     @RequestBody AddMemberTagModel tags,
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

    @ApiOperation(value = "删除会员标签", tags = "会员标签接口")
    @ApiResponse(code = 200, message = "1-删除会员标签成功")
    @RequestMapping(value = "{id}/delete.json", method = RequestMethod.DELETE)
    public JSONObject delete(
     @ApiParam("会员标签ID")
     @PathVariable Integer id, HttpSession session,
     HttpServletRequest request, HttpServletResponse response) {
        try {
            memberTagService.delete(getUser(session), getWechatId(session), id);
            return representation(Message.MEMBER_TAG_DELETE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "获取会员标签列表", tags = "会员标签接口")
    @ApiResponse(code = 200, message = "1-获取会员标签列表成功")
    @RequestMapping(value = "list.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"member:list", "system-setting:auto-reply"}, logical = Logical.OR)
    public JSONObject list(
     @ApiParam(name = "MemberTagModel", required = false)
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

    @ApiOperation(value = "更新会员标签", tags = "会员标签接口")
    @ApiResponse(code = 200, message = "1-更新会员标签成功")
    @RequestMapping(value = "{id}/update.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject update(
     @ApiParam("会员标签ID")
     @PathVariable Integer id,
     @ApiParam(name = "MemberTagModel", required = false)
     @RequestBody(required = false) MemberTagModel model, HttpSession session,
     HttpServletRequest request, HttpServletResponse response) {
        try {
            if (model == null) {
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


    @ApiOperation(value = "csv导入文件上传", tags = "会员标签接口")
    @ApiResponse(code = 200, message = "1-csv导入文件上传成功")
    @RequestMapping(value = "addtag-csv.json", method = RequestMethod.POST)
    public JSONObject addMemberTagByCSV2(@ApiParam(name = "上传文件", required = false)
                                         @RequestParam(required = false) MultipartFile file) {
        try {
            //1、校验和上传文件
            Upload upload = UploadController.upload(getWechatId(), file, Constants.ADD_MEMBER_TAG_BY_CSV, Constants.MEMBER);
            if (!FileUtils.codeString(upload.getAbsolutePath()).equals("UTF-8")) {
                throw new WechatException(Message.FILE_UTF8_ENCODING_ERROR);
            }
            //2、解析上传数据，并保存
            ImportCsvDto dto = new ImportCsvDto();
            dto.setCsv(upload.getAccessPath());
            dto.setCsvName(upload.getNewFileName());
            dto.setOriFileName(file.getOriginalFilename());
            dto.setUploadPath(upload.getAbsolutePath());
            dto.setUserId(getUser().getId());
            dto.setWechatId(getWechatId());
            memberTagService.csvAddMemberTag(dto);
            //3、添加返回结果
            JSONObject json = new JSONObject();
            json.put("oriName", file.getOriginalFilename());
            json.put("csvUrl", upload.getAccessPath());
            json.put("csvName", upload.getNewFileName());
            json.put("encode", FileUtils.codeString(upload.getAbsolutePath()));
            return representation(Message.CSV_UPLOAD_SUCCESS, json);

        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }

    }


    @ApiOperation(value = "会员标签CSV批量导入", tags = "会员标签接口")
    @ApiResponse(code = 200, message = "1-会员标签CSV批量导入成功")
    @RequestMapping(value = "tag-task.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addMemberTagTaskList(
     @ApiParam(name = "AddMemberTagTaskModel", required = false)
     @RequestBody(required = false) AddMemberTagTaskModel tagTask,
     HttpSession session, HttpServletRequest request,
     HttpServletResponse response) {
        if (tagTask == null) {
            tagTask = new AddMemberTagTaskModel();
        }
        Page<MemberTagCsv> memberTagCsvs = memberTagCsvService.searchTask(
         getWechatId(session), tagTask, true);
        return representation(Message.MEMBER_TAG_TASK_LIST_SUCCESS, memberTagCsvs.getResult(),
         tagTask.getPageNum(), tagTask.getPageSize(), memberTagCsvs.getTotal());
    }

    @ApiOperation(value = "会员标签移动", tags = "会员标签接口")
    @ApiResponse(code = 200, message = "1-会员标签移动成功")
    @RequestMapping(value = "move-tag.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject moveTag(
     @ApiParam(name = "MemberTagModel", required = false)
     @RequestBody(required = false) MemberTagModel model,
     HttpSession session, HttpServletRequest request,
     HttpServletResponse response) {
        if (model == null) {
            model = new MemberTagModel();
        }
        memberTagService.moveTag(getWechatId(session), model);
        return representation(Message.MEMBER_TAG_MOVE_SUCCESS);
    }

    @ApiOperation(value = "会员标签查询", tags = "会员标签接口")
    @ApiResponse(code = 200, message = "1-会员标签查询成功")
    @RequestMapping(value = "search.json", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions(value = {"member:list", "system-setting:auto-reply"}, logical = Logical.OR)
    public JSONObject search(
     @ApiParam(name = "MemberTagModel", required = false)
     @RequestBody(required = false) MemberTagModel model,
     HttpSession session, HttpServletRequest request,
     HttpServletResponse response) {
        List<MemberTagDto> dtos = memberTagService.searchName(
         getWechatId(session), model);
        return representation(Message.MEMBER_TAG_SEARCH_SUCCESS, dtos);
    }

    @ApiOperation(value = "双向同步会员标签", tags = "双向同步会员接口")
    @ApiResponse(code = 200, message = "1-双向同步会员标签成功")
    @RequestMapping(value = "sync.json", method = RequestMethod.GET)
    public JSONObject sync(
     String shopname,
     HttpSession session,
     HttpServletRequest request, HttpServletResponse response) {
        try {
            String wechatid = getWechatId(session).toString();
            log.info("shopname>>" + shopname);
            memberTagSyncJob.run(wechatid, shopname);
            return representation(Message.MEMBER_TAG_DELETE_SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
}
