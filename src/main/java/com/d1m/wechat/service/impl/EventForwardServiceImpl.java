package com.d1m.wechat.service.impl;

import com.d1m.wechat.common.Constant;
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
import org.springframework.cache.annotation.CacheEvict;
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

        return eventForwardMapper.selectEventForwardItems(query);
    }

    @Override
    public List<String> selectEventItems(Integer id) {
        return wxEventMapper.selectEventItmes(id);
    }


    @Override
    @CacheEvict(value = Constant.Cache.THIRD_PARTY_INTERFACE, allEntries = true)
    public void updateStatus(EventForward eventForward) {
        eventForwardMapper.updateByPrimaryKeySelective(eventForward);
    }



    @Override
    public List<String> queryEventForwardByInterfaceId(String id) {
        return eventForwardMapper.queryEventForwardByInterfaceId(id);
    }

}
