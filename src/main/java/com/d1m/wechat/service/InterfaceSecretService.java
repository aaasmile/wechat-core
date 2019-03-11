package com.d1m.wechat.service;

import com.d1m.wechat.model.enums.InterfaceType;

public interface InterfaceSecretService {
    //生成KEY和秘钥
    void createKeyAndSecret(String brand, InterfaceType type ,String name);
}
