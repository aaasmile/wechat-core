package com.d1m.wechat.service;

import com.d1m.wechat.model.WXEvent;
import com.d1m.wechat.pamametermodel.AddEventForwardModel;

import java.util.List;

public interface EventService {

    List<WXEvent> getAll();

    boolean addEventForward(AddEventForwardModel model);
}
