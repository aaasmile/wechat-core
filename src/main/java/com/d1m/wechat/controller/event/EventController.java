package com.d1m.wechat.controller.event;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.exception.WechatException;
import com.d1m.wechat.pamametermodel.AddEventForwardModel;
import com.d1m.wechat.pamametermodel.EditEventForwardModel;
import com.d1m.wechat.service.EventService;
import com.d1m.wechat.util.Message;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiResponse;
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

    @ApiOperation(value = "全部事件", tags = "事件接口列表")
    @ApiResponse(code = 200, message = "成功")
    @PostMapping("/get")
    public JSONObject queryEvent() {
        try {
            return representation(Message.SUCCESS, eventService.getAll());
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }

    @ApiOperation(value = "添加事件转发接口", tags = "事件转发接口列表")
    @ApiResponse(code = 200, message = "绑定成功")
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

    @ApiOperation(value = "修改事件转发接口", tags = "事件转发接口列表")
    @ApiResponse(code = 200, message = "绑定成功")
    @PostMapping("/edit")
    public JSONObject edit(@RequestBody EditEventForwardModel model) {
        try {
            eventService.editEventForward(model);
            return representation(Message.SUCCESS);
        } catch (WechatException e) {
            return wrapException(e);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
}
