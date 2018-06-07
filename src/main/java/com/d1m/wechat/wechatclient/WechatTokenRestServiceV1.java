package com.d1m.wechat.wechatclient;

import com.d1m.common.rest.RestResponse;
import com.d1m.wechat.configure.FeignClientConfiguration;
import org.springframework.cloud.netflix.feign.FeignClient;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * WeixinApiRestService
 * 升级wechat-token-server
 */
@FeignClient(
        name = "wechat-token-server-v1",
        configuration = FeignClientConfiguration.class
)
public interface WechatTokenRestServiceV1 {

    @RequestMapping(value = "/access-token/refresh/{appid}/{secret}",
            method = RequestMethod.POST, consumes = "application/json")
    RestResponse<String> refreshAccessToken(
            @PathVariable("appid") String appid,
            @PathVariable("secret") String secret
    );

    @RequestMapping(value = "/ticket/jsapi/{appid}/{secret}/",
            method = RequestMethod.POST, consumes = "application/json")
    RestResponse<String> getJsApiTicket(
            @PathVariable("appid") String appid,
            @PathVariable("secret") String secret
    );

    @RequestMapping(value = "/ticket/wx_card/{appid}/{secret}/",
            method = RequestMethod.POST, consumes = "application/json")
    RestResponse<String> getCardApiTicket(
            @PathVariable("appid") String appid,
            @PathVariable("secret") String secret
    );
}
