package com.d1m.wechat.service.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.Future;
import java.util.stream.Collectors;

import javax.annotation.Resource;

import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.ibatis.session.SqlSessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.common.ds.TenantContext;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.domain.dao.MemberTagDataDao;
import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.exception.BatchAddTagException;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.mapper.MemberMemberTagMapper;
import com.d1m.wechat.mapper.MemberTagDataMapper;
import com.d1m.wechat.mapper.MemberTagMapper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.Tag;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.model.enums.MemberTagDataStatus;
import com.d1m.wechat.schedule.SchedulerRestService;
import com.d1m.wechat.service.MemberService;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.service.MemberTagDataService;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.MappingIterator;
import com.fasterxml.jackson.dataformat.csv.CsvMapper;
import com.fasterxml.jackson.dataformat.csv.CsvSchema;
import com.xxl.job.core.biz.model.ReturnT;

import cn.afterturn.easypoi.excel.annotation.Excel;

/**
 * Created by jone.wang on 2018/11/6.
 * Description:
 */
@Service
public class MemberTagDataServiceImpl implements MemberTagDataService {

    private static final Logger log = LoggerFactory.getLogger(MemberTagDataServiceImpl.class);
    //默认每批次处理数量
    private static final Integer BATCHSIZE = 10000;

    @Autowired
    private MemberTagDataMapper memberTagDataMapper;

    @Autowired
    private MemberTagCsvService memberTagCsvService;

    @Autowired
    private MemberTagMapper memberTagMapper;

    @Autowired
    private MemberMapper memberMapper;

    @Autowired
    private SchedulerRestService schedulerRestService;

    @Autowired
    private MemberMemberTagMapper memberMemberTagMapper;

    @Autowired
    private MemberService memberService;

    @Resource
    private TenantHelper tenantHelper;

    @Autowired
    private MemberTagDataDao memberTagDataDao;


    private CsvMapper csvMapper = new CsvMapper();


