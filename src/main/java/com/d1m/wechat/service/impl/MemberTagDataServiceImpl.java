package com.d1m.wechat.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.mapper.MemberTagDataMapper;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.model.enums.MemberTagDataStatus;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.service.MemberTagDataService;
import com.d1m.wechat.util.MyMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

/**
 * Created by jone.wang on 2018/11/6.
 * Description:
 */
@Service
@Slf4j
public class MemberTagDataServiceImpl implements MemberTagDataService {

    @Autowired
    private MemberTagDataMapper memberTagDataMapper;

    @Autowired
    private MemberTagCsvService memberTagCsvService;

    private CsvMapper csvMapper = new CsvMapper();

    @Override
    public MyMapper<MemberTagData> getMapper() {
        return this.memberTagDataMapper;
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertFromExcel(Integer fileId, File file) {
        log.info("Batch insert form file [{}], for fileId [{}]", file.getPath(), fileId);
        if (Objects.isNull(fileId)) {
            log.error("File id is null!");
            return;
        }
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        final List<BatchEntity> entities = ExcelImportUtil.importExcel(file, BatchEntity.
                class, params);
        this.entitiesProcess(entities, fileId);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertFromCsv(Integer fileId, File file) {
        log.info("Batch insert form file [{}], for fileId [{}]", file.getPath(), fileId);
        try {
            final CsvSchema schema = csvMapper.schemaFor(BatchEntity.class).withHeader();
            MappingIterator<BatchEntity> mapping = csvMapper.readerFor(BatchEntity.class).with(schema).readValues(file);
            final List<BatchEntity> entities = mapping.readAll();
            this.entitiesProcess(entities, fileId);

        } catch (IOException e) {
            log.error("Csv to pojo error", e);
        }
    }

    private void entitiesProcess(Collection<BatchEntity> entities, Integer fileId) {
        final int result = memberTagCsvService.updateByPrimaryKeySelective(MemberTagCsv
                .builder()
                .fileId(fileId)
                .rows(entities.size())
                .lastUpdateTime(Timestamp.valueOf(LocalDateTime.now()))
                .status(MemberTagCsvStatus.ALREADY_IMPORTED)
                .build());
        if (result < 1 || CollectionUtils.isEmpty(entities)) {
            log.warn("fileId有误，或者成功解析0行数据！");
            throw new RuntimeException("");
        }

        final List<MemberTagData> memberTagDataList = entities.stream().map(e ->
                MemberTagData
                        .builder()
                        .fileId(fileId)
                        .openId(e.getOpenid())
                        .originalTag(e.getTag())
                        .status(MemberTagDataStatus.UNTREATED)
                        .createdAt(Timestamp.valueOf(LocalDateTime.now()))
                        .build()
        ).collect(Collectors.toList());

        memberTagDataMapper.insertList(memberTagDataList);

        log.info("Batch insert finish!");
    }


    @SuppressWarnings("WeakerAccess")
    @Data
    public static class BatchEntity {
        @Excel(name = "OPEN_ID")
        @JsonProperty(value = "OPEN_ID")
        private String openid;
        @Excel(name = "TAG")
        @JsonProperty(value = "TAG")
        private String tag;
    }
}
