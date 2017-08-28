package com.d1m.wechat.controller.config;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.dto.ConfigDto;
import com.d1m.wechat.service.ConfigService;
import com.d1m.wechat.util.Message;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * 配置控制器
 */
@Controller
@RequestMapping("/config")
public class ConfigController extends BaseController {
    private static Logger log = LoggerFactory.getLogger(ConfigController.class);
    @Autowired
    private ConfigService configService;

    @RequestMapping(value = "get/{group}/{key}", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("system-setting:config-list")
    public JSONObject get(@PathVariable String group, @PathVariable String key,
                             HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            ConfigDto config = configService.getConfigDto(getWechatId(session),group,key);
            return representation(Message.SUCCESS,config);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @RequestMapping(value = "group/{group}", method = RequestMethod.GET)
    @ResponseBody
    @RequiresPermissions("system-setting:config-list")
    public JSONObject group(@PathVariable String group,
                             HttpSession session, HttpServletRequest request, HttpServletResponse response) {
        try {
            List<ConfigDto> list = configService.getConfigDtoList(getWechatId(session),group);
            return representation(Message.SUCCESS,list);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @RequestMapping(value = "update", method = RequestMethod.POST)
    @ResponseBody
    @RequiresPermissions("system-setting:config-list")
    public JSONObject update(@RequestBody(required = false) JSONArray jsonArray,
                                   HttpServletRequest request, HttpServletResponse response) {
        try {
            configService.update(jsonArray);
            return representation(Message.SUCCESS);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
}