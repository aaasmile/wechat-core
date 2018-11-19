package com.d1m.wechat.controller.membertag;

import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.time.LocalDate;
import java.util.Date;
import java.util.List;
import java.util.Objects;
import java.util.UUID;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.stream.Collectors;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.poi.ss.usermodel.Workbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.dto.MemberTagCsvDto;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.pamametermodel.AddMemberTagTaskModel;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.service.MemberTagDataService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.FileUploadConfigUtil;
import com.d1m.wechat.util.Message;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import com.fasterxml.jackson.databind.SequenceWriter;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.github.pagehelper.Page;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import io.swagger.annotations.ApiResponse;
import tk.mybatis.mapper.entity.Example;

/**
 * Created by jone.wang on 2018/11/6. Description:
 */
@RestController
@RequestMapping("/tagsCsv")
@Api(value = "会员打标签Api2")
public class MemberTagCsvController extends BaseController {

	private static final Logger log = LoggerFactory.getLogger(MemberTagCsvController.class);
	@Autowired
	private MemberTagCsvService memberTagCsvService;

	@Autowired
	private MemberTagDataService memberTagDataService;

	private CsvMapper csvMapper = new CsvMapper();

	@ApiOperation(value = "测试用户打标签job")
	@RequestMapping("csv-excel-test.json")
	public BaseResponse csvExcelTest(Integer fileId) {
		try {
			log.info("MemberTagCsvController....csvExcelTest....fileId...." + fileId);
			// 数据标签检查
			CopyOnWriteArrayList<MemberTagData> list = memberTagDataService.getMembertagCsvData(fileId);
			log.info("MemberTagCsvController....csvExcelTest....list...." + (list == null ? 0 : list.size()));
			if (CollectionUtils.isNotEmpty(list)) {
				log.info("MemberTagCsvController....csvExcelTest....updateFileStatus....");
				// 设置上传文件为处理中状态
				memberTagCsvService.updateFileStatus(fileId, MemberTagCsvStatus.IN_PROCESS);
				log.info("MemberTagCsvController....csvExcelTest....asyncCsvJobBatch....");
				// 异步发起批量处理
				memberTagDataService.asyncCsvJobBatch(list, fileId);
			}
			return new BaseResponse.Builder().resultCode(Message.CSV_TEST_SUCCESS.getCode())
					.msg(Message.CSV_TEST_SUCCESS.getName()).build();
		} catch (Exception e) {
			log.error(e.getMessage(), e);
			return new BaseResponse.Builder().resultCode(Message.CSV_TEST_FAIL.getCode())
					.msg(Message.CSV_TEST_FAIL.getName()).build();
		}
	}

	@ApiOperation(value = "上传excel或者csv批量为用户打标签")
	@ApiResponse(code = 200, message = "导入文件上传成功")
	@RequestMapping(value = "csv-excel.json", method = RequestMethod.POST)
	public BaseResponse batchAddTagsOnCsv(@RequestParam MultipartFile file) throws Exception {
		final String originalFilename = file.getOriginalFilename();
		if (!originalFilename.endsWith(".csv") && !originalFilename.endsWith(".xls")
				&& !originalFilename.endsWith(".xlsx")) {

			return new BaseResponse.Builder().msg("上传失败，格式有误，请上传.xlsx或.csv（utf-8格式）文件").build();
		}
		FileUploadConfigUtil instance = FileUploadConfigUtil.getInstance();
		String uploadPath = instance.getValue(getWechatId(), "upload_path");
		log.info("upload_path : " + uploadPath);
		String fileFullName = uploadPath + "/" + DateUtil.formatYYYYMMDD(new Date()) + "/"
				+ UUID.randomUUID().toString() + "_" + file.getOriginalFilename();
		log.info("fileFullName : " + fileFullName);
		final File targetFile = new File(fileFullName);

		FileUtils.copyInputStreamToFile(file.getInputStream(), targetFile);
		long currentTime = System.currentTimeMillis();
		long m = 60L * 1000L;
		long runAt = currentTime + m;
		Date runTask = new Date(runAt);
		String dateTask = DateUtil.formatYYYYMMDDHHMMSS(runTask);
		String taskName = "MemberAddTagCSV_" + dateTask;
		log.info("任务名称:{}", taskName);
		log.info("runTask:{}", runTask);

		final MemberTagCsv.Builder memberTagCsvBuilder = new MemberTagCsv.Builder()
				.oriFile(file.getOriginalFilename()).sourceFilePath(fileFullName)
				.fileSize(String.valueOf(file.getSize())).wechatId(getWechatId()).creatorId(getUser().getId())
				.task(taskName).status(MemberTagCsvStatus.IN_PROCESS)
				.format(originalFilename.substring(originalFilename.lastIndexOf(".") + 1));

		if (originalFilename.endsWith(".csv")) {
			// 工具类计算出编码错误
			memberTagCsvBuilder.encoding("UTF-8");
		}
		final MemberTagCsv memberTagCsv = memberTagCsvBuilder.build();
		try {
			memberTagCsvService.insert(memberTagCsv);
			if (originalFilename.endsWith(".csv")) {
				memberTagDataService.batchInsertFromCsv(memberTagCsv.getFileId(), targetFile, runTask);
			} else {
				memberTagDataService.batchInsertFromExcel(memberTagCsv.getFileId(), targetFile, runTask);
			}
		} catch (RuntimeException e) {
			memberTagCsvService.updateByPrimaryKeySelective(new MemberTagCsv.Builder().fileId(memberTagCsv.getFileId())
					.status(MemberTagCsvStatus.IMPORT_FAILURE).errorMsg(e.getMessage()).build());

			return new BaseResponse.Builder().resultCode(Message.CSV_OR_EXCEL_PARSER_FAIL.getCode()).msg(e.getMessage())
					.build();
		}
		return new BaseResponse.Builder().resultCode(Message.FILE_UPLOAD_SUCCESS.getCode())
				.msg(Message.FILE_UPLOAD_SUCCESS.getName()).build();
	}

