package com.d1m.wechat.service;

import com.d1m.wechat.model.QrcodePersonal;
import com.d1m.wechat.pamametermodel.QrcodeModel;

public interface QrcodePersonalService extends IService<QrcodePersonal> {

    public QrcodePersonal create(Integer wechatId, Integer memberId, String scene);

    public QrcodePersonal create(Integer wechatId, Integer memberId, int expireSeconds,QrcodeModel qrcodeModel);
}