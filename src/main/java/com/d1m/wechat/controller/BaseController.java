package com.d1m.wechat.controller;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.model.User;
import com.d1m.wechat.model.enums.UserStatus;
import com.d1m.wechat.service.WechatService;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.util.MessageUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.support.RequestContextUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.Locale;

public class BaseController {

    @Resource
    private WechatService wechatService;

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Deprecated
    protected Integer getWechatId(HttpSession session) {
        return getWechatId();
    }

    @Deprecated
    protected User getUser(HttpSession session) {
        return getUser();
    }

    @Deprecated
    protected Integer getCompanyId(HttpSession session) {
        return getCompanyId();
    }

    protected Integer getWechatId() {
        return getUser().getWechatId();
    }

    protected User getUser() {
        if ("dev".equals(System.getProperty("env"))) {
            return testEnvUser();
        }
        Subject subject = SecurityUtils.getSubject();
        User user = (User) subject.getPrincipal();
        Integer wechatId = (Integer) subject.getSession().getAttribute("CURRENT_WECHAT");
        if (wechatId != null) {
            user.setWechatId(wechatId);
        }
        return user;
    }

    private User testEnvUser() {
        final User user = new User();
        user.setId(1);
        user.setWechatId(3);
        user.setCreatorId(1);
        user.setUsername("root");
        user.setStatus(UserStatus.INUSED.getValue());
        return user;
    }

    protected Integer getCompanyId() {
        return getUser().getCompanyId();
    }

    @Deprecated
    protected Integer getIsSystemRole(HttpSession session) {
        return getIsSystemRole();
    }

    protected Integer getIsSystemRole() {
        return wechatService.getIsSystemRole(getUser());
    }

    protected MessageUtil getMessageUtil() {
        return MessageUtil.getInstance();
    }

    protected Integer getResultCode(String msg) {
        return getMessageUtil().getResultCode(msg);
    }

    protected JSONObject representation(Message msg) {
        JSONObject json = new JSONObject();
        json.put("resultCode", msg.getCode().toString());
        json.put("msg", msg.name());
        return json;
    }

    protected JSONObject representation(Message msg, Object data) {
        JSONObject json = new JSONObject();
        json.put("resultCode", msg.getCode().toString());
        json.put("msg", msg.name());
        json.put("data", data);
        return json;
    }

    protected JSONObject representation(Message msg, Object data,
                                        Integer pageSize, Integer pageNum, long count) {
        JSONObject json = new JSONObject();
        json.put("resultCode", msg.getCode().toString());
        json.put("msg", msg.name());
        JSONObject subJson = new JSONObject();
        subJson.put("result", data);
        subJson.put("pageSize", pageSize);
        subJson.put("pageNum", pageNum);
        subJson.put("count", count < 0 ? 0 : count);
        json.put("data", subJson);
        return json;
    }

    protected JSONObject wrapException(Exception e) {
        JSONObject json = new JSONObject();
        Integer resultCode = getResultCode(e.getMessage());
        String msg = e.getMessage();
        if (resultCode == null) {
            resultCode = Message.SYSTEM_ERROR.getCode();
            msg = Message.SYSTEM_ERROR.name();
        }
        json.put("resultCode", resultCode.toString());
        json.put("msg", msg);
        logger.error("[" + resultCode + "]" + msg, e);
        return json;
    }

    public Locale getLocale(HttpServletRequest request) {
        return RequestContextUtils.getLocale(request);

    }
}
