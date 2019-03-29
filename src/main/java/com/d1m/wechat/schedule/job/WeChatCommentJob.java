package com.d1m.wechat.schedule.job;

import cn.d1m.wechat.client.model.WxTemplate;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.model.Template;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.Wechat;
import com.d1m.wechat.model.enums.MassConversationResultStatus;
import com.d1m.wechat.pamametermodel.MassConversationModel;
import com.d1m.wechat.schedule.BaseJobHandler;
import com.d1m.wechat.service.MassConversationResultService;
import com.d1m.wechat.service.TemplateService;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.HttpUtils;
import com.d1m.wechat.util.ParamUtil;
import com.d1m.wechat.wechatclient.WechatClientDelegate;
import com.xxl.job.core.biz.model.ReturnT;
import com.xxl.job.core.handler.annotation.JobHander;
import com.xxl.job.core.log.XxlJobLogger;
import org.reflections.scanners.FieldAnnotationsScanner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import javax.servlet.http.HttpSession;
import java.util.ArrayList;
import java.util.List;

@JobHander(value="weChatCommentJob")
@Component
public class WeChatCommentJob extends BaseJobHandler {

    @Resource
    private WechatService wechatService;

    @Resource
    private MassConversationResultService massConversationResultService;

    private final static String COMMENT_API="https://api.weixin.qq.com/cgi-bin/comment/list?access_token=";


    @Override
    public ReturnT<String> run(String... strings) throws Exception {
        XxlJobLogger.log("WeChatCommentJob===========>>");
        if(strings!=null){
            // 参数1为微信ID
            Integer wechatId = ParamUtil.getInt(strings[0], null);
            String tokenUrl=System.getProperty("accesstoken.url") == null ?"http://dev.wechat.d1m.cn/api/wechat/access-token/wx2e896c8034634aa6/7f22235bed7a6fcc281b09958b2ce5f8":System.getProperty("accesstoken.url");
            String tokenDataString = HttpUtils.sendGet(tokenUrl);
            XxlJobLogger.log("tokenData=======>"+tokenDataString);
            if(tokenDataString==null){
                XxlJobLogger.log("TOKEN IS NULL");
                return ReturnT.FAIL;
            }
            JSONObject tokenData = JSONObject.parseObject(tokenDataString);
            List<String> list = massConversationResultService.selectMsgDataId(wechatId==null?11:wechatId);
            XxlJobLogger.log("msgDataId===========>>"+list);
            for (String msgDataId : list) {
                JSONObject sendData=new JSONObject();
                sendData.put("msg_data_id",msgDataId);
                sendData.put("begin",0);
                sendData.put("count",50);
                sendData.put("type",0);
                String commenTdata = HttpUtils.doPost(COMMENT_API + tokenData.get("data"), sendData);
                XxlJobLogger.log("commenTdata===========>>"+commenTdata);
            }
        }
        return ReturnT.SUCCESS;
    }

}
