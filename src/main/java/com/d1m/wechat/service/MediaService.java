package com.d1m.wechat.service;

import com.alibaba.fastjson.JSONObject;

/**
 * Created by D1M on 2017/5/16.
 */
public interface MediaService {
    JSONObject getWxMedia(String appkey, String mediaId, String sign, String timestamp);
}
