package com.d1m.wechat.controller.api;

import com.alibaba.fastjson.JSONObject;
import com.d1m.wechat.controller.BaseController;
import com.d1m.wechat.service.MediaService;
import com.d1m.wechat.util.Message;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Created by D1M on 2017/5/16.
 */
@Controller
@RequestMapping("/api/media")
public class MediaController extends BaseController {
    private Logger log = LoggerFactory.getLogger(MediaController.class);

    @Autowired
    private MediaService mediaService;

    @RequestMapping(value = "get.json", method = RequestMethod.GET)
    @ResponseBody
    public JSONObject get(@RequestParam String appkey,
                            @RequestParam String mediaId,
                            @RequestParam String sign,
                            @RequestParam String timestamp) {
        try {
            JSONObject wxMedia = mediaService.getWxMedia(appkey, mediaId, sign, timestamp);
            return representation(Message.WX_MEDIA_GET_SUCCESS, wxMedia);
        } catch (Exception e) {
            log.error(e.getMessage());
            return wrapException(e);
        }
    }
}
