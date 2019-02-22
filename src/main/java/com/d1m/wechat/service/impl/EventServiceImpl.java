package com.d1m.wechat.service.impl;

import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.mapper.EventForwardDetailsMapper;
import com.d1m.wechat.mapper.EventForwardMapper;
import com.d1m.wechat.mapper.WxEventMapper;
import com.d1m.wechat.model.EventForward;
import com.d1m.wechat.model.EventForwardDetails;
import com.d1m.wechat.model.WxEvent;
import com.d1m.wechat.pamametermodel.AddEventForwardModel;
import com.d1m.wechat.pamametermodel.EditEventForwardModel;
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

        addEventForwardDetails(model.getEventIds(), eventForward.getId());
        return true;
    }

    private boolean addEventForwardDetails(List<Integer> eventIds, final Integer eventForwardId) {
        List<EventForwardDetails> eventForwardDetails = new ArrayList<>();
        eventIds.forEach(eventId -> {
            eventForwardDetails.add(new EventForwardDetails(eventForwardId, eventId));
        });
        eventForwardDetailsMapper.insertList(eventForwardDetails);
        return true;
    }

    @Override
    public List<EventForward> getForwardByThirdPartyId(Integer thirdPartyId) {
        return eventForwardMapper.select(new EventForward(thirdPartyId));
    }

    @Override
    public boolean editEventForward(EditEventForwardModel model) {
        EventForward eventForward = eventForwardMapper.selectByPrimaryKey(model.getId());
        if (eventForward == null) {
            throw new WechatException(Message.ILLEGAL_REQUEST);
        }

        if (model.getEventIds() == null || model.getEventIds().size() == 0) {
            throw new WechatException(Message.MISSING_PARAMTERS);
        }

        eventForward.setUserUuid(model.getUserUuid());
        eventForwardMapper.updateByPrimaryKey(eventForward);

        eventForwardDetailsMapper.delete(new EventForwardDetails(model.getId()));
        addEventForwardDetails(model.getEventIds(), model.getId());
        return true;
    }

    @Override
    public MyMapper<WxEvent> getMapper() {
        return wxEventMapper;
    }
}
