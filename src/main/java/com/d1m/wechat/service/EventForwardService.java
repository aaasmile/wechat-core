package com.d1m.wechat.service;

import com.d1m.wechat.dto.EventForwardDto;
import com.d1m.wechat.model.EventForward;
import com.d1m.wechat.model.enums.InterfaceStatus;
import com.github.pagehelper.Page;

import java.util.List;
import java.util.Map;

public interface EventForwardService {
    //查询转发接口列表
    Page<EventForwardDto> selectForwardItems(Map<String, String> query);
    //查询事件
    List<String> selectEventItems(Integer id);

    int findByInterfaceId(String interfaceId);


    EventForward checkIsExist(Integer id);

    void eventForwardEnableOrDisable(InterfaceStatus status, Integer id);

    void updateStatus(EventForward eventForward);
    //查询是否需要union_id
    List<String> queryEventForwardByInterfaceId(String id);
}
