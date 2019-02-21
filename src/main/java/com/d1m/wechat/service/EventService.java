package com.d1m.wechat.service;

import com.d1m.wechat.model.WxEvent;
import com.d1m.wechat.pamametermodel.AddEventForwardModel;

import java.util.List;

public interface EventService extends BaseService<WxEvent> {

    List<WxEvent> getAll();

    boolean addEventForward(AddEventForwardModel model);
}
