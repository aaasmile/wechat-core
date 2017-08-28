package com.d1m.wechat.schedule.job;

import java.util.Date;
import java.util.List;

import com.d1m.wechat.schedule.BaseJobHandler;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import cn.d1m.wechat.client.model.WxUserCumulate;
import cn.d1m.wechat.client.model.WxUserSummary;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.model.ReportUserSource;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.service.ReportUserSourceService;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.DateUtil;

/**
 * 定时抓取微信数据来源报表
 * @author d1m
 */
@JobHander(value="fetchUserSourceReportJob")
@Component
public class FetchUserSourceReportJob extends BaseJobHandler {

	@Autowired
	private ReportUserSourceService reportService;

	@Autowired
	private WechatService wechatService;
	
	@Override
	public ReturnT<String> run(String... strings) throws Exception {
		try {
			Date beginDate = DateUtil.getDate(-7);
            Date endDate = DateUtil.getYestoday();
			List<Wechat> list = wechatService.getWechatList();
			for (Wechat wechat:list) {
				// 查询用户来源
				List<WxUserSummary> summaryList = WechatClientDelegate.getUserSummary(wechat.getId(), beginDate, endDate).get();
				XxlJobLogger.log("summaryList size : " + summaryList.size());
				for(WxUserSummary ua:summaryList){
					ReportUserSource us = reportService.getRecordByDate(wechat.getId(), DateUtil.formatYYYYMMDD(ua.getRefDate()));
					if(us!=null){
						setUserSource(us,ua);
						us.setCreatedAt(new Date());
						reportService.updateAll(us);
					}else{
						us = new ReportUserSource();
						us.setWechatId(wechat.getId());
						us.setDate(DateUtil.formatYYYYMMDD(ua.getRefDate()));
						us.setCreatedAt(new Date());
						setUserSource(us,ua);
						reportService.save(us);
					}
				}
				
				// 查询用户累计
				List<WxUserCumulate> cumulateList = WechatClientDelegate.getUserCumulate(wechat.getId(), beginDate, endDate).get();
				XxlJobLogger.log("cumulateList size : " + cumulateList.size());
				for(WxUserCumulate ua:cumulateList){
					ReportUserSource us = reportService.getRecordByDate(wechat.getId(), DateUtil.formatYYYYMMDD(ua.getRefDate()));
					if(us!=null){
						us.setCumulateUser(ua.getCumulateUser());
						us.setCreatedAt(new Date());
						reportService.updateAll(us);
					}else{
						us = new ReportUserSource();
						us.setWechatId(wechat.getId());
						us.setDate(DateUtil.formatYYYYMMDD(ua.getRefDate()));
						us.setCreatedAt(new Date());
						us.setCumulateUser(ua.getCumulateUser());
						reportService.save(us);
					}
				}
			}
			return ReturnT.SUCCESS;
		} catch (Exception e) {
			e.printStackTrace();
			XxlJobLogger.log("获取用户来源报表失败：" + e.getMessage());
			return ReturnT.FAIL;
		}
	}
	
	private void setUserSource(ReportUserSource us, WxUserSummary ua){
		int source = ua.getUserSource();
		switch (source) {
			case 0:
				us.setNewUser0(ua.getNewUser());
				us.setCancelUser0(ua.getCancelUser());
				break;
			case 1:
				us.setNewUser1(ua.getNewUser());
				us.setCancelUser1(ua.getCancelUser());
				break;
			case 17:
				us.setNewUser17(ua.getNewUser());
				us.setCancelUser17(ua.getCancelUser());
				break;
			case 30:
				us.setNewUser30(ua.getNewUser());
				us.setCancelUser30(ua.getCancelUser());
				break;
			case 43:
				us.setNewUser43(ua.getNewUser());
				us.setCancelUser43(ua.getCancelUser());
				break;
			case 51:
				us.setNewUser51(ua.getNewUser());
				us.setCancelUser51(ua.getCancelUser());
				break;
			case 57:
				us.setNewUser57(ua.getNewUser());
				us.setCancelUser57(ua.getCancelUser());
				break;
			case 75:
				us.setNewUser75(ua.getNewUser());
				us.setCancelUser75(ua.getCancelUser());
				break;
			case 78:
				us.setNewUser78(ua.getNewUser());
				us.setCancelUser78(ua.getCancelUser());
				break;
			default:
				break;
		}
	}

}
