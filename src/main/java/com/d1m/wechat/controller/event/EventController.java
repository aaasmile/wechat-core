package com.d1m.wechat.controller.event;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.pamametermodel.AddEventForwardModel;
import com.d1m.wechat.service.EventService;
import com.d1m.wechat.util.Message;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/event")
public class EventController extends BaseController {

    @Autowired
    private EventService eventService;

    @PostMapping("/get")
    public JSONObject queryEvent() {
        try {
            return representation(Message.SUCCESS, eventService.getAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @PostMapping("/add")
    public JSONObject add(@RequestBody AddEventForwardModel model) {
        try {
            eventService.addEventForward(model);
            return representation(Message.SUCCESS);
        } catch (WechatException e) {
            return wrapException(e);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
}
