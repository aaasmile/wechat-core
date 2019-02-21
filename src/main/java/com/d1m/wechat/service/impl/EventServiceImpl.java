package com.d1m.wechat.service.impl;

import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.EventForwardDetailsMapper;
import com.d1m.wechat.mapper.EventForwardMapper;
import com.d1m.wechat.mapper.WxEventMapper;
import com.d1m.wechat.model.EventForward;
import com.d1m.wechat.model.EventForwardDetails;
import com.d1m.wechat.model.WxEvent;
import com.d1m.wechat.pamametermodel.AddEventForwardModel;
import com.d1m.wechat.service.EventService;
import com.d1m.wechat.util.Message;
import com.d1m.wechat.util.MyMapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@Slf4j
public class EventServiceImpl implements EventService {

    @Autowired
    private WxEventMapper wxEventMapper;

    @Autowired
    private EventForwardMapper eventForwardMapper;

    @Autowired
    private EventForwardDetailsMapper eventForwardDetailsMapper;

    @Override
    public List<WxEvent> getAll() {
        return wxEventMapper.selectAll();
    }

    @Override
    public boolean addEventForward(AddEventForwardModel model) {
        EventForward eventForward = new EventForward(model.getThirdPartyId(), model.getInterfaceId(), model.getUserUuid());
        eventForwardMapper.insertSelective(eventForward);

        if (eventForward.getId() == null) {
            log.error("event forward add fail");
            throw new WechatException(Message.EVENT_FORWARD_ADD_FAIL);
        }

        List<EventForwardDetails> eventForwardDetails = new ArrayList<>();
        model.getEventIds().forEach(eventId -> {
            eventForwardDetails.add(new EventForwardDetails(eventForward.getId(), eventId));
        });
        eventForwardDetailsMapper.insertList(eventForwardDetails);
        return true;
    }

    @Override
    public MyMapper<WxEvent> getMapper() {
        return wxEventMapper;
    }
}
