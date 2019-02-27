package com.d1m.wechat.service;

import com.d1m.wechat.domain.web.BaseResponse;
import com.d1m.wechat.dto.InterfaceConfigDto;

import java.util.Map;

/**
 * Created by jone.wang on 2019/2/26.
 * Description:
 */
public interface InterfaceRabbit {
    BaseResponse sendToThirdPart(InterfaceConfigDto interfaceConfigDto, Map<String, String> payload);
}
