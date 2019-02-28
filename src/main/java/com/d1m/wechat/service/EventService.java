package com.d1m.wechat.service;

import com.d1m.wechat.dto.EventForwardDetailsDto;
import com.d1m.wechat.model.EventForward;
import com.d1m.wechat.model.WxEvent;
import com.d1m.wechat.pamametermodel.AddEventForwardModel;
import com.d1m.wechat.pamametermodel.EditEventForwardModel;

import java.util.List;

public interface EventService extends BaseService<WxEvent> {

    List<WxEvent> getAll();

    boolean addEventForward(AddEventForwardModel model);

    List<EventForward> getForwardByThirdPartyId(Integer thirdPartyId);

    boolean editEventForward(EditEventForwardModel model);

    EventForwardDetailsDto queryEventForwardDetails(Integer eventForwardId);

    boolean deleteEventForward(Integer eventForwardId);
}
