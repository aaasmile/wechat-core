package com.d1m.wechat.controller.membertag;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.exception.BatchAddTagException;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.service.MemberTagDataService;
import com.d1m.wechat.util.Message;
import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.time.LocalDate;
import java.time.LocalTime;

/**
 * Created by jone.wang on 2018/11/6.
 * Description:
 */
@RestController
@RequestMapping("/member-tag/batch")
@Api(value = "会员打标签Api2")
public class MemberTagCsvController extends BaseController {

    @Autowired
    private MemberTagCsvService memberTagCsvService;

    @Autowired
    private MemberTagDataService memberTagDataService;

    @Value("${batch-add-tags.file-dir}")
    private String fileDir;

    @PostMapping("/csv_excel")
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

}
