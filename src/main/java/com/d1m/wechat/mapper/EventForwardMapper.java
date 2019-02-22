package com.d1m.wechat.mapper;

import com.d1m.wechat.dto.EventForwardDetailsDto;
import com.d1m.wechat.dto.EventForwardDto;
import com.d1m.wechat.model.EventForward;
import com.d1m.wechat.util.MyMapper;
import com.github.pagehelper.Page;

import java.util.Map;

public interface EventForwardMapper extends MyMapper<EventForward> {

    Page<EventForwardDto> selectEventForwardItems(Map<String, String> query);

    EventForwardDetailsDto queryEventForwardDetailsById(Integer id);
}
