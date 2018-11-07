package com.d1m.wechat.controller.membertag;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.model.enums.MemberTagDataStatus;
import com.d1m.wechat.pamametermodel.AddMemberTagTaskModel;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.service.MemberTagDataService;
import com.d1m.wechat.util.Message;
import com.github.pagehelper.Page;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import tk.mybatis.mapper.entity.Example;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by jone.wang on 2018/11/6.
 * Description:
 */
@RestController
@RequestMapping("/member-tag/batch")
@Slf4j
@Api(value = "会员打标签Api2")
public class MemberTagCsvController extends BaseController {

    @Autowired
    private MemberTagCsvService memberTagCsvService;

    @Autowired
    private MemberTagDataService memberTagDataService;

    @Value("${batch-add-tags.file-dir}")
    private String fileDir;

    @PostMapping("/csv_excel")
    @ApiOperation(value = "上传excel或者csv批量为用户打标签")
    public BaseResponse batchAddTagsOnCsv(@RequestParam MultipartFile file) throws Exception {
        final String originalFilename = file.getOriginalFilename();
        if (!originalFilename.endsWith(".csv")
                && !originalFilename.endsWith(".xls")
                && !originalFilename.endsWith(".xlsx")) {

            return BaseResponse.builder().msg("不支持的文件格式").build();
        }

        String fileFullName = fileDir + LocalDate.now().toString() + "/" + LocalTime.now().toString() + "_" + file.getOriginalFilename();

        final File targetFile = new File(fileFullName);

        FileUtils.copyInputStreamToFile(file.getInputStream(),
                targetFile);

        final MemberTagCsv.MemberTagCsvBuilder memberTagCsvBuilder = MemberTagCsv
                .builder()
                .oriFile(file.getOriginalFilename())
                .sourceFilePath(fileFullName)
                .fileSize(String.valueOf(file.getSize()))
                .wechatId(getWechatId())
                .creatorId(getUser().getCreatorId())
                .status(MemberTagCsvStatus.IN_PROCESS)
                .format(originalFilename.substring(originalFilename.lastIndexOf(".") + 1));

        if (originalFilename.endsWith(".csv")) {
            //工具类计算出编码错误
            final String encode = com.d1m.wechat.util.FileUtils.codeString(fileFullName);
            memberTagCsvBuilder.encoding("UTF-8");
        }
        final MemberTagCsv memberTagCsv = memberTagCsvBuilder.build();
        try {
            memberTagCsvService.insert(memberTagCsv);
            if (originalFilename.endsWith(".csv")) {
                memberTagDataService.batchInsertFromCsv(memberTagCsv.getFileId(), targetFile);
            } else {
                memberTagDataService.batchInsertFromExcel(memberTagCsv.getFileId(), targetFile);
            }
        } catch (RuntimeException e) {
            memberTagCsvService.updateByPrimaryKeySelective(MemberTagCsv.builder()
                    .fileId(memberTagCsv.getFileId())
                    .status(MemberTagCsvStatus.PROCESS_FAILURE)
                    .errorMsg(e.getMessage())
                    .build());

            return BaseResponse.builder()
                    .resultCode(Message.CSV_OR_EXCEL_PARSER_FAIL.getCode())
                    .msg(Message.CSV_OR_EXCEL_PARSER_FAIL.getName())
                    .build();
        }
        return BaseResponse.builder()
                .resultCode(Message.FILE_UPLOAD_SUCCESS.getCode())
                .msg(Message.FILE_UPLOAD_SUCCESS.getName()).build();
    }

    @GetMapping(path = "/{id}/fail_export", consumes = MediaType.APPLICATION_OCTET_STREAM_VALUE)
    @ApiOperation(value = "失败数据下载")
    public ResponseEntity<Resource> failDataExport(@PathVariable Integer id, HttpServletResponse response) {
        final MemberTagCsv memberTagCsv = memberTagCsvService.selectByKey(id);
        if (Objects.isNull(memberTagCsv)) {
            return ResponseEntity.ok().body(new ByteArrayResource(("{\"resultCode\": 0,\"msg\":\"找不到上传文件\"}").getBytes(StandardCharsets.UTF_8)));
        }
        final Example example = new Example(MemberTagData.class);
        example.createCriteria()
                .andEqualTo("status", MemberTagDataStatus.PROCESS_FAILURE);
        example.or().andEqualTo("checkStatus", false);
        final List<MemberTagData> memberTagDatas = memberTagDataService.selectByExample(example);
        if (CollectionUtils.isEmpty(memberTagDatas)) {
            return ResponseEntity.ok().body(new ByteArrayResource(("{\"resultCode\": 0,\"msg\":\"没有错误数据\"}").getBytes(StandardCharsets.UTF_8)));
        }
        final List<FailDataExport> failDataExports = memberTagDatas
                .stream()
                .map(FailDataExport::convert)
                .collect(Collectors.toList());
        ResponseEntity<Resource> responseEntity = null;
        try (final Workbook workbook = ExcelExportUtil.exportExcel(new ExportParams(), FailDataExport.class, failDataExports)) {

            try (final ServletOutputStream outputStream = response.getOutputStream()) {

                response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
                response.setHeader("Pragma", "no-cache");
                response.setHeader("Expires", "0");
                response.setHeader("charset", "utf-8");
                response.setHeader("Content-Disposition", "attachment;filename=\"" + URLEncoder.encode(LocalDate.now() + "失败数据.xls", "UTF-8") + "\"");

                workbook.write(outputStream);

                responseEntity = ResponseEntity.ok()
                        .contentType(MediaType.parseMediaType("application/x-msdownload"))
                        .build();

            }
        } catch (IOException e) {
            log.error("Export fail file error", e);
        }
        return responseEntity;
    }

    @Data
    public static class FailDataExport {
        @Excel(name = "OPEN_ID")
        private String openId;
        @Excel(name = "TAG")
        private String errorTag;
        @Excel(name = "FAIL_REASON")
        private String errorMsg;

        public static FailDataExport convert(Object o) {
            final FailDataExport failDataExport = new FailDataExport();
            BeanUtils.copyProperties(o, failDataExport);
            return failDataExport;
        }
    }


    @ApiOperation(value = "会员标签CSV批量导入", tags = "会员标签接口")
    @ApiResponse(code = 200, message = "1-会员标签CSV批量导入成功")
    @RequestMapping(value = "tag-task.json", method = RequestMethod.POST)
    @ResponseBody
    public JSONObject addMemberTagTaskList(
            @ApiParam(name = "AddMemberTagTaskModel", required = false)
            @RequestBody(required = false) AddMemberTagTaskModel tagTask) {
        if (tagTask == null) {
            tagTask = new AddMemberTagTaskModel();
        }
        Page<MemberTagCsv> memberTagCsvs = memberTagCsvService.searchTask(
                getWechatId(), tagTask);
        return representation(Message.MEMBER_TAG_TASK_LIST_SUCCESS, memberTagCsvs.getResult(),
                tagTask.getPageNum(), tagTask.getPageSize(), memberTagCsvs.getTotal());
    }
}
