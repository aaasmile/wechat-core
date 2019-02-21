package com.d1m.wechat.service.impl;

import com.d1m.wechat.dto.EventForwardDto;
import com.d1m.wechat.mapper.EventForwardMapper;
import com.d1m.wechat.mapper.WXEventMapper;
import com.d1m.wechat.service.EventForwardService;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
@Service
@Slf4j
public class EventForwardServiceImpl implements EventForwardService {
    @Autowired
   private EventForwardMapper eventForwardMapper;
    @Autowired
    private WXEventMapper wxEventMapper;
    @Override
    public Page<EventForwardDto> selectForwardItems(Map<String, String> query) {

        Page<EventForwardDto>  InterfaceEventForwardDtos = eventForwardMapper.selectEventForwardItems(query);
        return InterfaceEventForwardDtos;
    }

    @Override
    public List<String> selectEventItems(Integer id) {
        List<String> events =wxEventMapper.selectEventItmes(id);
        return events;
    }
}
