package com.d1m.wechat.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.common.ds.TenantContext;
import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.exception.BatchAddTagException;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.mapper.MemberTagDataMapper;
import com.d1m.wechat.mapper.MemberTagMapper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.model.enums.MemberTagDataStatus;
import com.d1m.wechat.schedule.SchedulerRestService;
import com.d1m.wechat.service.AsyncService;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.service.MemberTagDataService;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.util.MyMapper;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.xxl.job.core.biz.model.ReturnT;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
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

    @Autowired
    private MemberTagService memberTagService;

    @Autowired
    private MemberTagMapper memberTagMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private SchedulerRestService schedulerRestService;

    @Autowired
    private AsyncService asyncService;

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
        long currentTime = System.currentTimeMillis();
        long m = 60L * 1000L;
        long runAt = currentTime + m;
        Date runTask = new Date(runAt);
        String dateTask = DateUtil.formatYYYYMMDDHHMMSS(runTask);
        String taskName = "MemberAddTagCSV_" + dateTask;

        final MemberTagCsv memberTagCsv = memberTagCsvService
         .selectByKey(MemberTagCsv.builder().fileId(fileId).build());
        if (Objects.isNull(memberTagCsv)) {
            throw new WechatException(Message.FILE_EXT_NOT_SUPPORT);
        }
        memberTagCsv.setStatus(MemberTagCsvStatus.ALREADY_IMPORTED);
        memberTagCsv.setRows(entities.size());
        memberTagCsv.setLastUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
        memberTagCsv.setTask(dateTask);
        memberTagCsv.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
        memberTagCsvService.updateByPrimaryKeySelective(memberTagCsv);

        if (CollectionUtils.isEmpty(entities)) {
            log.warn("fileId有误，或者成功解析0行数据！");
            throw new BatchAddTagException("解析失败，请下载excel模板按照格式添加数据！");
        }

        final List<MemberTagData> memberTagDataList = entities.stream().map(e ->
         MemberTagData
          .builder()
          .fileId(fileId)
          .openId(e.getOpenid())
          .wechatId(memberTagCsv.getWechatId())
          .originalTag(e.getTag())
          .status(MemberTagDataStatus.UNTREATED)
          .createdAt(Timestamp.valueOf(LocalDateTime.now()))
          .build()
        ).collect(Collectors.toList());

        memberTagDataMapper.insertList(memberTagDataList);

        log.info("Batch insert finish!");
        log.info("taskName:"+taskName);
        log.info("runTask:"+runTask);
        log.info("memberTagCsv:"+JSON.toJSON(memberTagCsv));
        //异步发起任务调度
        asyncService.asyncInvoke(() -> schedulerTask(taskName, runTask, memberTagCsv));
        log.info("Batch schedulerTask finish!");
    }

    /**
     * 发起异步任务调度
     *
     * @param taskName
     * @param runTask
     * @param record
     */
    public void schedulerTask(String taskName, Date runTask, MemberTagCsv record) {
        try {
            Map<String, Object> jobMap = new HashMap<>();
            jobMap.put("jobGroup", 1);
            jobMap.put("jobDesc", taskName);
            jobMap.put("jobCron", DateUtil.cron.format(runTask));
            jobMap.put("executorHandler", "memberTagCsvJob");
            jobMap.put("executorParam", "-d" + TenantContext.getCurrentTenant() + "," + record.getFileId());

            ReturnT<String> returnT = schedulerRestService.addJob(jobMap);
            log.info("jobMap:" + JSON.toJSON(jobMap));
            log.info("returnT执行结果:" + JSON.toJSON(returnT));
            if (ReturnT.FAIL_CODE == returnT.getCode()) {
                throw new WechatException(
                 Message.MEMBER_ADD_TAG_BY_CSV_ERROR);
            }
        } catch (Exception e) {
            log.error(e.getLocalizedMessage(), e);
            throw new WechatException(
             Message.MEMBER_ADD_TAG_BY_CSV_ERROR);
        }
    }

    @SuppressWarnings("WeakerAccess")
    @Data
    public static class BatchEntity {
        @Excel(name = "OPEN_ID", isImportField = "true")
        @JsonProperty(value = "OPEN_ID", required = true)
        private String openid;
        @Excel(name = "TAG", isImportField = "true")
        @JsonProperty(value = "TAG", required = true)
        private String tag;
    }


    /**
     * 导入数据检查
     *
     * @param list
     */
    public void checkDataIsOK(List<MemberTagData> list) throws Exception {
        if (CollectionUtils.isNotEmpty(list)) {
            for (MemberTagData memberTagData : list) {
                log.info("======正在进行数据检查》》》》》============");
                log.info("memberTagData>>" + JSONObject.toJSON(memberTagData));

                //校验OpenID
                if (StringUtils.isEmpty(memberTagData.getOpenId())) {
                    updateErrorMsg(memberTagData.getDataId(), "会员OpenID不能为空");
                    log.info("dataId:" + memberTagData.getDataId() + "，会员OpenID不能为空！");
                    list.remove(memberTagData);
                }

                //校验标签
                if (StringUtils.isEmpty(memberTagData.getOriginalTag())) {
                    updateErrorMsg(memberTagData.getDataId(), "标签不能为空");
                    log.info("dataId:" + memberTagData.getDataId() + "，标签不能为空！");
                    list.remove(memberTagData);
                }

                //检查openID是否存在
                if (selectCount(memberTagData.getOpenId()) <= 0) {
                    updateErrorMsg(memberTagData.getDataId(), "不存在此会员OpenID");
                    log.info("dataId:" + memberTagData.getDataId() + "，不存在此会员OpenID！");
                    list.remove(memberTagData);
                }

                //检查标签是否存在
                checkTagsIsExist(memberTagData.getOriginalTag(), memberTagData.getWechatId(), memberTagData.getDataId());
                updateCheckStats(memberTagData.getDataId(), memberTagData.getOriginalTag());
            }

        }
    }


    /**
     * 更新数据错误原因
     *
     * @param dataId
     * @param errorMsg
     * @throws Exception
     */
    public void updateErrorMsg(Integer dataId, String errorMsg) throws Exception {
        try {
            MemberTagData memberTagData = new MemberTagData();
            memberTagData.setDataId(dataId);
            memberTagData.setErrorMsg(errorMsg);
            memberTagData.setCheckStatus(false);
            memberTagDataMapper.updateByPrimaryKey(memberTagData);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新数据检查状态
     *
     * @param dataId
     * @param originalTag
     * @throws Exception
     */
    public void updateCheckStats(Integer dataId, String originalTag) throws Exception {
        try {
            MemberTagData memberTagData = new MemberTagData();
            memberTagData.setDataId(dataId);
            MemberTagData tagData = memberTagDataMapper.selectOne(memberTagData);
            if (tagData.getOriginalTag().equals(originalTag)) {
                memberTagData.setDataId(dataId);
                memberTagData.setCheckStatus(false);
                int t = memberTagDataMapper.updateByPrimaryKey(memberTagData);
                if (t == 1) {
                    log.info("原始标签" + originalTag + "，都不存在！");
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新错误标签和原因
     *
     * @param dataId
     * @param errorMsg
     * @throws Exception
     */
    public void updateErrorTagAndErrorMsg(Integer dataId, String errorMsg, String tag, String errorTag) throws Exception {
        try {
            MemberTagData memberTagData = new MemberTagData();
            memberTagData.setDataId(dataId);
            MemberTagData tagData = memberTagDataMapper.selectOne(memberTagData);
            if (tagData != null) {
                memberTagData.setErrorTag(setTags(tagData.getErrorTag(), errorTag));
                memberTagData.setTag(setTags(tagData.getTag(), tag));
                memberTagData.setDataId(tagData.getDataId());
                memberTagData.setErrorMsg(setErrorMsg(tagData.getErrorMsg(), errorMsg));
                memberTagDataMapper.updateByPrimaryKey(memberTagData);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 拼接tags
     *
     * @param source
     * @param tag
     * @return
     */
    public String setTags(String source, String tag) {
        String resultValue = null;
        if (StringUtils.isNotBlank(source)) {
            resultValue = source + "|" + tag;
        } else {
            resultValue = tag;
        }
        return resultValue;
    }


    /**
     * 拼接ErrorMsg
     *
     * @param source
     * @param errorMsg
     * @return
     */
    public String setErrorMsg(String source, String errorMsg) {
        String resultValue = null;
        if (StringUtils.isNotBlank(source)) {
            resultValue = source + "；" + errorMsg;
        } else {
            resultValue = errorMsg;
        }
        return resultValue;
    }


    /**
     * 查询openID是否存在
     *
     * @param openId
     * @return
     */
    public Integer selectCount(String openId) {
        Member member = new Member();
        member.setOpenId(openId);
        return memberMapper.selectCount(member);

    }


    /**
     * 检查标签是否存在
     *
     * @param tagStr
     * @param wechatId
     * @return
     */
    public void checkTagsIsExist(String tagStr, Integer wechatId, Integer dataId) throws Exception {
        if (StringUtils.isNotBlank(tagStr)) {
            String[] tags = tagStr.split("\\|");
            for (String tag : tags) {
                MemberTag memberTag = new MemberTag();
                memberTag.setName(tag);
                memberTag.setWechatId(wechatId);
                memberTag.setStatus((byte) 1);
                memberTag = memberTagMapper.selectOne(memberTag);
                if (memberTag == null) {
                    String errorMsg = tag + ",不存在此标签";
                    //Integer dataId, String errorMsg, String tag, String errorTag
                    updateErrorTagAndErrorMsg(dataId, errorMsg, null, tag);
                } else {
                    updateErrorTagAndErrorMsg(dataId, null, tag, null);
                }
            }
        }
    }


}