	@RequestMapping(value = "{id}/fail-export.json", method = RequestMethod.GET)
	@ApiOperation(value = "失败数据下载")
	public BaseResponse failDataExport(@PathVariable Integer id, HttpServletResponse response) {
		Workbook workbook = null;
		SequenceWriter writer = null;
		final MemberTagCsv memberTagCsv = memberTagCsvService.selectByKey(id);
		if (Objects.isNull(memberTagCsv)) {
			return new BaseResponse.Builder().resultCode(0).msg("找不到上传文件").build();
		}
		final Example example = new Example(MemberTagData.class);
		example.createCriteria().andEqualTo("fileId", id).andIsNotNull("errorMsg");
		final List<MemberTagData> memberTagDatas = memberTagDataService.selectByExample(example);
		if (CollectionUtils.isEmpty(memberTagDatas)) {
			return new BaseResponse.Builder().resultCode(0).msg("没有错误数据").build();
		}

		final List<FailDataExport> failDataExports = memberTagDatas.stream().map(FailDataExport::convert)
				.collect(Collectors.toList());

		final ExportParams exportParams = new ExportParams();
		String format = StringUtils.isNotBlank(memberTagCsv.getFormat()) ? memberTagCsv.getFormat() : "xlsx";
		if ("xlsx".equals(format)) {
			exportParams.setType(ExcelType.XSSF);
			workbook = ExcelExportUtil.exportExcel(exportParams, FailDataExport.class, failDataExports);
		} else if ("xls".equals(format)) {
			exportParams.setType(ExcelType.HSSF);
			workbook = ExcelExportUtil.exportExcel(exportParams, FailDataExport.class, failDataExports);
		}
		log.info("下载格式：{}", format);
		String importFileName = "失败数据." + format;
		log.info("succDataExports:{}", JSON.toJSON(failDataExports));
		try {
			try (ServletOutputStream outputStream = response.getOutputStream()) {
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Expires", "0");
				response.setHeader("charset", "utf-8");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + URLEncoder.encode(LocalDate.now() + importFileName, "UTF-8") + "\"");
				if ("csv".equals(format)) {
					CsvSchema schema = csvMapper.schemaFor(FailDataExport.class).withHeader().withLineSeparator("\r\n")
							.withoutQuoteChar();
					writer = csvMapper.writerFor(FailDataExport.class).with(schema).writeValues(outputStream);
					writer.writeAll(failDataExports);
					writer.close();
				} else {
					workbook.write(outputStream);
				}
			}
		} catch (IOException e) {
			log.error("Export fail file error", e);
		}
		return null;
	}

	@RequestMapping(value = "{id}/success-export.json", method = RequestMethod.GET)
	@ApiOperation(value = "成功数据下载")
	public BaseResponse successDataExport(@PathVariable Integer id, HttpServletResponse response) {
		Workbook workbook = null;
		SequenceWriter writer = null;
		final MemberTagCsv memberTagCsv = memberTagCsvService.selectByKey(id);
		if (Objects.isNull(memberTagCsv)) {
			return new BaseResponse.Builder().resultCode(0).msg("找不到上传文件").build();
		}
		final Example example = new Example(MemberTagData.class);
		example.createCriteria().andEqualTo("fileId", id).andIsNull("errorMsg");
		final List<MemberTagData> memberTagDatas = memberTagDataService.selectByExample(example);
		if (CollectionUtils.isEmpty(memberTagDatas)) {
			return new BaseResponse.Builder().resultCode(0).msg("没有数据").build();
		}
		final List<SuccDataExports> succDataExports = memberTagDatas.stream().map(SuccDataExports::convert)
				.collect(Collectors.toList());

		final ExportParams exportParams = new ExportParams();
		String format = StringUtils.isNotBlank(memberTagCsv.getFormat()) ? memberTagCsv.getFormat() : "xlsx";
		if ("xlsx".equals(format)) {
			exportParams.setType(ExcelType.XSSF);
			workbook = ExcelExportUtil.exportExcel(exportParams, SuccDataExports.class, succDataExports);
		} else if ("xls".equals(format)) {
			exportParams.setType(ExcelType.HSSF);
			workbook = ExcelExportUtil.exportExcel(exportParams, SuccDataExports.class, succDataExports);
		}
		log.info("下载格式：{}", format);
		String importFileName = "成功数据." + format;
		log.info("succDataExports:{}", JSON.toJSON(succDataExports));
		try {
			try (ServletOutputStream outputStream = response.getOutputStream()) {
				response.setHeader("Cache-Control", "no-cache, no-store, must-revalidate");
				response.setHeader("Pragma", "no-cache");
				response.setHeader("Expires", "0");
				response.setHeader("charset", "utf-8");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"" + URLEncoder.encode(LocalDate.now() + importFileName, "UTF-8") + "\"");
				if ("csv".equals(format)) {
					CsvSchema schema = csvMapper.schemaFor(SuccDataExports.class).withHeader().withLineSeparator("\r\n")
							.withoutQuoteChar();
					writer = csvMapper.writerFor(SuccDataExports.class).with(schema).writeValues(outputStream);
					writer.writeAll(succDataExports);
					writer.close();
				} else {
					workbook.write(outputStream);
				}
			}
		} catch (IOException e) {
			log.error("Export fail file error", e);
		}
		return null;
	}

	@JsonPropertyOrder({ "OPEN_ID", "TAG", "FAIL_REASON" })
	public static class FailDataExport {
		@Excel(name = "OPEN_ID")
		@JsonProperty(value = "OPEN_ID")
		private String openId;
		@Excel(name = "TAG")
		@JsonProperty(value = "TAG")
		private String originalTag;
		@Excel(name = "FAIL_REASON")
		@JsonProperty(value = "FAIL_REASON")
		private String errorMsg;

		public static FailDataExport convert(Object o) {
			final FailDataExport failDataExport = new FailDataExport();
			BeanUtils.copyProperties(o, failDataExport);
			return failDataExport;
		}

		public String getOpenId() {
			return openId;
		}

		public void setOpenId(String openId) {
			this.openId = openId;
		}

		public String getOriginalTag() {
			return originalTag;
		}

		public void setOriginalTag(String originalTag) {
			this.originalTag = originalTag;
		}

		public String getErrorMsg() {
			return errorMsg;
		}

		public void setErrorMsg(String errorMsg) {
			this.errorMsg = errorMsg;
		}

		@Override
		public String toString() {
			return "FailDataExport [openId=" + openId + ", originalTag=" + originalTag + ", errorMsg=" + errorMsg + "]";
		}
	}

	public static class SuccDataExports {
		@Excel(name = "OPEN_ID")
		@JsonProperty(value = "OPEN_ID")
		private String openId;
		@Excel(name = "TAG")
		@JsonProperty(value = "TAG")
		private String originalTag;

		public static SuccDataExports convert(Object o) {
			final SuccDataExports succDataExport = new SuccDataExports();
			BeanUtils.copyProperties(o, succDataExport);
			return succDataExport;
		}

		public String getOpenId() {
			return openId;
		}

		public void setOpenId(String openId) {
			this.openId = openId;
		}

		public String getOriginalTag() {
			return originalTag;
		}

		public void setOriginalTag(String originalTag) {
			this.originalTag = originalTag;
		}

		@Override
		public String toString() {
			return "SuccDataExports [openId=" + openId + ", originalTag=" + originalTag + "]";
		}
	}

	@ApiOperation(value = "导入加签任务列表", tags = "会员标签接口")
	@ApiResponse(code = 200, message = "1-会员标签CSV批量导入成功")
	@RequestMapping(value = "tag-task.json", method = RequestMethod.POST)
	@ResponseBody
	public JSONObject addMemberTagTaskList(
			@ApiParam(name = "AddMemberTagTaskModel", required = false) @RequestBody(required = false) AddMemberTagTaskModel tagTask) {
		if (tagTask == null) {
			tagTask = new AddMemberTagTaskModel();
		}
		Page<MemberTagCsvDto> memberTagCsvs = memberTagCsvService.searchTask(getWechatId(), tagTask);
		return representation(Message.MEMBER_TAG_TASK_LIST_SUCCESS, memberTagCsvs.getResult(), tagTask.getPageNum(),
				tagTask.getPageSize(), memberTagCsvs.getTotal());
	}
}
