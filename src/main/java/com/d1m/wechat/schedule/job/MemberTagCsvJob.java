package com.d1m.wechat.schedule.job;

import com.alibaba.fastjson.JSON;
import com.d1m.wechat.domain.entity.MemberTagCsv;
import com.d1m.wechat.domain.entity.MemberTagData;
import com.d1m.wechat.dto.MemberStatsCounts;
import com.d1m.wechat.mapper.*;
import com.d1m.wechat.model.Member;
import com.d1m.wechat.model.MemberMemberTag;
import com.d1m.wechat.model.MemberTag;
import com.d1m.wechat.model.enums.MemberTagCsvStatus;
import com.d1m.wechat.model.enums.MemberTagDataStatus;
import com.d1m.wechat.schedule.BaseJobHandler;
import com.d1m.wechat.service.*;

import com.d1m.wechat.util.ParamUtil;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@JobHander(value = "memberTagCsvJob")
@Component
@Slf4j
public class MemberTagCsvJob extends BaseJobHandler {

    @Resource
    private MemberTagDataService memberTagDataService;

    @Resource
    private MemberMemberTagMapper memberMemberTagMapper;

    @Resource
    private MemberTagDataMapper memberTagDataMapper;

    @Resource
    private MemberTagCsvMapper memberTagCsvMapper;

    @Resource
    private MemberMapper memberMapper;

    @Resource
    private MemberTagMapper memberTagMapper;

    @Override
    public ReturnT<String> run(String... strings) throws Exception {
        String errorMsg = null;
        try {

            if (strings != null) {
                //获取导入文件id
                Integer fileId = ParamUtil.getInt(strings[0], null);
                XxlJobLogger.log("获取导入文件id : " + fileId);
                log.info("获取导入文件id : " + fileId);
                //设置上传文件为导入中状态
                updateFileStatus(fileId, MemberTagCsvStatus.IN_IMPORT);
                //设置上传数据状态为处理中
                updateDataStatus(fileId, MemberTagDataStatus.IN_PROCESS);
                //数据标签检查
                List<MemberTagData> list = getMembertagCsvData(fileId);
                if (CollectionUtils.isNotEmpty(list)) {
                    log.info("======准备数据标签检查》》》》》============");
                    memberTagDataService.checkDataIsOK(list);
                } else {
                    errorMsg = "没有找到数据！";
                    updateCsv(fileId, errorMsg);
                    log.info("fileId:" + fileId + "," + errorMsg);
                }

                //获取待加签的正确数据
                List<MemberTagData> addTagsDataList = getCsvData(fileId);
                if (CollectionUtils.isNotEmpty(list)) {
                    log.info("======准备加签》》》》》============");
                    addTags(addTagsDataList);
                } else {
                    errorMsg = "没有找到正确数据！";
                    updateCsv(fileId, errorMsg);
                    log.info("fileId:" + fileId + "," + errorMsg);
                }

                //统计结果
                log.info("======统计结果》》》》》============");
                updateCountCsv(fileId);
            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("会员导入批量加标签失败：" + e.getMessage());
            return ReturnT.FAIL;
        }
    }


    /**
     * 更新上传文件信息
     *
     * @param fileId
     */
    private void updateCsv(Integer fileId, String errorMsg) {
        MemberTagCsv csv = new MemberTagCsv();
        csv.setFileId(fileId);
        csv.setErrorMsg(errorMsg);
        csv.setSuccessCount(0);
        csv.setFailCount(0);
        csv.setStatus(MemberTagCsvStatus.PROCESS_SUCCEED);
        memberTagCsvMapper.updateByPrimaryKey(csv);
    }

    /**
     * 获取待处理的数据
     *
     * @param fileId
     */
    public List<MemberTagData> getMembertagCsvData(Integer fileId) {
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
        return memberTagDataMapper.select(memberTagData);
    }


    /**
     * 批量导入加标签处理
     *
     * @param list
     * @throws Exception
     */
    public Boolean addTags(List<MemberTagData> list) throws Exception {
        List<MemberMemberTag> tagsList = new ArrayList<>();
        MemberTagDataStatus status = null;
        String errorMsg = null;
        Boolean result = false;
        int suceessCount = 0;
        try {
            for (MemberTagData memberTagData : list) {
                String[] tags = memberTagData.getTag().split("\\|");
                for (String tag : tags) {
                    List<MemberMemberTag> mmtList = memberMemberTagMapper.selecteIsExist(memberTagData.getOpenId()
                     , tag, memberTagData.getWechatId());
                    if (CollectionUtils.isNotEmpty(mmtList)) {
                        log.info("已加标签数据：" + JSON.toJSON(memberTagData));
                        list.remove(memberTagData);
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
                        mmTag.setMemberId(m.getId());
                        if (!tagsList.contains(mmTag)) {
                            log.info("设置标签数据集合：" + JSON.toJSON(mmTag));
                            tagsList.add(mmTag);
                        }
                    }
                }
            }
            log.info("待加标签数据：" + JSON.toJSON(tagsList));
            if (CollectionUtils.isNotEmpty(tagsList)) {
                suceessCount = memberMemberTagMapper.insertList(tagsList);
                log.info("======加签中，已完成：》》》》》=" + suceessCount + "===========");
                suceessCount++;
            }
            status = MemberTagDataStatus.PROCESS_SUCCEED;
            result = true;
        } catch (Exception e) {
            status = MemberTagDataStatus.PROCESS_FAILURE;
            errorMsg = "数据执行加签异常！";
            e.printStackTrace();
            log.info(errorMsg + e.getMessage());
        } finally {
            for (MemberTagData memberTagData : list) {
                MemberTagData tagData = new MemberTagData();
                tagData.setStatus(status);
                tagData.setDataId(memberTagData.getDataId());
                tagData.setErrorTag(errorMsg);
                int t = memberTagDataMapper.updateByPrimaryKeySelective(tagData);
                log.info("批量导入加标签处理方法:" + t);
            }
        }
        return result;
    }


    /**
     * 更新上传信息表统计数据
     *
     * @param fileId
     */
    public void updateCountCsv(Integer fileId) {
        MemberStatsCounts memberStatsCounts = memberTagDataMapper.getCount(fileId);
        if (memberStatsCounts != null) {
            MemberTagCsv csv = new MemberTagCsv();
            csv.setFailCount(memberStatsCounts.getFailCount());
            csv.setSuccessCount(memberStatsCounts.getSuccessCount());
            int t = memberTagCsvMapper.updateByPrimaryKeySelective(csv);
            log.info("更新上传信息表统计数据结果：" + t);
        }
    }


    /**
     * 更新上传文件
     *
     * @param fileId
     * @param
     * @param status
     */
    public void updateFileStatus(Integer fileId, MemberTagCsvStatus status) {
        MemberTagCsv csv = new MemberTagCsv();
        csv.setStatus(status);
        csv.setFileId(fileId);
        int t = memberTagCsvMapper.updateByPrimaryKeySelective(csv);
        log.info("更新上传文件：" + t);

    }


    /**
     * 更新上传数据状态
     *
     * @param fileId
     * @param status
     */
    public void updateDataStatus(Integer fileId, MemberTagDataStatus status) {
        MemberTagData data = new MemberTagData();
        data.setStatus(status);
        data.setFileId(fileId);
        memberTagDataMapper.updateByPrimaryKeySelective(data);
    }


}
