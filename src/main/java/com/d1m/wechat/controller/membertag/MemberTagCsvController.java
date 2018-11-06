package com.d1m.wechat.controller.membertag;

import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.service.MemberTagCsvService;
import io.swagger.annotations.Api;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.ObjectUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
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

    @Value("${batch-add-tags.file-dir}")
    private String fileDir;

    @PostMapping("/csv_excel")
    public BaseResponse batchAddTagsOnCsv(@RequestParam MultipartFile file) throws IOException {
        final String originalFilename = file.getOriginalFilename();
        if (!originalFilename.endsWith(".csv")
                && !originalFilename.endsWith(".xls")
                && !originalFilename.endsWith(".xlsx")) {

            return BaseResponse.builder().msg("不支持的文件格式").build();
        }

        String fileFullName = fileDir + LocalDate.now().toString() + "/" + LocalTime.now().toString() + "_" + file.getName();

        final File targetFile = new File(fileFullName);

        FileUtils.copyInputStreamToFile(file.getInputStream(),
                targetFile);

        final MemberTagCsv.MemberTagCsvBuilder memberTagCsvBuilder = MemberTagCsv
                .builder()
                .oriFile(file.getName())
                .sourceFilePath(fileFullName)
                .fileSize(String.valueOf(file.getSize()))
                .wechatId(getWechatId())
                .creatorId(getUser().getCreatorId())
                .status(MemberTagCsvStatus.IN_PROCESS)
                .format(originalFilename.substring(originalFilename.lastIndexOf(".") + 1));

        if (originalFilename.endsWith(".xls") && originalFilename.endsWith(".xlsx")) {
            memberTagCsvService.insert(memberTagCsvBuilder.build());
        } else {
            byte[] encodeB = new byte[3];
            try (InputStream in = file.getInputStream()) {
                in.read(encodeB);
                if (!ObjectUtils.isEmpty(encodeB)) {
                    final String encode = Integer.toHexString(encodeB[0] & 0xFF) +
                            Integer.toHexString(encodeB[1] & 0xFF) +
                            Integer.toHexString(encodeB[2] & 0xFF);
                    if ("EFBBBF".equals(encode)) {
                        memberTagCsvBuilder.format("UTF-8");
                    }
                }
            }
            memberTagCsvService.insert(memberTagCsvBuilder.build());
        }
        return BaseResponse.builder().build();
    }
}
