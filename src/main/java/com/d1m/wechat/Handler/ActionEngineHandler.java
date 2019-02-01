package com.d1m.wechat.Handler;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.common.ds.TenantContext;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.model.ActionEngine;
import com.d1m.wechat.service.ActionEngineService;
import com.esotericsoftware.minlog.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

@Component
public class ActionEngineHandler {

    private Logger log = LoggerFactory.getLogger(ActionEngineHandler.class);
    @Resource
    TenantHelper tenantHelper;
    @Autowired
    private ActionEngineService actionEngineService;

//    @RabbitListener(queues = "${dcrm.wechat-core.actionEngine.deleteWechatByValue}")
    public void deleteWechatByValue(Map<String,Object> message) {
        try {
            Integer value = (Integer) message.get("value");
            Integer wechatId = (Integer) message.get("wechatId");
            String domain = tenantHelper.getTenantByWechatId(wechatId);
            if (domain != null){
                TenantContext.setCurrentTenant(domain);
                Log.info("mq domain: "+domain);
            }
            final Example example = new Example(ActionEngine.class);
            JSONObject removeJsonObj = new JSONObject();
            removeJsonObj.put("code",301);
            removeJsonObj.put("value","[" + value + "]");
            example.createCriteria().andLike("effect", removeJsonObj.toJSONString());
            updateByValue(example, removeJsonObj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }
//    @RabbitListener(queues = "${dcrm.wechat-core.actionEngine.deleteDcrmByValue}")
    public void deleteDcrmByValue(Map<String,Object> message) {
        try {
            Integer value = (Integer) message.get("value");
            Integer wechatId = (Integer) message.get("wechatId");
            String domain = tenantHelper.getTenantByWechatId(wechatId);
            if (domain != null){
                TenantContext.setCurrentTenant(domain);
                Log.info("mq domain: "+domain);
            }
            final Example example = new Example(ActionEngine.class);
            JSONObject removeJsonObj = new JSONObject();
            removeJsonObj.put("code",301);
            removeJsonObj.put("value","[" + value + "]");
            example.createCriteria().andLike("effect", removeJsonObj.toJSONString());
            updateByValue(example, removeJsonObj);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
        }
    }

    private void updateByValue(Example example, JSONObject removeJsonObj) throws Exception {
        List<ActionEngine> actionEngineList = actionEngineService.selectByExample(example);
        if(actionEngineList == null || actionEngineList.isEmpty()) {
            throw  new Exception("menuKey can not find from menu");
        }
        for( int i = 0; i < actionEngineList.size(); i++) {
            ActionEngine actionEngine = actionEngineList.get(i);
            JSONArray jsonArray = JSONArray.parseArray(actionEngine.getEffect());
            jsonArray.remove(removeJsonObj);
            log.info("remove json ... " + removeJsonObj.toJSONString() + "...jsonArray..." + jsonArray.toJSONString());
            actionEngine.setEffect(jsonArray.toJSONString());
            actionEngineService.updateAll(actionEngine);
        }
    }
}
