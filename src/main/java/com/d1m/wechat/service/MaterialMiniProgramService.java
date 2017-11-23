package com.d1m.wechat.service;

import com.d1m.wechat.model.MaterialMiniProgram;

public interface MaterialMiniProgramService extends IService<MaterialMiniProgram> {

    MaterialMiniProgram search(Integer wechatId, Integer materialId);

}
