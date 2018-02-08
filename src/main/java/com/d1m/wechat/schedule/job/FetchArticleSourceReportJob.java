package com.d1m.wechat.schedule.job;

import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import javax.annotation.Resource;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.parser.Feature;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import cn.d1m.wechat.client.model.WxArticleData;
import cn.d1m.wechat.client.model.WxArticleDetail;
import cn.d1m.wechat.client.model.common.WxList;
import com.d1m.wechat.mapper.ReportArticleHourSourceMapper;
import com.d1m.wechat.mapper.ReportArticleSourceDetailMapper;
import com.d1m.wechat.mapper.ReportArticleSourceMapper;
import com.d1m.wechat.model.ReportArticleHourSource;
import com.d1m.wechat.model.ReportArticleSource;
import com.d1m.wechat.model.ReportArticleSourceDetail;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.schedule.BaseJobHandler;
import com.d1m.wechat.service.ReportArticleHourSourceService;
import com.d1m.wechat.service.ReportArticleSourceService;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.DateUtil;
import com.d1m.wechat.wechatclient.WechatClientDelegate;

@Slf4j
@JobHander(value="fetchArticleSourceReportJob")
@Component
public class FetchArticleSourceReportJob extends BaseJobHandler {

    @Resource
    private ReportArticleSourceService reportService;

    @Resource
    private ReportArticleHourSourceService reportHourService;

    @Resource
    private WechatService wechatService;

    @Resource
    private ReportArticleSourceMapper reportArticleSourceMapper;

    @Resource
    private ReportArticleHourSourceMapper reportArticleHourSourceMapper;

    @Resource
    private ReportArticleSourceDetailMapper reportArticleSourceDetailMapper;

    @Override
    public ReturnT<String> run(String... strings) throws Exception {
        try {
            List<Wechat> list = wechatService.getWechatList();
            for (Wechat wechat : list) {
                for (int i = -1; i > -6; i--) {
                    Date date = DateUtil.getDate(i);
                    saveReportArticleSource(wechat.getId(), date);
                    saveReportArticleHourSource(wechat.getId(), date);
                    saveReportArticleDetail(wechat.getId(), date);
                }
            }
            return ReturnT.SUCCESS;

        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("获取群发图文报表失败：" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

    private void saveReportArticleDetail(Integer wechatId, Date date) {
        List<ReportArticleSourceDetail> reportArticleSourceDetailList = new LinkedList<>();
        WxList<WxArticleData> wxList = WechatClientDelegate.getArticleTotal(wechatId, date, date);
        for (WxArticleData wxArticleData : wxList.getData()) {
            for (WxArticleDetail wxArticleDetail : wxArticleData.getDetails()) {
                ReportArticleSourceDetail detail = JSON.parseObject(JSON.toJSONString(wxArticleDetail), ReportArticleSourceDetail.class, Feature.InternFieldNames);
                detail.setMsgid(wxArticleData.getMsgId());
                detail.setRefDate(wxArticleData.getRefDate());
                detail.setTitle(wxArticleData.getTitle());
                detail.setWechatId(wechatId);
                reportArticleSourceDetailList.add(detail);
            }
        }
        try {
            reportArticleSourceDetailMapper.insertList(reportArticleSourceDetailList);
        } catch (Exception e) {
            log.error("ReportArticleSourceDetailList保存失败", e);
        }

    }

    private void saveReportArticleSource(Integer wechatId, Date date) {
        List<WxArticleData> articleList = WechatClientDelegate.getArticleSummary(wechatId, date, date).get();
        for (WxArticleData aa : articleList) {
            ReportArticleSource record = new ReportArticleSource();
            record.setWechatId(wechatId);
            record.setMsgid(aa.getMsgId());
            record.setRefDate(aa.getRefDate());
            List<ReportArticleSource> list = reportArticleSourceMapper.select(record);
            if(list!=null && list.size()>0){
                record = list.get(0);
            }else {
                record = null;
            }
            if (record != null) {
                record.setTitle(aa.getTitle());
                record.setCreatedAt(date);
                record.setPageUser(aa.getIntPageReadUser());
                record.setPageCount(aa.getIntPageReadCount());
                record.setOriPageUser(aa.getOriPageReadUser());
                record.setOriPageCount(aa.getOriPageReadCount());
                record.setShareUser(aa.getShareUser());
                record.setShareCount(aa.getShareCount());
                record.setAddFavUser(aa.getAddToFavUser());
                record.setAddFavCount(aa.getAddToFavCount());
                reportService.updateNotNull(record);
            } else {
                ReportArticleSource ras = new ReportArticleSource();
                ras.setWechatId(wechatId);
                ras.setCreatedAt(date);
                ras.setRefDate(aa.getRefDate());
                ras.setMsgid(aa.getMsgId());
                ras.setTitle(aa.getTitle());
                ras.setPageUser(aa.getIntPageReadUser());
                ras.setPageCount(aa.getIntPageReadCount());
                ras.setOriPageUser(aa.getOriPageReadUser());
                ras.setOriPageCount(aa.getOriPageReadCount());
                ras.setShareUser(aa.getShareUser());
                ras.setShareCount(aa.getShareCount());
                ras.setAddFavUser(aa.getAddToFavUser());
                ras.setAddFavCount(aa.getAddToFavCount());
                reportService.save(ras);
            }
        }
    }

    private void saveReportArticleHourSource(Integer wechatId, Date date) {
        List<WxArticleData> articleHourList = WechatClientDelegate.getUserReadHour(wechatId, date, date).get();
        for (WxArticleData aa : articleHourList) {
            ReportArticleHourSource record = new ReportArticleHourSource();
            record.setWechatId(wechatId);
            record.setRefDate(aa.getRefDate());
            record.setRefHour(String.valueOf(aa.getRefHour()));
            List<ReportArticleHourSource> list = reportArticleHourSourceMapper.select(record);
            if(list!=null && list.size()>0){
                record = list.get(0);
            }else {
                record = null;
            }
            if (record != null) {
                record.setCreatedAt(date);
                record.setUserSource(aa.getUserSource());
                record.setPageUser(aa.getIntPageReadUser());
                record.setPageCount(aa.getIntPageReadCount());
                record.setOriPageUser(aa.getOriPageReadUser());
                record.setOriPageCount(aa.getOriPageReadCount());
                record.setShareUser(aa.getShareUser());
                record.setShareCount(aa.getShareCount());
                record.setAddFavUser(aa.getAddToFavUser());
                record.setAddFavCount(aa.getAddToFavCount());
                reportHourService.updateNotNull(record);
            } else {
                ReportArticleHourSource rahs = new ReportArticleHourSource();
                rahs.setWechatId(wechatId);
                rahs.setCreatedAt(date);
                rahs.setRefDate(aa.getRefDate());
                rahs.setRefHour(String.valueOf(aa.getRefHour()));
                rahs.setUserSource(aa.getUserSource());
                rahs.setPageUser(aa.getIntPageReadUser());
                rahs.setPageCount(aa.getIntPageReadCount());
                rahs.setOriPageUser(aa.getOriPageReadUser());
                rahs.setOriPageCount(aa.getOriPageReadCount());
                rahs.setShareUser(aa.getShareUser());
                rahs.setShareCount(aa.getShareCount());
                rahs.setAddFavUser(aa.getAddToFavUser());
                rahs.setAddFavCount(aa.getAddToFavCount());
                reportHourService.save(rahs);
            }
        }
    }

}
