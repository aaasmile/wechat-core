package com.d1m.wechat.controller.api;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.common.ds.TenantContext;
import com.d1m.common.ds.TenantHelper;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.model.ActionEngine;
import com.d1m.wechat.service.ActionEngineService;
import com.d1m.wechat.util.Message;
import com.esotericsoftware.minlog.Log;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import tk.mybatis.mapper.entity.Example;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/api/actionEngine")
public class ActionEngineApiController extends BaseController {

    private Logger log = LoggerFactory.getLogger(ActionEngineApiController.class);
    @Autowired
    private ActionEngineService actionEngineService;
    @Resource
    TenantHelper tenantHelper;

    @RequestMapping("deleteWechatByValue")
    public JSONObject deleteWechatByValue(Integer value, Integer wechatId) {

        try {
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
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.SYSTEM_ERROR, e.getMessage());
        }
    }

    @RequestMapping("deleteDcrmByValue")
    public JSONObject deleteDcrmByValue(Integer value, Integer wechatId) {
        try {
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
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            return representation(Message.SYSTEM_ERROR, e.getMessage());
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
