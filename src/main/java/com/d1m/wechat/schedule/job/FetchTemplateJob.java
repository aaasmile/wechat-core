package com.d1m.wechat.schedule.job;

import java.util.ArrayList;
import java.util.List;
import javax.annotation.Resource;

import com.d1m.wechat.schedule.BaseJobHandler;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.springframework.stereotype.Component;

import cn.d1m.wechat.client.model.WxTemplate;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.d1m.wechat.model.Template;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.service.TemplateService;
import com.d1m.wechat.service.WechatService;

@JobHander(value="fetchTemplateJob")
@Component
public class FetchTemplateJob extends BaseJobHandler {

    @Resource
    private TemplateService templateService;

    @Resource
    private WechatService wechatService;

    @Override
    public ReturnT<String> run(String... strings) throws Exception {
        try {
            List<Wechat> list = wechatService.getWechatList();
            for (Wechat wechat : list) {
                List<Template> oriList = templateService.list(wechat.getId());
                List<WxTemplate> fetchList = WechatClientDelegate.getTemplateList(wechat.getId()).get();
                outer:
                for (WxTemplate ta : fetchList) {
                    for (Template t : oriList) {
                        if (t.getTemplateId().equals(ta.getTemplateId())) {
                            continue outer;
                        }
                    }
                    Template record = new Template();
                    record.setTemplateId(ta.getTemplateId());
                    record.setTitle(ta.getTitle());
                    record.setPrimaryIndustry(ta.getPrimaryIndustry());
                    record.setDeputyIndustry(ta.getDeputyIndustry());
                    record.setWechatId(wechat.getId());
                    record.setParameters(parseParameter(ta.getContent()));
                    record.setContent(ta.getContent());
                    record.setStatus((byte) 1);
                    templateService.save(record);
                }
                if (oriList != null) {
                    outer:
                    for (Template t : oriList) {
                        for (WxTemplate ta : fetchList) {
                            if (ta.getTemplateId().equals(t.getTemplateId())) {
                                t.setStatus((byte) 1);
                                templateService.updateNotNull(t);
                                continue outer;
                            }
                        }
                        t.setStatus((byte) 0);
                        templateService.updateNotNull(t);
                    }
                }

            }
            return ReturnT.SUCCESS;
        } catch (Exception e) {
            e.printStackTrace();
            XxlJobLogger.log("获取消息模板列表失败：" + e.getMessage());
            return ReturnT.FAIL;
        }
    }

    private String parseParameter(String content) {
        String[] str1 = content.split(".DATA");
        List<String> list = new ArrayList<>();
        for (int i = 0; i < str1.length - 1; i++) {
            String[] str2 = str1[i].split("\\{\\{");
            list.add(str2[1]);
        }
        return list.toString();
    }

}
