package com.d1m.wechat.service.impl;

import com.d1m.wechat.dto.EventForwardDto;
import com.d1m.wechat.mapper.EventForwardMapper;
import com.d1m.wechat.mapper.WxEventMapper;
import com.d1m.wechat.model.EventForward;
import com.d1m.wechat.model.enums.InterfaceStatus;
import com.d1m.wechat.service.EventForwardService;
import com.d1m.wechat.util.DateUtil;
import com.github.pagehelper.Page;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
@Service
@Slf4j
public class EventForwardServiceImpl implements EventForwardService {
    @Autowired
   private EventForwardMapper eventForwardMapper;
    @Autowired
    private WxEventMapper wxEventMapper;
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

    @Override
    public int findByInterfaceId(String interfaceId) {
        return eventForwardMapper.findByInterfaceId(interfaceId);

    }

    @Override
    public EventForward checkIsExist(Integer id) {
        return eventForwardMapper.selectByPrimaryKey(id);
    }

    @Override
    public void eventForwardEnableOrDisable(InterfaceStatus status, Integer id) {
        try {
            int t = eventForwardMapper.updateStatusById(id, status, DateUtil.formatYYYYMMDDHHMM(new Date()));
        } catch (Exception e) {
            //logger.error(e.getMessage());
        }
    }

    @Override
    public void updateStatus(EventForward eventForward) {
        eventForwardMapper.updateByPrimaryKeySelective(eventForward);
    }


}
