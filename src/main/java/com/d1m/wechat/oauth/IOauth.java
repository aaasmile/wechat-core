package com.d1m.wechat.oauth;

import java.util.Map;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import cn.d1m.wechat.client.model.WxUser;

public interface IOauth {
    void execute(HttpServletRequest request, HttpServletResponse response, WxUser wxUser, Map<String, Object> params);
}