    @Override
    public MyMapper<MemberTagData> getMapper() {
        return this.memberTagDataMapper;
    }


    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertFromExcel(Integer fileId, File file, Date runTask) {
        log.info("Batch insert form file [{}], for fileId [{}]", file.getPath(), fileId);
        if (Objects.isNull(fileId)) {
            log.error("File id is null!");
            return;
        }

        final ToCSV toCSV = new ToCSV();

        try {
            String sourcePath = file.getAbsolutePath();
            final String csvFilePath = sourcePath.replace(file.getName(), "");
            toCSV.convertExcelToCSV(file.getAbsolutePath(), csvFilePath, ",");
            final String csvFile = file.getAbsolutePath()
             .replace(".xlsx", ".csv")
             .replace(".xls", ".csv");
            this.batchInsertFromCsv(fileId, new File(csvFile), runTask);
        } catch (IOException e) {
            log.error("Excel to Csv error", e);
        }

    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertFromCsv(Integer fileId, File file, Date runTask) {
        log.info("Batch insert form file [{}], for fileId [{}]", file.getPath(), fileId);
        FileInputStream fis = null;
        InputStreamReader isr = null;
        BufferedReader br = null;
        try {
            CsvSchema schema;
            //1、判断将要插入的数量
            schema = csvMapper.schemaFor(BatchEntity.class).withHeader();
            int count = 0;
            fis = new FileInputStream(file);
            isr = new InputStreamReader(fis);
            br = new BufferedReader(isr);
            String line = br.readLine();
            String firstLine = null;
            StringBuilder builder = new StringBuilder();
            if (StringUtils.isNotBlank(line)) {
                firstLine = line;
            }
            MappingIterator<BatchEntity> mapping;
            final MemberTagCsv memberTagCsv = memberTagCsvService
             .selectByKey(fileId);
            if (Objects.isNull(memberTagCsv)) {
                throw new WechatException(Message.FILE_EXT_NOT_SUPPORT);
            }
            Integer batchSize = memberService.getBatchSize(memberTagCsv.getWechatId()) != null ?
             memberService.getBatchSize(memberTagCsv.getWechatId()) : BATCHSIZE;
            while (StringUtils.isNotBlank(line)) {
                builder.append(line);
                builder.append("\n");
                line = br.readLine();
                count++;
                if (count % batchSize == 0) {
                    mapping = csvMapper.readerFor(BatchEntity.class)
                     .with(schema).readValues(builder.toString());
                    final List<BatchEntity> entities = mapping.readAll();
                    this.entitiesProcess(entities, fileId, memberTagCsv.getWechatId());
                    builder = new StringBuilder();
                    builder.append(firstLine);
                    builder.append("\n");
                }

            }


            final String csrStr = builder.toString();
            if (StringUtils.isNotBlank(csrStr)) {
                mapping = csvMapper.readerFor(BatchEntity.class)
                 .with(schema).readValues(csrStr);
                final List<BatchEntity> entities = mapping.readAll();
                if (CollectionUtils.isNotEmpty(entities)) {
                    this.entitiesProcess(entities, fileId, memberTagCsv.getWechatId());
                }
            }

            memberTagCsv.setStatus(MemberTagCsvStatus.ALREADY_IMPORTED);
            memberTagCsv.setRows(count);
            memberTagCsv.setLastUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
            memberTagCsv.setCreatedAt(Timestamp.valueOf(LocalDateTime.now()));
            memberTagCsvService.updateByPrimaryKeySelective(memberTagCsv);

            log.info("taskName:{}", memberTagCsv.getTask());
            log.info("runTask:{}", runTask);
            log.info("memberTagCsv:{}", JSON.toJSON(memberTagCsv));
            //发起任务调度
            schedulerTask(memberTagCsv.getTask(), runTask, memberTagCsv);
            log.info("Batch schedulerTask finish!");
        } catch (IOException e) {
            log.error("Csv to pojo error", e);
        } finally {
            IOUtils.closeQuietly(isr);
            IOUtils.closeQuietly(fis);
            IOUtils.closeQuietly(br);
        }
    }

    private void entitiesProcess(Collection<BatchEntity> entities, Integer fileId, Integer wechatId) {


        if (CollectionUtils.isEmpty(entities)) {
            log.warn("fileId有误，或者成功解析0行数据！");
            throw new BatchAddTagException("解析失败，请下载excel模板按照格式添加数据！");
        }

        final List<MemberTagData> memberTagDataList = entities.stream().map(e ->
         new MemberTagData
          .Builder()
          .fileId(fileId)
          .openId(e.getOpenid())
          .wechatId(wechatId)
          .originalTag(e.getTag())
          .status(MemberTagDataStatus.UNTREATED)
          .createdAt(Timestamp.valueOf(LocalDateTime.now()))
          .build()
        ).collect(Collectors.toList());
        memberTagDataDao.batchInsert(memberTagDataList);
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
            // 1、获取数据源
            String domain = tenantHelper.getTenantByWechatId(record.getWechatId());
            if (StringUtils.isNotBlank(domain)) {
                TenantContext.setCurrentTenant(domain);
                log.info("This domain: " + domain);
            }
            TenantContext.setCurrentTenant(domain);
            log.info("获取当前租户: " + TenantContext.getCurrentTenant());

            //5、准备发起异步任务调度
            Map<String, Object> jobMap = new HashMap<>();
            jobMap.put("jobGroup", 1);
            jobMap.put("jobDesc", taskName);
            jobMap.put("jobCron", DateUtil.cron.format(runTask));
            jobMap.put("executorHandler", "memberTagCsvJob");
            jobMap.put("executorParam", "-d" + TenantContext.getCurrentTenant() + "," + record.getFileId() + "," + record.getWechatId());

            ReturnT<String> returnT = schedulerRestService.addJob(jobMap);
            log.info("jobMap:" + JSON.toJSON(jobMap));
            log.info("returnT发起异步任务调度返回结果:" + JSON.toJSON(returnT));
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



    public static class BatchEntity {
        @Excel(name = "OPEN_ID", isImportField = "true")
        @JsonProperty(value = "OPEN_ID", required = true)
        private String openid;
        @Excel(name = "TAG", isImportField = "true")
        @JsonProperty(value = "TAG", required = true)
        private String tag;

        public String getOpenid() {
            return openid;
        }

        public void setOpenid(String openid) {
            this.openid = openid;
        }

        public String getTag() {
            return tag;
        }

        public void setTag(String tag) {
            this.tag = tag;
        }

        @Override
        public String toString() {
            return "BatchEntity [openid=" + openid + ", tag=" + tag + "]";
        }
    }


    /**
     * 导入数据检查
     *
     * @param list
     */
    public CopyOnWriteArrayList<MemberTagData> checkDataIsOK(CopyOnWriteArrayList<MemberTagData> list) throws Exception {
        CopyOnWriteArrayList<MemberTagData> rightList = new CopyOnWriteArrayList<>();
        List<MemberTagData> statusList = new ArrayList<>();
        if (CollectionUtils.isNotEmpty(list)) {
            Boolean b = true;
            for (MemberTagData memberTagData : list) {
                log.info("======正在进行数据检查》》》》》============");
                log.info("memberTagData>>" + JSONObject.toJSON(memberTagData));
                //校验OpenID
                if (StringUtils.isEmpty(memberTagData.getOpenId())) {
                    updateErrorStatus("会员OpenID不能为空", memberTagData);
                    b = false;
                }

                //校验标签
                if (StringUtils.isEmpty(memberTagData.getOriginalTag())) {
                    updateErrorStatus("标签不能为空", memberTagData);
                    b = false;
                }

                if (b) {
                    //检查openID是否存在
                    if (selectCount(memberTagData.getOpenId()) <= 0) {
                        updateErrorStatus("不存在此会员OpenID", memberTagData);
                    }
                    //检查标签是否存在
                    checkTagsIsExist(memberTagData.getOriginalTag(), memberTagData);
                    //更新数据检查状态
                    MemberTagData updatetag = updateCheckStats(memberTagData);
                    statusList.add(memberTagData);
                    //如果检查状态为true，则把该标签数据添加到list中
                    if (updatetag.getCheckStatus()) {
                        rightList.add(updatetag);
                    }
                }
            }
            memberTagDataDao.updateBatch(statusList);
        }
        log.info("返回待加签的list：", JSON.toJSON(rightList));
        return rightList;
    }



    /**
     * 因必填信息引起的错误，需要更新状态为完成
     *
     * @param
     * @param errorMsg
     * @throws Exception
     */
    public void updateErrorStatus(String errorMsg, MemberTagData memberTagData) throws Exception {
        try {
            memberTagData.setErrorMsg(setErrorMsg(memberTagData.getErrorMsg(), errorMsg));
            memberTagData.setCheckStatus(false);
            memberTagData.setStatus(MemberTagDataStatus.PROCESS_SUCCEED);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }


    /**
     * 更新数据检查状态
     *
     * @param
     * @throws Exception
     */
    public MemberTagData updateCheckStats(MemberTagData memberTagData) throws Exception {
        try {
            if (StringUtils.isNotBlank(memberTagData.getErrorTag())) {
                memberTagData.setCheckStatus(false);
                memberTagData.setStatus(MemberTagDataStatus.PROCESS_SUCCEED);
                log.info("如果有一个错误标签，则该条数据不用做加签处理:{}", memberTagData.getErrorTag());
            } else {
                memberTagData.setCheckStatus(true);
            }
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
        return memberTagData;
    }


    /**
     * 更新标签和原因
     *
     * @param
     * @param errorMsg
     * @throws Exception
     */
    public void updateErrorTagAndErrorMsg(String errorMsg, String tag, String errorTag, MemberTagData memberTagData) throws Exception {
        try {
            memberTagData.setErrorTag(setTags(memberTagData.getErrorTag(), errorTag));
//            memberTagData.setTag(setTags(memberTagData.getTag(), tag));
            memberTagData.setDataId(memberTagData.getDataId());
            memberTagData.setErrorMsg(setErrorMsg(memberTagData.getErrorMsg(), errorMsg));
        } catch (Exception e) {
            log.error(e.getMessage(), e);
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
        if (StringUtils.isNotBlank(tag)) {
            if (StringUtils.isNotBlank(source)) {
                resultValue = source + "|" + tag;
            } else {
                resultValue = tag;
            }
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
        if (StringUtils.isNotBlank(errorMsg)) {
            if (StringUtils.isNotBlank(source)) {
                resultValue = source + "；" + errorMsg;
            } else {
                resultValue = errorMsg;
            }
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
     * @param
     * @return
     */
    public void checkTagsIsExist(String tagStr, MemberTagData currentMemberTagData) throws Exception {
        if (StringUtils.isNotBlank(tagStr)) {
            String[] tags = tagStr.split("\\|");
            for (String tag : tags) {
                MemberTag memberTag = new MemberTag();
                memberTag.setName(tag);
                memberTag.setWechatId(currentMemberTagData.getWechatId());
                memberTag.setStatus((byte) 1);
                memberTag = memberTagMapper.selectOne(memberTag);
                if (memberTag == null) {
                    String errorMsg = tag + "：不存在此标签";
                    updateErrorTagAndErrorMsg(errorMsg, null, tag, currentMemberTagData);
                } else {
                    updateErrorTagAndErrorMsg(null, tag, null, currentMemberTagData);
                }
            }
            currentMemberTagData.setTag(tagStr);
        }
    }


    /**
     * 更新上传数据状态
     *
     * @param list
     */
    public int updateDataStatus(CopyOnWriteArrayList<MemberTagData> list) {
        List<MemberTagData> collect = list.stream().map(m -> {
            MemberTagData memberTagData = new MemberTagData();
            BeanUtils.copyProperties(m, memberTagData);
            memberTagData.setStatus(MemberTagDataStatus.IN_PROCESS); //处理中
            return memberTagData;
        }).collect(Collectors.toList());
        return memberTagDataDao.updateBatch(collect);
    }


    /**
     * 获取待处理的数据
     *
     * @param fileId
     */
    public CopyOnWriteArrayList<MemberTagData> getMembertagCsvData(Integer fileId, int pageNum, int batchSize) {

        return memberTagDataMapper.getMembertagCsvData(fileId, pageNum, batchSize);
    }


    /**
     * 批量导入加标签处理
     *
     * @param list
     * @throws Exception
     */
    public Integer addTags(List<MemberTagData> list) throws Exception {
        List<Tag> tagsList = new CopyOnWriteArrayList<>();
        MemberTagDataStatus status = null;
        String errorMsg = null;
        Integer suceessCount = 0;
        try {
            if (CollectionUtils.isNotEmpty(list)) {
                for (MemberTagData memberTagData : list) {
                    String[] tags = memberTagData.getTag().split("\\|");
                    for (String tag : tags) {
                        Tag mmTag = new Tag();
                        mmTag.setWechatId(memberTagData.getWechatId());
                        mmTag.setOpenId(memberTagData.getOpenId());
                        mmTag.setMemberTagName(tag);
                        mmTag.setCreatedAt(new Date());
                        tagsList.add(mmTag);
                        //此处不需要去重，数据库已经做了唯一索引
//                        tagsList = tagsList.stream().filter(CommonUtils.distinctByKey(t -> t.getWechatId()
//                         + t.getOpenId()+t.getMemberTagName())).collect(Collectors.toList());
                    }
                }
                if (CollectionUtils.isNotEmpty(tagsList)) {
                    log.info("待加标签数据：", JSON.toJSON(tagsList));
                    memberMemberTagMapper.insertOrUpdateList(tagsList);
                    suceessCount = tagsList.size();
                    log.info("======加签中，已完成：》》》》》=" + suceessCount + "===========");
                }
                status = MemberTagDataStatus.PROCESS_SUCCEED;
            }
        } catch (Exception e) {
            status = MemberTagDataStatus.PROCESS_FAILURE;
            errorMsg = "导入失败";
            e.printStackTrace();
            log.info(errorMsg + e.getMessage());
        } finally {
            if (CollectionUtils.isNotEmpty(list)) {
                for (MemberTagData memberTagData : list) {
                    MemberTagData tagData = new MemberTagData();
                    tagData.setStatus(status);
                    tagData.setDataId(memberTagData.getDataId());
                    tagData.setErrorMsg(errorMsg);
                    int t = memberTagDataMapper.updateByPrimaryKeySelective(tagData);
                }
            }
        }
        return suceessCount;
    }


    /**
     * 分批处理
     */
    public Integer batchExecute(CopyOnWriteArrayList<MemberTagData> list) {
        Integer suceessCount = 0;
        if (CollectionUtils.isNotEmpty(list)) {
            //设置上传数据状态为处理中
            updateDataStatus(list);//1 代表处理中
            log.info("======准备数据标签检查》》》》》============");
            try {
                //检查数据，并返回正确待加签的数据
                List<MemberTagData> addTagsDataList = checkDataIsOK(list);
                if (CollectionUtils.isNotEmpty(addTagsDataList)) {
                    log.info("======准备加签》》》》》============");
                    suceessCount = addTags(addTagsDataList);
                }

            } catch (Exception e) {
                log.info("分批处理:", e.getMessage());
            }
        }

        return suceessCount;
    }

}
