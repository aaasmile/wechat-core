package com.d1m.wechat.service;

import com.d1m.wechat.model.OauthUrlLog;

/**
 * Created by Stoney.Liu on 2017/9/19.
 */
public interface OauthUrlLogService extends IService<OauthUrlLog> {
    void batchInsertLog(OauthUrlLog oauthUrlLog);
}
