package com.d1m.wechat.service.impl;

import cn.afterturn.easypoi.excel.ExcelImportUtil;
import cn.afterturn.easypoi.excel.annotation.Excel;
import cn.afterturn.easypoi.excel.entity.ImportParams;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.d1m.common.ds.TenantContext;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.exception.BatchAddTagException;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.MemberMapper;
import com.d1m.wechat.mapper.MemberMemberTagMapper;
import com.d1m.wechat.mapper.MemberTagDataMapper;
import com.d1m.wechat.mapper.MemberTagMapper;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.MemberMemberTag;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.model.enums.MemberTagDataStatus;
import com.d1m.wechat.schedule.SchedulerRestService;
import com.d1m.wechat.service.AsyncService;
import com.d1m.wechat.service.MemberTagCsvService;
import com.d1m.wechat.service.MemberTagDataService;
import com.d1m.wechat.service.MemberTagService;
import com.d1m.wechat.util.CommonUtils;
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

import javax.annotation.Resource;
import java.io.File;
import java.io.IOException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Function;
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
    private MemberMemberTagMapper memberMemberTagMapper;

    @Autowired
    private AsyncService asyncService;

    @Resource
    private TenantHelper tenantHelper;

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
        ImportParams params = new ImportParams();
        params.setHeadRows(1);
        final List<BatchEntity> entities = ExcelImportUtil.importExcel(file, BatchEntity.
         class, params);
        this.entitiesProcess(entities, fileId, runTask);
    }

    @Override
    @Transactional(rollbackFor = Exception.class)
    public void batchInsertFromCsv(Integer fileId, File file, Date runTask) {
        log.info("Batch insert form file [{}], for fileId [{}]", file.getPath(), fileId);
        try {
            final CsvSchema schema = csvMapper.schemaFor(BatchEntity.class).withHeader();
            MappingIterator<BatchEntity> mapping = csvMapper.readerFor(BatchEntity.class).with(schema).readValues(file);
            final List<BatchEntity> entities = mapping.readAll();
            this.entitiesProcess(entities, fileId, runTask);

        } catch (IOException e) {
            log.error("Csv to pojo error", e);
        }
    }

    private void entitiesProcess(Collection<BatchEntity> entities, Integer fileId, Date runTask) {

        final MemberTagCsv memberTagCsv = memberTagCsvService
         .selectByKey(MemberTagCsv.builder().fileId(fileId).build());
        if (Objects.isNull(memberTagCsv)) {
            throw new WechatException(Message.FILE_EXT_NOT_SUPPORT);
        }
        memberTagCsv.setStatus(MemberTagCsvStatus.ALREADY_IMPORTED);
        memberTagCsv.setRows(entities.size());
        memberTagCsv.setLastUpdateTime(Timestamp.valueOf(LocalDateTime.now()));
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
        log.info("taskName:{}", memberTagCsv.getTask());
        log.info("runTask:{}", runTask);
        log.info("memberTagCsv:{}", JSON.toJSON(memberTagCsv));
        //异步发起任务调度
        asyncService.asyncInvoke(() -> schedulerTask(memberTagCsv.getTask(), runTask, memberTagCsv));
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
            // 多数据源支持
            String domain = tenantHelper.getTenantByWechatId(record.getWechatId());
            if (StringUtils.isNotBlank(domain)) {
                TenantContext.setCurrentTenant(domain);
                log.info("This domain: " + domain);
            }
            TenantContext.setCurrentTenant(domain);
            log.info("获取当前租户: " + TenantContext.getCurrentTenant());
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
    public void checkDataIsOK(CopyOnWriteArrayList<MemberTagData> list) throws Exception {
        if (CollectionUtils.isNotEmpty(list)) {
            Boolean b = true;
            for (MemberTagData memberTagData : list) {
                log.info("======正在进行数据检查》》》》》============");
                log.info("memberTagData>>" + JSONObject.toJSON(memberTagData));

                //校验OpenID
                if (StringUtils.isEmpty(memberTagData.getOpenId())) {
                    updateErrorStatus(memberTagData.getDataId(), "会员OpenID不能为空");
                    log.info("dataId:" + memberTagData.getDataId() + "，会员OpenID不能为空！");
                    //list.remove(memberTagData);
                    b = false;
                }

                //校验标签
                if (StringUtils.isEmpty(memberTagData.getOriginalTag())) {
                    updateErrorStatus(memberTagData.getDataId(), "标签不能为空");
                    log.info("dataId:" + memberTagData.getDataId() + "，标签不能为空！");
                    //list.remove(memberTagData);
                    b = false;
                }

                if (b) {
                    //检查openID是否存在
                    if (selectCount(memberTagData.getOpenId()) <= 0) {
                        updateErrorStatus(memberTagData.getDataId(), "不存在此会员OpenID");
                        log.info("dataId:" + memberTagData.getDataId() + "，不存在此会员OpenID！");
                        //list.remove(memberTagData);
                        //b=false;
                    }
                    //检查标签是否存在
                    checkTagsIsExist(memberTagData.getOriginalTag(), memberTagData.getWechatId(), memberTagData.getDataId());
                    //若errortag=originaltag,则表示标签都不存在
                    updateCheckStats(memberTagData.getDataId(), memberTagData.getOriginalTag());
                }
            }

        }
    }

    /**
     * 因必填信息引起的错误，需要更新状态为完成
     *
     * @param dataId
     * @param errorMsg
     * @throws Exception
     */
    public void updateErrorStatus(Integer dataId, String errorMsg) throws Exception {
        try {
            MemberTagData memberTagData = new MemberTagData();
            memberTagData.setDataId(dataId);
            memberTagData.setErrorMsg(errorMsg);
            memberTagData.setCheckStatus(false);
            memberTagData.setStatus(MemberTagDataStatus.PROCESS_SUCCEED);
            int t = memberTagDataMapper.updateByPrimaryKeySelective(memberTagData);
            log.info("因必填信息引起的错误，需要更新状态为完成:{}", t);

        } catch (Exception e) {
            e.printStackTrace();
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
            int t = memberTagDataMapper.updateByPrimaryKeySelective(memberTagData);
            log.info("更新数据错误原因:{}", t);

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
            log.info("错误标签:{}", tagData.getErrorTag());
            if (StringUtils.isNotBlank(tagData.getErrorTag())) {
                memberTagData.setCheckStatus(false);
                memberTagData.setStatus(MemberTagDataStatus.PROCESS_SUCCEED);
                log.info("如果有一个错误标签，则该条数据不用做加签处理:{}", tagData.getErrorTag());
            } else {
                memberTagData.setCheckStatus(true);
            }
            int t = memberTagDataMapper.updateByPrimaryKeySelective(memberTagData);
            if (t == 1) {
                log.info("更新数据检查状态" + t);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 更新标签和原因
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
                int t = memberTagDataMapper.updateByPrimaryKeySelective(memberTagData);
                log.info("更新标签和原因:{}", t);
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


    /**
     * 更新上传数据状态
     *
     * @param fileId
     * @param status
     */
    public void updateDataStatus(Integer fileId, Integer status) {
        int t = memberTagDataMapper.updateDataStatus(fileId, status);
        log.info("更新上传数据状态：{}", t);
    }


    /**
     * 获取待处理的数据
     *
     * @param fileId
     */
    public CopyOnWriteArrayList<MemberTagData> getMembertagCsvData(Integer fileId) {

        return memberTagDataMapper.getMembertagCsvData(fileId);
    }

    /**
     * 获取待加签的正确数据
     *
     * @param fileId
     */
    public List<MemberTagData> getCsvData(Integer fileId) {
        MemberTagData memberTagData = new MemberTagData();
        memberTagData.setFileId(fileId);
        memberTagData.setCheckStatus(true);
        memberTagData.setStatus(MemberTagDataStatus.IN_PROCESS);
        return memberTagDataMapper.select(memberTagData);
    }

    /**
     * 批量导入加标签处理
     *
     * @param list
     * @throws Exception
     */
    public Boolean addTags(List<MemberTagData> list) throws Exception {

        List<MemberMemberTag> tagsList = new CopyOnWriteArrayList<>();
        MemberTagDataStatus status = null;
        String errorMsg = null;
        Boolean result = false;
        int suceessCount = 0;
        try {
            if (CollectionUtils.isNotEmpty(list)) {
                for (MemberTagData memberTagData : list) {
                    String[] tags = memberTagData.getTag().split("\\|");
                    for (String tag : tags) {
                        List<MemberMemberTag> mmtList = memberMemberTagMapper.selecteIsExist(memberTagData.getOpenId()
                         , tag, memberTagData.getWechatId());
                        if (CollectionUtils.isNotEmpty(mmtList)) {
                            log.info("已加标签数据：" + JSON.toJSON(memberTagData));
                            //list.remove(memberTagData);
                        } else {
                            MemberMemberTag mmTag = new MemberMemberTag();
                            mmTag.setWechatId(memberTagData.getWechatId());
                            mmTag.setOpenId(memberTagData.getOpenId());
                            MemberTag memberTag = new MemberTag();
                            memberTag.setName(tag);
                            memberTag.setWechatId(memberTagData.getWechatId());
                            MemberTag mTag = memberTagMapper.selectOne(memberTag);
                            mmTag.setMemberTagId(mTag.getId());
                            Member member = new Member();
                            member.setWechatId(memberTagData.getWechatId());
                            member.setOpenId(memberTagData.getOpenId());
                            Member m = memberMapper.selectOne(member);
                            mmTag.setCreatedAt(new Date());
                            mmTag.setMemberId(m.getId());
                            tagsList.add(mmTag);
                            tagsList = tagsList.stream().filter(CommonUtils.distinctByKey(t -> t.getMemberId() + t.getWechatId()
                             + t.getOpenId() + t.getMemberTagId())).collect(Collectors.toList());
                            log.info("设置标签数据集合：" + JSON.toJSON(mmTag));
                        }
                    }
                }

                if (CollectionUtils.isNotEmpty(tagsList)) {
                    log.info("待加标签数据：", JSON.toJSON(tagsList));
                    suceessCount = memberMemberTagMapper.insertList(tagsList);
                    log.info("======加签中，已完成：》》》》》=" + suceessCount + "===========");
                }
                status = MemberTagDataStatus.PROCESS_SUCCEED;
                result = true;
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
                    log.info("批量导入加标签处理方法:" + t);
                }
            }
        }
        return result;
    }

    private boolean contains(List<MemberMemberTag> list, MemberTag memberTag) {
        if (list == null || list.isEmpty()) {
            return false;
        }
        for (MemberMemberTag tag : list) {
            if (tag.getId().equals(memberTag.getId())) {
                return true;
            }
        }
        return false;
    }

}
